package com.example.botforuni.domain;

import org.springframework.stereotype.Component;

@Component
public class BotUser {
    private Long id;
    private Position position;
    private String fullName;
    private String yearEntry;
    private String statement;
    private String phoneNumber;
    private String groupe;
    private String mail;

    public Long getId() {
        return id;
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

    public String getMail() {
        return mail;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setMail(String mail) {
        this.mail = mail;
    }
}
