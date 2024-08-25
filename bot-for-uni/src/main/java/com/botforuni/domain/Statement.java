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
    private String fullName;
    private String yearEntry;
    private String groupe;
    private String phoneNumber;
    private String faculty;
    private String typeOfStatement;
    private Long telegramId;
    public String toString() {
        return "ПІБ:  " + getFullName() + "\n" +
                "Група:  " + getGroupe() + "\n" +
                "Рік набору:  " + getYearEntry() + "\n" +
                "Факультет:  " + getFaculty() + "\n" +
                "Номер телефону:  " + getPhoneNumber() + "\n" +
                "Тип заявки:  " + getTypeOfStatement();

    }


}
