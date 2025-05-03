package org.ymy.oneinterview.job.cycle;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.ymy.oneinterview.esdao.QuestionEsDao;
import org.ymy.oneinterview.mapper.QuestionMapper;
import org.ymy.oneinterview.model.dto.question.QuestionEsDTO;
import org.ymy.oneinterview.model.entity.Question;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ymy
 */ // todo 取消注释开启任务
@Component
@Slf4j
public class IncSyncQuestionToEs {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionEsDao questionEsDao;

    /**
     * 每10分钟执行一次
     */
    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void run() {
        // 查询近 10 分钟内的数据
        long tenMinutes = 10 * 60 * 1000L;
        Date tenMinutesAgoDate = new Date(new Date().getTime() - tenMinutes);
        List<Question> questionList = questionMapper.listQuestionWithDelete(tenMinutesAgoDate);
        if (CollUtil.isEmpty(questionList)) {
            log.info("no inc question");
            return;
        }
        List<QuestionEsDTO> questionEsDTOList = questionList.stream()
                .map(QuestionEsDTO::objToDto)
                .collect(Collectors.toList());
        final int pageSize = 500;
        int total = questionEsDTOList.size();
        log.info("IncSyncQuestionToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            questionEsDao.saveAll(questionEsDTOList.subList(i, end));
        }
        log.info("IncSyncQuestionToEs end, total {}", total);
    }
}
