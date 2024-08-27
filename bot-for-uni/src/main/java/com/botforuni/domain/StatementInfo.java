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
    @Id
    private Long Id;
    private Long statementId;
    private boolean isReady;
    private  boolean status;
}
