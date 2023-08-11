package com.jafar.partthree.controller;

import com.jafar.DB;
import com.jafar.StudentGrade;
import com.jafar.partthree.model.AddGradeRequestModel;
import com.jafar.partthree.model.DeleteGradeRequestModel;
import com.jafar.partthree.model.GradesRequestModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GradesController {
    private final DB db = new DB();
    @PostMapping("/grades")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<StudentGrade> getGrades(@RequestBody GradesRequestModel request) {
        return db.getStudentGrades(request.studentId(), request.courseId());
    }

    @PostMapping("/grade")
    @CrossOrigin(origins = "http://localhost:3000")
    public boolean getGrades(@RequestBody AddGradeRequestModel addGradeRequestModel) {
        return db.addGrade(Integer.parseInt(addGradeRequestModel.studentId()),
                Integer.parseInt(addGradeRequestModel.courseId()),
                Integer.parseInt(addGradeRequestModel.grade()));
    }
    @DeleteMapping("/grade")
    @CrossOrigin(origins = "http://localhost:3000")
    public boolean getGrades(@RequestBody DeleteGradeRequestModel deleteGradeRequestModel) {
        return db.deleteGrade(Integer.parseInt(deleteGradeRequestModel.studentId()), Integer.parseInt(deleteGradeRequestModel.courseId()));
    }
}
