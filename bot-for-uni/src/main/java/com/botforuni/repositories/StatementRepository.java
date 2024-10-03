package com.botforuni.repositories;

import com.botforuni.domain.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface StatementRepository extends JpaRepository<Statement,Long> {

    List<Statement> findAllByTelegramId(Long telegramId);


//    @Query(value = "SELECT f.id, f.file_name, f.file_type, f.data, f.statement_id" +
//            "FROM statement s " +
//            "JOIN files f ON s.id = f.statement_id" +
//            "WHERE f.statement_id = :statementId",
//            nativeQuery = true)
//    List<Objects[]> findFileById(@Param("statementId") Long statementId);
//


}