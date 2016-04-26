package com.group1.eda_397_group1;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Andam on 26/04/16.
 */
public class DatabaseDummy {
    private  DatabaseDummy instance;
    private ArrayList<Task> tasks  = null;



    private DatabaseDummy(){
        tasks = populateDatabase();
    }

    public DatabaseDummy getInstance(){
        if(instance == null){
            instance = new DatabaseDummy();
        }

        return  instance;
    }

    public ArrayList<Task> populateDatabase(){
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
        return taskList;
    }


    public void addToDatabase(Task task){
        this.tasks.add(task);
    }

    public void detletFromDatabase(Task task){
        this.tasks.remove(task);
    }
}
