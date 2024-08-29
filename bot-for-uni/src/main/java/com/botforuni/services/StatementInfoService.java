package com.botforuni.services;

import com.botforuni.domain.Statement;
import com.botforuni.domain.StatementInfo;
import com.botforuni.repositories.StatementInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementInfoService {

    private static StatementInfoRepository statementInfoRepository;
    @Autowired
    public StatementInfoService(StatementInfoRepository statementInfoRepository) {
        StatementInfoService.statementInfoRepository = statementInfoRepository;
    }

    public static void save(StatementInfo statementInfo) {
        statementInfoRepository.save(statementInfo);
    }

    public static StatementInfo generate( Statement statement) {
        return new StatementInfo(statement.getId(), false,false,statement);
    }


    public static List<StatementInfo> getReadyStatement(){

        return statementInfoRepository.findTrueStatusAndFalseIsReady();
    }


    public static void saveAll(List<StatementInfo> infoList){
        statementInfoRepository.saveAll(infoList);

    }
}
