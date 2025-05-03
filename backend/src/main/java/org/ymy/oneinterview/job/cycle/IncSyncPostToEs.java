package org.ymy.oneinterview.job.cycle;

import org.ymy.oneinterview.esdao.PostEsDao;
import org.ymy.oneinterview.mapper.PostMapper;
import org.ymy.oneinterview.model.dto.post.PostEsDTO;
import org.ymy.oneinterview.model.entity.Post;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollUtil;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 增量同步帖子到 es
 *
 * @author ymy
 * @from
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class IncSyncPostToEs {

    @Resource
    private PostMapper postMapper;

    @Resource
    private PostEsDao postEsDao;

    /**
     * 每10分钟执行一次
     */
    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void run() {
        // 查询近 10 分钟内的数据
        long tenMinutes = 10 * 60 * 1000L;
        Date tenMinutesAgoDate = new Date(new Date().getTime() - tenMinutes);
        List<Post> postList = postMapper.listPostWithDelete(tenMinutesAgoDate);
        if (CollUtil.isEmpty(postList)) {
            log.info("no inc post");
            return;
        }
        List<PostEsDTO> postEsDTOList = postList.stream()
                .map(PostEsDTO::objToDto)
                .collect(Collectors.toList());
        final int pageSize = 500;
        int total = postEsDTOList.size();
        log.info("IncSyncPostToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            postEsDao.saveAll(postEsDTOList.subList(i, end));
        }
        log.info("IncSyncPostToEs end, total {}", total);
    }
}
