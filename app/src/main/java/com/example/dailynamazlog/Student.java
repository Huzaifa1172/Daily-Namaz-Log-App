package com.example.dailynamazlog;

public class Student {
    public int id;
    public String name;
    public String father_name;
    public String rollnum;

    public Student(String name, String father_name, String rollnum) {
        this.name = name;
        this.father_name = father_name;
        this.rollnum = rollnum;
    }

    public Student(int id, String name, String father_name, String rollnum) {
        this.id = id;
        this.name = name;
        this.father_name = father_name;
        this.rollnum = rollnum;
    }

}