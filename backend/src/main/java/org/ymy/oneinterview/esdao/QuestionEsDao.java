package org.ymy.oneinterview.esdao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.ymy.oneinterview.model.dto.post.PostEsDTO;
import org.ymy.oneinterview.model.dto.question.QuestionEsDTO;

import java.util.List;

/**
 * 帖子 ES 操作
 *
 * @author ymy
 * @from
 */
public interface QuestionEsDao extends ElasticsearchRepository<QuestionEsDTO, Long> {

    List<QuestionEsDTO> findByUserId(Long userId);
}
