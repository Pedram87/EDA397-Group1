package com.group1.eda_397_group1;

/**
 * Created by jesper on 2016-04-19.
 */
public class User {

    private String firstName;
    private String lastName;
    private String email;

    User(String email, String firstName, String lastName){

        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
