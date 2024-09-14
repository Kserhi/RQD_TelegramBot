package com.botforuni.utils;

public class Validator {

    private static final String NAME_PATTERN = "^[a-zA-Zа-яА-ЯїЇіІєЄґҐ\\s]+$";
    private static final String GROUP_PATTERN = "^[А-Яа-яA-Za-z]{2}\\d{2}[а-яa-z]$";
    private static final String YEAR_PATTERN = "^\\d{4}$";
    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 50;
    private static final int YEAR_MIN_VALUE = 1900;
    private static final int YEAR_MAX_VALUE = 2100;

    public static ValidationResult validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ValidationResult(false, "Ім'я не може бути порожнім.");
        }
        if (name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH) {
            return new ValidationResult(false, "Ім'я повинно містити від " + NAME_MIN_LENGTH + " до " + NAME_MAX_LENGTH + " символів.");
        }
        if (!name.matches(NAME_PATTERN)) {
            return new ValidationResult(false, "Ім'я може містити тільки літери та пробіли.");
        }
        return new ValidationResult(true, "Ім'я валідне.");
    }

    public static ValidationResult validateGroup(String group) {
        if (group == null || group.trim().isEmpty()) {
            return new ValidationResult(false, "Група не може бути порожньою.");
        }
        if (!group.matches(GROUP_PATTERN)) {
            return new ValidationResult(false, "Група повинна відповідати формату: дві літери, дві цифри та одна літера (наприклад, AB12c).");
        }
        return new ValidationResult(true, "Група валідна.");
    }

    public static ValidationResult validateYear(String year) {
        if (year == null || year.trim().isEmpty()) {
            return new ValidationResult(false, "Рік не може бути порожнім.");
        }
        if (!year.matches(YEAR_PATTERN)) {
            return new ValidationResult(false, "Рік повинен містити 4 цифри.");
        }
        int yearValue = Integer.parseInt(year);
        if (yearValue < YEAR_MIN_VALUE || yearValue > YEAR_MAX_VALUE) {
            return new ValidationResult(false, "Рік повинен бути в діапазоні від " + YEAR_MIN_VALUE + " до " + YEAR_MAX_VALUE + ".");
        }
        return new ValidationResult(true, "Рік валідний.");
    }

    // Клас для представлення результату валідації
    public static class ValidationResult {
        private final boolean isValid;
        private final String message;

        public ValidationResult(boolean isValid, String message) {
            this.isValid = isValid;
            this.message = message;
        }

        public boolean isValid() {
            return isValid;
        }

        public String getMessage() {
            return message;
        }
    }
}
