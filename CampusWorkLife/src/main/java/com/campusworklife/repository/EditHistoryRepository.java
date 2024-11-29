package com.campusworklife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campusworklife.entity.EditHistory;

public interface EditHistoryRepository extends JpaRepository<EditHistory, Integer> {
}
