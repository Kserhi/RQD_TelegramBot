package com.example.botforuni.testData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserData extends JpaRepository<User,Long> {


}
