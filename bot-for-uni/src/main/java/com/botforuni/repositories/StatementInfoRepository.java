package com.botforuni.repositories;

import com.botforuni.domain.StatementInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StatementInfoRepository extends JpaRepository<StatementInfo,Long> {

    @Query("SELECT s_i FROM StatementInfo s_i WHERE s_i.status = true AND s_i.isReady = false")
    List<StatementInfo> findTrueStatusAndFalseIsReady();


}