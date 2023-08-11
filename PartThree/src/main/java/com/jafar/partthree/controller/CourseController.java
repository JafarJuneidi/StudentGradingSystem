package com.jafar.partthree.controller;

import com.jafar.Course;
import com.jafar.DB;
import com.jafar.StudentGrade;
import com.jafar.partthree.model.AddCourseRequestModel;
import com.jafar.partthree.model.CourseModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseController {
    private final DB db = new DB();

    @GetMapping("/courses")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<CourseModel> getCourses() {
        List<Course> courses = db.getCourses(null);

        return courses.stream()
                .map(course -> {
                    List<StudentGrade> grades = db.getStudentGrades(null, Integer.toString(course.id()));
                    double total = grades.stream()
                            .mapToDouble(grade -> Double.parseDouble(grade.grade()))
                            .sum();
                    double average = grades.isEmpty() ? 0 : total / grades.size();
                    return new CourseModel(Integer.toString(course.id()), course.name(), course.instructor(), Integer.toString(grades.size()), Double.toString(average));
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/course")
    @CrossOrigin(origins = "http://localhost:3000")
    public boolean addCourse(@RequestBody AddCourseRequestModel addCourseRequestModel) {
        return db.addCourse(addCourseRequestModel.courseName(),
                Integer.parseInt(addCourseRequestModel.instructorId()));
    }
}
