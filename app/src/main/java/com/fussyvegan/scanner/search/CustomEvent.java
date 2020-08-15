package com.fussyvegan.scanner.search;

public class CustomEvent {
    public boolean isResort;
    public boolean checkBox1;
    public boolean checkBox2;
    public boolean checkBox3;
    public boolean checkBox4;
    public boolean checkBox5;
    public boolean checkBox6;

    public CustomEvent(boolean isResort, boolean checkBox1, boolean checkBox2, boolean checkBox3,
                       boolean checkBox4, boolean checkBox5,  boolean checkBox6){
        this.isResort = isResort;
        this.checkBox1 = checkBox1;
        this.checkBox2 = checkBox2;
        this.checkBox3 = checkBox3;
        this.checkBox4 = checkBox4;
        this.checkBox5 = checkBox5;
        this.checkBox6 = checkBox6;
    }

    public boolean isResort() {
        return isResort;
    }

    public boolean isCheckBox1() {
        return checkBox1;
    }

    public boolean isCheckBox2() {
        return checkBox2;
    }

    public boolean isCheckBox3() {
        return checkBox3;
    }

    public boolean isCheckBox4() {
        return checkBox4;
    }

    public boolean isCheckBox5() {
        return checkBox5;
    }

    public boolean isCheckBox6() {
        return checkBox6;
    }
}
