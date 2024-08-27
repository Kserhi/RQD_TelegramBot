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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
