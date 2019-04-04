package com.goodpeople.setgo.repository;

import com.goodpeople.setgo.domain.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, String> {
}
