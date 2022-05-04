package com.example.sendiassignment.utils;

public enum CreatedTime {
    DAY(1, "Day"), WEEK(7, "Week"), MONTH(30, "Month");

    CreatedTime(int days, String displayText) {
        this.numOfDays = days;
        this.displayText = displayText;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public String getDisplayText() {
        return displayText;
    }

    private final int numOfDays;
    private final String displayText;
}
