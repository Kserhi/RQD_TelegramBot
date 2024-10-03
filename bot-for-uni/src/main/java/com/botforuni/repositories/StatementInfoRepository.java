package com.botforuni.repositories;

import com.botforuni.domain.StatementInfo;
import com.botforuni.domain.StatementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatementInfoRepository extends JpaRepository<StatementInfo,Long> {

//    @Query("SELECT s_i FROM StatementInfo s_i WHERE s_i.statementStatus = 'READY' AND s_i.isReady = false")
//    List<StatementInfo> findWhereIsReadyFalse();
//
//
//
//    @Query("SELECT s FROM StatementInfo s WHERE s.isReady = true AND s.statementStatus = :status")
//    List<StatementInfo> findByStatus(@Param("status") StatementStatus status);


    @Query("SELECT s_i FROM StatementInfo s_i WHERE s_i.statementStatus = :status AND s_i.isReady = false")
    List<StatementInfo> findWhereIsReadyFalse(@Param("status") StatementStatus status);

}