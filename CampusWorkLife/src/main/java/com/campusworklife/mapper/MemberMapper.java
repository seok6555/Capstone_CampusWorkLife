package com.campusworklife.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.campusworklife.dto.Member;

@Mapper
public interface MemberMapper {

    @Select("SELECT * FROM member")
    @Results({
        @Result(property = "joinDate", column = "join_date") 
    })
    List<Member> findAll();
    
    @Delete("DELETE FROM member WHERE username=#{username}")
    void deleteById(String username);
}

