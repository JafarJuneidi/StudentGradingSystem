package com.jafar;

import java.io.Serializable;

public record StudentGrade(String studentName, String courseName, String instructorName,
                           String grade) implements Serializable {
}
