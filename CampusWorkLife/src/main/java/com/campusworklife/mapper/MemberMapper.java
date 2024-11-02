package com.campusworklife.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.campusworklife.dto.Member;

@Mapper
public interface MemberMapper {
	
	@Select("SELECT * FROM member")
	List<Member> findAll();
}
