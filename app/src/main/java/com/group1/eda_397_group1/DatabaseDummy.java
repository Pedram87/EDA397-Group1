package com.group1.eda_397_group1;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Andam on 26/04/16.
 */
public class DatabaseDummy {
    private  static DatabaseDummy instance;
    private static ArrayList<Task> tasks  = null;
    private static ArrayList<User> users = null;




    public static DatabaseDummy getInstance(){
        if(instance == null){
            instance = new DatabaseDummy();
            tasks = populateTask();
            users = populateUsers();
        }

        return  instance;
    }

    public DatabaseDummy(){

    }

    private static ArrayList<Task> populateTask(){
        ArrayList<Task> taskList = new ArrayList<>();

        Task task1 = new Task("Create login UI", 6, new User("a@b.c", "Mustafa", "Husseini"),
                new User("g@b.c", "Berima", "Andam"), new User("l.g@m.c", "First", "Man"));

        Task task2 = new Task("Design Database", 8, new User("mus@bs.cd", "Berima", "Andam"),
                new User("g@b.c", "Berima", "Andam"), new User("sfs@", "Maximillian", "Berger"));

        Task task3 = new Task("Refactor Backend Design", 2, new User("ddt@bs.cd", "Joseph", "Martins"),
                new User("gssf@b.c", "William", "Facebook"), new User("ksf@gkj.com", "Michel", "Book"));

        Task task4 = new Task("Write Tests", 7, new User("ddt@bs.cd", "Joseph", "Martins"),
                new User("gssf@b.c", "William", "Facebook"), new User("ksf@gkj.com", "Michel", "Book"));

        Task task5 = new Task("Refactor Backend Design", 2, new User("ddt@bs.cd", "Joseph", "Martins"),
                new User("gssf@b.c", "William", "Facebook"), new User("ksf@gkj.com", "Michel", "Book"));
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
        taskList.add(task5);
        return taskList;
    }


    private static ArrayList<User> populateUsers(){
        ArrayList users = new ArrayList();

        User user1 = new User("j@c.com","Jesper","Karlberg");
        User user2 = new User("b@a.com","Berima","Andam");
        User user3 = new User("m@h.com","Mustafa","Hussein");
        User user4 = new User("u@4.d","Danny","Wilcox");
        User user5 = new User("f@t.com","Felix","Thomas");
        User user6 = new User("t@m","Tim","Malcolm");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        return users;
    }

    public ArrayList<Task> getTasks(){
        return tasks;
    }
    public ArrayList<User> getUsers(){
        return users;
    }

    public static void addToDatabase(Task task){
        tasks.add(task);
    }

    public static void addToDatabase(User user){
        users.add(user);
    }

    public static void detletFromDatabase(Task task){
        tasks.remove(task);
    }

    public static void deletFromDatabse(User user){
        users.remove(user);
    }
}
