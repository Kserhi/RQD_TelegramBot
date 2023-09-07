package com.example.botforuni.repositories;

import com.example.botforuni.domain.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BotUserRepository extends JpaRepository<BotUser,Long> {
//    просто прописав в назві методу те за чим вім має знаходити
    List<BotUser> findByTelegramIdAndStatement(Long telegramId,String statement);
}
