package com.botforuni.repositories;

import com.botforuni.domain.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatementRepository extends JpaRepository<Statement,Long> {

    List<Statement> findAllByTelegramId(Long telegramId);






}