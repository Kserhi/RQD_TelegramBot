package com.botforuni.utils;

public class Validator {

    private static final String NAME_PATTERN = "^[a-zA-Zа-яА-ЯїЇіІєЄґҐ\\s]+$";
    private static final String GROUP_PATTERN = "^[А-Яа-яA-Za-z]{2}\\d{2}[а-яa-z]$";
    private static final String YEAR_PATTERN = "^\\d{4}$";


    public static boolean validateName(String name) {
        return name != null && name.matches(NAME_PATTERN);
    }

    public static boolean validateGroup(String group) {
        return group != null && group.matches(GROUP_PATTERN);
    }

    public static boolean validateYear(String year) {
        return year != null && year.matches(YEAR_PATTERN);
    }


}
