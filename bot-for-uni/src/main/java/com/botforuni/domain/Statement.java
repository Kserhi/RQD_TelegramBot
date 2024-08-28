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

    @OneToOne(mappedBy = "statement", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private StatementInfo statementInfo;


    @Override
    public String toString() {
        String status = statementInfo.isStatus() ? "Готова" : "В обробці";

        return new StringBuilder()
                .append("ПІБ:  ").append(getFullName()).append("\n")
                .append("Група:  ").append(getGroupe()).append("\n")
                .append("Рік набору:  ").append(getYearEntry()).append("\n")
                .append("Факультет:  ").append(getFaculty()).append("\n")
                .append("Номер телефону:  ").append(getPhoneNumber()).append("\n")
                .append("Тип заявки:  ").append(getTypeOfStatement()).append("\n")
                .append("Статус заявки:  ").append(status)
                .toString();
    }


}
