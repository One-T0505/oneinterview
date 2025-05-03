package org.ymy.oneinterview.constant;

/**
 * 2024/10/17 - 15 : 36
 *
 * @author ymy
 * @version 1.0
 * @description
 **/
public interface RedisConstant {

    /*
    * 用户签到记录的 Redis key 前缀
    *
    * */
    String USER_SIGN_IN_REDIS_KEY_PREFIX = "user:signins";


    /*
     * 获取用户签到记录的 Redis key
     *
     * */
    static String getUserSignInRedisKey(int year, long userId) {
        return String.format("%s:%s:%S", USER_SIGN_IN_REDIS_KEY_PREFIX, year, userId);
    }
}
