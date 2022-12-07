package com.team.rocket.springdemo.controller;

import com.team.rocket.springdemo.model.Student;
import com.team.rocket.springdemo.service.StudentService;
import com.team.rocket.springdemo.service.WebsocketSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {

    private final StudentService studentService;
    @Autowired
    private WebsocketSevice websocketSevice;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping
    public void createStudent(@RequestBody Student student) throws Exception{
        studentService.createStudent(student);
        websocketSevice.sendNewStudent(student);
    }

    @DeleteMapping(path="{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

}
