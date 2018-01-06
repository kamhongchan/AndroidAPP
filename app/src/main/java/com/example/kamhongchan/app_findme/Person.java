package com.example.kamhongchan.app_findme;

/**
 * Created by kamhongchan on 2018/1/7.
 */

public class Person {
    private String name;
    private String phoneNumber;

    public Person(String name, String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
