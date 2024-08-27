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
    @Column(name = "id")
    private Long statementId;
    private boolean isReady;
    private  boolean status;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Statement statement;


}
