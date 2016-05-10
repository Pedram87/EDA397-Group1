package com.group1.eda_397_group1;

/**
 * Created by Max on 10.05.2016.
 */
public class UserSingleton {

    private static UserSingleton instance;
    private static String email;
//    private String name;

    private UserSingleton() {}

    private UserSingleton(String email/*, String name*/) {
        email = email;
//        name = name;
    }

    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

    public static void setEmail(String email) {
        instance.email = email;
    }
//
//    public static void setName(String name) {
//        instance.name = name;
//    }

    public static String getEmail() {
        return email;
    }
}
