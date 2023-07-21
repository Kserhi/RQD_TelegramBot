package com.example.botforuni.cache;

import com.example.botforuni.domain.BotUser;
import com.example.botforuni.domain.Position;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotUserCache implements Cache<BotUser>{
    private final Map<Long, BotUser> users;

    public BotUserCache() {
        this.users = new HashMap<>();
    }

    @Override
    public void add(BotUser botUser) {
        users.put(botUser.getTelegramId(), botUser);

    }

    @Override
    public void remove(BotUser botUser) {
        users.remove(botUser.getTelegramId());

    }

    @Override
    public BotUser findBy(Long id) {
        return users.get(id);
    }

    @Override
    public List<BotUser> getAll() {
        return new ArrayList<>(users.values());
    }

   public static BotUser generateUserFromMessage(Message message) {
        BotUser user = new BotUser();
        user.setTelegramId(message.getChatId());
        user.setPosition(Position.INPUT_USER_NAME);
        return user;
    }

}
