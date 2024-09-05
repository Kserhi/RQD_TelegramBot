package com.botforuni.services;

import com.botforuni.domain.StatementCache;
import com.botforuni.domain.TelegramUserCache;
import com.botforuni.repositories.StatementCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class StatementCacheService {

    private final StatementCacheRepository statementCacheRepository;

    @Autowired
    public StatementCacheService(StatementCacheRepository statementCacheRepository) {
        this.statementCacheRepository = statementCacheRepository;
    }


//    public void save(StatementCache statement){
//        statementCacheRepository.save(statement);
//    }
//
//    public Optional<StatementCache> get(Long id){
//        return statementCacheRepository.findById(id);
//    }

    public void removeAll(Long id){
        statementCacheRepository.removeAllById(id);
    }




    public  StatementCache generateStatement(TelegramUserCache telegramUserCache, String typeOfStatement) {
        StatementCache statementCache = new StatementCache();
        statementCache.setId(telegramUserCache.getTelegramId());
        statementCache.setTypeOfStatement(typeOfStatement);
        statementCache.setTelegramUserCache(telegramUserCache);

        return statementCache;
    }



}
