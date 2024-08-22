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
public class Statement {

    @javax.persistence.Id
    @SequenceGenerator(name = "statement_sequence",
            sequenceName = "statement_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "statement_sequence")
    private Long Id;


    private Long telegramId;
    private String fullName;
    private String yearEntry;
    private String statement;
    private String phoneNumber;
    private String groupe;
    private boolean status = false;
    private boolean isReady = false;


    public String toString(){
        return  "ПІБ:  " +getFullName() + "\n" +
                "Група:  " +getGroupe() + "\n" +
                "Рік набору:  "+ getYearEntry() + "\n" +
                "Номер телефону:  " +getPhoneNumber() + "\n" +
                "Тип заявки:  " + getStatement();

    }


}
