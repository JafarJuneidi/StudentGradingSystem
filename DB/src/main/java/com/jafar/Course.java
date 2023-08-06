package com.jafar;

import java.io.Serializable;

public class Course implements Serializable {
    private final int id;
    private final String name;
    private final String instructor;

    public Course(int id, String name, String instructor) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';
    }
}