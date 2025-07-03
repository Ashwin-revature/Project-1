package org.example.service;

public class StudentServiceFactory {

    private StudentServiceFactory() {}

    public static StudentService createStudentService() {
        return new StudentServiceImpl();
    }
}