package org.ymy.oneinterview.model.dto.questionBank;

import org.ymy.oneinterview.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询题库请求
 *
 * @author ymy
 * @from
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionBankQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private Long notId;

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片
     */
    private String picture;


    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 根据场景确定是否需要展示当前题库的题目列表
     */
    private boolean needQueryQuestionList;

    private static final long serialVersionUID = 1L;
}
