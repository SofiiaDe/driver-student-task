package com.task.students;

import com.task.students.utils.ConsoleOutputter;
import com.task.students.utils.IOutputter;
import com.task.students.utils.IStudentDataFormatter;
import com.task.students.utils.SimpleStudentFormatter;

public class Main {
    public static void main(String[] args) {
        IOutputter outputter = new ConsoleOutputter();
        IStudentDataFormatter studentDataFormatter = new SimpleStudentFormatter();
        Department department = new Department(outputter, studentDataFormatter);

        Student kateBerry = Student.builder()
                .name("Kate")
                .lastName("Berry")
                .build();
        department.addStudentGrade(kateBerry, 97);

        Student tomSmith = Student.builder()
                .name("Tom")
                .lastName("Smith")
                .build();
        department.addStudentGrade(tomSmith, 95);

        Student andrewLey = Student.builder()
                .name("Andrew")
                .lastName("Ley")
                .build();
        department.addStudentGrade(andrewLey, 81);
        department.addStudentGrade(andrewLey, 92);

        department.printAllStudentsGrades();

        StudentHashCodeSample emmyHill = StudentHashCodeSample.builder()
                .name("Emmy")
                .lastName("Hill")
                .build();
        StudentHashCodeSample tonnyHill = StudentHashCodeSample.builder()
                .name("Tonny")
                .lastName("Hill")
                .build();
        System.out.println(emmyHill.hashCode() == tonnyHill.hashCode());
    }
}