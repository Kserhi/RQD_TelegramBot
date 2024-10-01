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

        return "ПІБ: " + fullName + "\n" +
                "Група: " + groupe + "\n" +
                "Рік набору: " + yearEntry + "\n" +
                "Факультет: " + faculty + "\n" +
                "Номер телефону: " + phoneNumber + "\n" +
                "Тип заявки: " + typeOfStatement + "\n" +
                "Статус заявки: " + status;
    }


}
