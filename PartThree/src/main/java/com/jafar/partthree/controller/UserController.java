package com.jafar.partthree.controller;

import com.jafar.DB;
import com.jafar.StudentGrade;
import com.jafar.User;
import com.jafar.partthree.model.AddUserRequestModel;
import com.jafar.partthree.model.AuthRequestModel;
import com.jafar.partthree.model.StudentModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    final DB db = new DB();

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestModel user) {
        Optional<User> userOptional = db.login(user.username(), user.password());
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Unauthorized");
        }
    }

    @GetMapping("/students")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<StudentModel> getStudents() {
        List<User> students = db.getUsers("3");

        return students.stream()
                .map(student -> {
                    List<StudentGrade> grades = db.getStudentGrades(student.id(), null);
                    double total = grades.stream()
                            .mapToDouble(grade -> Double.parseDouble(grade.grade()))
                            .sum();
                    double average = grades.isEmpty() ? 0 : total / grades.size();
                    return new StudentModel(student.id(), student.name(), Integer.toString(grades.size()), Double.toString(average));
                })
                .collect(Collectors.toList());
    }


    @PostMapping("/user")
    @CrossOrigin(origins = "http://localhost:3000")
    public boolean addUser(@RequestBody AddUserRequestModel addUserRequestModel) {
        return db.addUser(addUserRequestModel.username(), addUserRequestModel.password(), Integer.parseInt(addUserRequestModel.role()));
    }

    @GetMapping("/users")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<User> getUsers() {
        return db.getUsers(null);
    }

    @DeleteMapping("/user/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public boolean deleteUser(@PathVariable int id) {
        return db.deleteUser(id);
    }
}

