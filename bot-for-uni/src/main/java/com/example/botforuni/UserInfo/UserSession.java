package com.example.botforuni.UserInfo;

import com.example.botforuni.enums.ConversationState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSession {
    private Long chatId;
    private ConversationState state;
    private String groups;
    private String text;
}
