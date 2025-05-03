# OneInterview

一个面试题库系统，包含前端和后端两个部分。

## 项目结构

- `frontend/`: 基于Next.js构建的前端应用
- `backend/`: 基于Spring Boot构建的后端应用

## 开发指南

### 后端

```bash
cd backend
mvn spring-boot:run
```

### 前端

```bash
cd frontend
npm install
npm run dev
```

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