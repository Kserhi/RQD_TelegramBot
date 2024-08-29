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
    private Long id;
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
        String status = (statementInfo != null && statementInfo.isStatus()) ? "Готова" : "В обробці";

        return new StringBuilder()
                .append("ПІБ: ").append(fullName).append("\n")
                .append("Група: ").append(groupe).append("\n")
                .append("Рік набору: ").append(yearEntry).append("\n")
                .append("Факультет: ").append(faculty).append("\n")
                .append("Номер телефону: ").append(phoneNumber).append("\n")
                .append("Тип заявки: ").append(typeOfStatement).append("\n")
                .append("Статус заявки: ").append(status)
                .toString();
    }


}
