package com.example.botforuni.testData;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Servise {
    private final UserData userData;
    @Autowired
    public Servise(UserData userData) {
        this.userData = userData;
    }
    public List<User> getUser(){
       return userData.findAll();
    }
}
