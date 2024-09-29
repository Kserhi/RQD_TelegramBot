package com.botforuni.domain;

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
public class TelegramUserCache {
    @Id
    private Long telegramId;

    private Integer massageId;


    @Enumerated(EnumType.STRING)
    private Position position;

    @OneToOne(mappedBy = "telegramUserCache", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private StatementCache statementCache;



}
