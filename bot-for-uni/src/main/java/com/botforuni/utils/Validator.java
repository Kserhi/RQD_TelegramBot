package com.botforuni.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Validator {


    private static final String NAME_PATTERN = "^[а-яА-ЯїЇіІєЄґҐ\\s]+$"; // Українські літери та пробіли
    private static final String GROUP_PATTERN = "^[А-Яа-я]{2}\\d{2}[А-Яа-я]$"; // Дві українські літери, дві цифри, одна українська літера
    private static final String YEAR_PATTERN = "^\\d{4}$";
    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 100;
    private static final int YEAR_MIN_VALUE = 1900;
    private static final int YEAR_MAX_VALUE = 2100;


    private static final List<String> VALID_FACULTIES = Arrays.asList(
            "Факультет цивільного захисту",
            "Факультет пожежної та техногенної безпеки",
            "Факультет психології і соціального захисту"
    );
    private static final Set<String> VALID_FACULTY_SET = new HashSet<>(VALID_FACULTIES);


    public static ValidationResult validateName(String name) {
        if (name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH) {
            return new ValidationResult(false, "Ім'я повинно містити від " + NAME_MIN_LENGTH + " до " + NAME_MAX_LENGTH + " символів.");
        }
        if (!name.matches(NAME_PATTERN)) {
            return new ValidationResult(false, "Ім'я може містити тільки українські літери та пробіли.");
        }
        return new ValidationResult(true, "Ім'я валідне.");
    }

    public static ValidationResult validateGroup(String group) {
        if (!group.matches(GROUP_PATTERN)) {
            return new ValidationResult(false, "Група повинна відповідати формату: дві літери, дві цифри та одна літера (наприклад, AB12c).");
        }
        return new ValidationResult(true, "Група валідна.");
    }

    public static ValidationResult validateYear(String year) {
        if (!year.matches(YEAR_PATTERN)) {
            return new ValidationResult(false, "Рік повинен містити 4 цифри.");
        }
        int yearValue = Integer.parseInt(year);
        if (yearValue < YEAR_MIN_VALUE || yearValue > YEAR_MAX_VALUE) {
            return new ValidationResult(false, "Рік повинен бути в діапазоні від " + YEAR_MIN_VALUE + " до " + YEAR_MAX_VALUE + ".");
        }
        return new ValidationResult(true, "Рік валідний.");
    }


    public static ValidationResult validateFaculty(String faculty) {
        if (faculty == null || faculty.trim().isEmpty()) {
            return new ValidationResult(false, "Факультет не може бути порожнім.");
        }
        if (!VALID_FACULTY_SET.contains(faculty)) {
            return new ValidationResult(false, "Факультет не є дійсним. Введіть один з наступних факультетів: " + String.join(", ", VALID_FACULTIES) + ".");
        }
        return new ValidationResult(true, "Факультет валідний.");
    }


}
