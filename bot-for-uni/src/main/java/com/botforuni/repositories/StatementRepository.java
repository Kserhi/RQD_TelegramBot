package com.botforuni.repositories;

import com.botforuni.domain.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface StatementRepository extends JpaRepository<Statement,Long> {

    List<Statement> findAllByTelegramId(Long telegramId);




}