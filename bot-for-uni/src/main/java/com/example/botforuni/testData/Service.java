package com.example.botforuni.testData;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    private final UserData userData;

    @Autowired
    public Service(UserData userData) {
        this.userData = userData;
    }

    public List<User> getUser(){
       return userData.findAll();
    }
}
