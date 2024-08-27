package com.botforuni.domain;

import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class TelegramUser {
    @Id
    private Long telegramId;

    @Enumerated(EnumType.STRING)
    private Position position;

    private Long idOfStatement;


}
