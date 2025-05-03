package org.ymy.oneinterview.esdao;

import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 2024/10/17 - 22 : 01
 *
 * @author ymy
 * @description
 **/
class QuestionEsDaoTest {

    @Resource
    private QuestionEsDao questionEsDao;

    @Test
    void findByUserId() {
        questionEsDao.findByUserId(1L);
    }
}
