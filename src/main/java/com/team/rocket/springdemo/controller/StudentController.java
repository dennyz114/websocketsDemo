package com.team.rocket.springdemo.controller;

import com.team.rocket.springdemo.model.Student;
import com.team.rocket.springdemo.service.StudentService;
import com.team.rocket.springdemo.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {

    private final StudentService studentService;
    private final WebsocketService websocketService;

    @Autowired
    public StudentController(StudentService studentService, WebsocketService websocketService) {
        this.studentService = studentService;
        this.websocketService = websocketService;
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping
    public void createStudent(@RequestBody Student student) throws Exception{
        studentService.createStudent(student);
        websocketService.sendNewStudent(student);
    }

    @DeleteMapping(path="{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

}
