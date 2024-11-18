package com.campusworklife.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campusworklife.entity.Member2;
import com.campusworklife.entity.Suggestion;


public interface SuggestionRepository extends JpaRepository<Suggestion,Integer> {

	 // 제목으로 검색
    @Query("SELECT s FROM Suggestion s WHERE s.title LIKE %:title%")
    Page<Suggestion> findByTitleContaining(@Param("title") String title, Pageable pageable);
/*
<코드 설명및 정리>
 @Query를 사용하여 좀 더 구체적인 쿼리를 작성한 것
 findByTitleContainint:메서드 이름
 Page<Suggestion>을 반환하는데, 이는 검색된 Suggestion 목록을 페이지 단위로 나누어 반환합니다
 Pageable 파라미터를 통해 어떤 페이지를 가져올지, 한 페에지에 몇개의 항목을 표시할지를 설정할 수 있습니다.
 
 Pageable은 Spring Data JPA에서 제공하는 페이징 처리를 위한 인터페이스
 
 
 */
    // 작성자 이름으로 검색
    @Query("SELECT s FROM Suggestion s JOIN s.member2 m WHERE m.username LIKE %:username%")
    Page<Suggestion> findByMemberUsernameContaining(@Param("username") String username, Pageable pageable);
   
}