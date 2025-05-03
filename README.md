# OneInterview

一个面试题库系统，包含前端和后端两个部分。

## 项目结构

- `frontend/`: 基于Next.js构建的前端应用
- `backend/`: 基于Spring Boot构建的后端应用

## 开发指南

### 前端

#### 环境要求
1. Node.js >= 18.18
2. Next.js 14

```bash
cd frontend
npm install
npm run dev
```

### 后端
我们的服务启动在8101端口，建议不要修改，因为其他服务和后端通信时已经写好了这个地址。多参考下 application.xml 配置文件。jdk版本必须使用11

#### mysql
1. 需要创建一个名为one_interview的数据库名
2. 相关的创建表的sql在 ./backend/sql/create_table.sql
   
#### redis
1. 配置文件中会连接本地6379端口运行的redis，使用索引为1的数据库

#### ElasticSearch
1. 本服务还依赖于ES和kibana，版本必须都为 7.17.28
2. 下载好了后直接运行即可，使用的都是默认的端口号  ES 9200  kibana 5601
3. 下载对应版本的ik分词器放在es的plugins目录下，命名为ik即可
4. 访问 http://localhost:5601/app/dev_tools#/console 测试es和kibana是否成功运行。
```
POST /_analyze
{
  "analyzer": "ik_smart", 
  "text": "我是个帅小伙，非常喜欢编程"
}
```
运行该命令检测ik分词器是否安装成功。
5. mysql中的题目数据通过全量和增量方式同步至ES中，其中全量同步只在项目启动时运行一次，而增量同步以固定时间间隔运行。

#### hotkey
1. hotkey是京东的一个轻量级的热点探测框架，用于根据指定的热点规则自动发现热点数据同步至本地缓存caffine或者分布式缓存redis。其中hotkey有几个核心组件：worker、dashboard、etcd，具体可以参考：
   1. https://gitee.com/jd-platform-opensource/hotkey
   2. https://www.codefather.cn/course/1826803928691945473/section/1833034244522041346?contentType=text#heading-43
   3. https://mp.weixin.qq.com/s/xOzEj5HtCeh_ezHDPHw6Jw
2. 本服务中etcd是通过docker部署的，版本使用 bitnami/etcd:3.5.15，按照官方命令启动即可，监听端口2379
3. hotkey的启动则在本项目 hotkey-master-v0.0.4。本项目已经修改了其配置文件 application.yml，把启动端口改为了 8111；直接运行worker module即可。
4. 启动hotkey dashboard，执行resource目录下的db.sql，创建dashboard所需的库表，hotkey依赖mysql来持久化账户信息和热点阈值规则。执行sql脚本前需要先执行以下命令：
```
create database hotkey_db;
use hotkey_db;
```
5. hotkey项目下还有一个client module，我们需要单独打包安装这个module，然后在我们的服务中引入该依赖，这样才能使用。

#### snetinel
本服务使用的是 1.8.6 版本的，只需要下载对应的jar包即可。通过该命令即可启动：==java -Dserver.port=8131 -jar sentinel-dashboard-1.8.6.jar== .因为sentinel项目是没关联到mysql之类的数据库做持久化的，所以只要重新启动了，那么原有的流量统计的数据和限流规则就都没了。因为引入了sentinel组件，所以再启动我们后端服务时就需要添加配置来绑定该sentinel，在启动项目时添加一个vm参数：
==-Dcsp.sentinel.dashboard.server=localhost:8131==
这样就可以关联起来了

本地访问 http://localhost:8131/ 即可访问控制台，账户密码默认都是sentinel

#### nacos
1. 下载和sentinel-1.8.6兼容的 2.2.0（我用的2.2.3）版本的nacos-server，直接在bin目录运行：sh startup.sh -m standalone

## 部署

### 打包后端

```bash
cd backend
mvn clean package
```

### 构建前端

```bash
cd frontend
npm run build
``` 