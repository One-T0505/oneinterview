package org.ymy.oneinterview.mapper;

import org.apache.ibatis.annotations.Select;
import org.ymy.oneinterview.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;

/**
* @author ymy
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2024-10-15 16:48:33
* @Entity org.ymy.oneinterview.model.entity.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {


    /*
    * 查询题目列表（包含已被删除的数据）
    * */
    @Select("select * from question where updateTime >= #{minUpdateTime}")
    List<Question> listQuestionWithDelete(Date minUpdateTime);
}




