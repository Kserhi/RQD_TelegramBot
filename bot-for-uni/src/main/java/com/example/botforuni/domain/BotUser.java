package com.example.botforuni.domain;

import javax.persistence.*;

@Entity
@Table
public class BotUser {
    //    @Id
//    @SequenceGenerator(
//            name = "botUsers_sequence",
//            sequenceName = "user_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(


//            strategy = GenerationType.SEQUENCE,
//            generator = "user_sequence"
//    )

    public BotUser() {
    }

    public BotUser(Long Id,
                   Long telegramId,
                   Position position,
                   String fullName,
                   String yearEntry,
                   String statement,
                   String phoneNumber,
                   String groupe) {
        this.Id = Id;
        this.telegramId = telegramId;
        this.position = position;
        this.fullName = fullName;
        this.yearEntry = yearEntry;
        this.statement = statement;
        this.phoneNumber = phoneNumber;
        this.groupe = groupe;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    private Long telegramId;
    @Transient
    private Position position;
    private String fullName;
    private String yearEntry;
    private String statement;
    private String phoneNumber;
    private String groupe;

    public Long getTelegramId() {
        return telegramId;
    }

    public Position getPosition() {
        return position;
    }

    public String getFullName() {
        return fullName;
    }

    public String getYearEntry() {
        return yearEntry;
    }

    public String getStatement() {
        return statement;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGroupe() {
        return groupe;
    }


    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setYearEntry(String yearEntry) {
        this.yearEntry = yearEntry;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }
    public String toString(){
        return  "ПІБ:  " +getFullName() + "\n" +
                "Група:  " +getGroupe() + "\n" +
                "Рік набору:  "+ getYearEntry() + "\n" +
                "Номер телефону:  " +getPhoneNumber() + "\n" +
                "Тип заявки:  " + getStatement();

    }
}
