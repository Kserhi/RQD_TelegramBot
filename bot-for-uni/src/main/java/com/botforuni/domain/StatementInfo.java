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
public class StatementInfo {
    @javax.persistence.Id
    @SequenceGenerator(name = "statementInfo_sequence",
            sequenceName = "statementInfo_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "statementInfo_sequence")
    private Long Id;

    private Long statementId;
    private boolean isReady;
    private  boolean status;
}
