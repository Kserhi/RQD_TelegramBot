package com.botforuni.utils;
////TODO зробити нормальне шифрування
public class HashFunction {

    public static String code(Long fileId){

        return fileId.toString();
    }


    public static Integer deCode(String hash){
        return Integer.valueOf(hash);
    }
}
