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
public class StatementCache {

    @Id
    @Column(name = "id")
    private Long id;

    private String fullName;
    private String yearEntry;
    private String groupe;
    private String phoneNumber;
    private String faculty;
    private String typeOfStatement;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private TelegramUserCache telegramUserCache;


    @Override
    public String toString() {

        return new StringBuilder()
                .append("ПІБ: ").append(fullName).append("\n")
                .append("Група: ").append(groupe).append("\n")
                .append("Рік набору: ").append(yearEntry).append("\n")
                .append("Факультет: ").append(faculty).append("\n")
                .append("Номер телефону: ").append(phoneNumber).append("\n")
                .append("Тип заявки: ").append(typeOfStatement).append("\n")
                .toString();
    }

}
