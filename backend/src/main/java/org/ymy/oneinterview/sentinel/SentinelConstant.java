package org.ymy.oneinterview.sentinel;

/**
 * 2024/10/20 - 12 : 17
 *
 * @author ymy
 * @description Sentinel 限流熔断常量
 **/
public interface SentinelConstant {

    /**
     * 分页获取题库列表接口限流
     */
    String listQuestionBankVOByPage = "listQuestionBankVOByPage";

    /**
     * 分页获取题目列表接口限流
     */
    String listQuestionVOByPage = "listQuestionVOByPage";
}
