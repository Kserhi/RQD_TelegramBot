package com.botforuni.repositories;

import com.botforuni.domain.TelegramUserCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserCacheRepository extends JpaRepository<TelegramUserCache,Long> {


}
