package org.ymy.oneinterview.blackfilter;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

/**
 * 2024/10/20 - 15 : 00
 *
 * @author ymy
 * @version 1.0
 * @description IP黑名单过滤工具类
 **/

@Slf4j
public class BlackIpUtils {

    private static BitMapBloomFilter bloomFilter;


    /*
    * 判断ip是否在黑名单里
    * */
    public static boolean isBlackIp(String ip) {
        return bloomFilter.contains(ip);
    }


    /*
    * 重建ip黑名单，因为布隆过滤器是在内存中的会丢失，所以我们采用了nacos配置中心，将黑名单存入到了nacos中，一旦发生改变，我们都需要从nacos中加载黑名单
    * */
    public static void refreshBlackIpList(String configInfo) {
        if (StrUtil.isBlank(configInfo)) {
            configInfo = "{}";
        }
        // 解析 yaml 文件
        Yaml yaml = new Yaml();
        Map map = yaml.loadAs(configInfo, Map.class);
        List<String> blackIpList = (List<String>) map.get("blackIpList");

        synchronized (BlackIpUtils.class) {
            // 构造布隆过滤器
            if (CollUtil.isNotEmpty(blackIpList)) {
                BitMapBloomFilter bitMapBloomFilter = new BitMapBloomFilter(1000);
                for (String blackIp : blackIpList) {
                    bitMapBloomFilter.add(blackIp);
                }
                bloomFilter = bitMapBloomFilter;
            } else {
                bloomFilter = new BitMapBloomFilter(100);
            }
        }

    }
}
