package com.campusworklife.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import com.campusworklife.dto.Member;

@Mapper
public interface MemberMapper {

    @Select("SELECT * FROM member")
    @Results({
        @Result(property = "joinDate", column = "join_date") 
    })
    List<Member> findAll();
}
