package com.example.botforuni.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class BotUser {

    @Id
    @SequenceGenerator(name = "user_sequence",
    sequenceName = "user_sequence",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY,
    generator = "user_sequence")
    private Long Id;

    @Transient
    private Position position;
    @Transient
    public static final String STATEMENTFORMILITARI = "Довідка для військомату";
    @Transient
    public static final String STATEMENTFORSTUDY = "Довідка з місця навчання";

    private Long telegramId;
    private String fullName;
    private String yearEntry;
    private String statement;
    private String phoneNumber;
    private String groupe;
    private boolean status = false;


    public String toString(){
        return  "ПІБ:  " +getFullName() + "\n" +
                "Група:  " +getGroupe() + "\n" +
                "Рік набору:  "+ getYearEntry() + "\n" +
                "Номер телефону:  " +getPhoneNumber() + "\n" +
                "Тип заявки:  " + getStatement();

    }


}
