package com.example.botforuni.testData;

import javax.persistence.*;

@Entity
@Table
public class User {
    @Id
    @SequenceGenerator(
            name = "User",
            sequenceName = "User",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "User"
    )

    private Long id;
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
