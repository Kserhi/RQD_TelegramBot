package com.botforuni.repositories;

import com.botforuni.domain.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserRepository extends JpaRepository<TelegramUser,Long> {


}