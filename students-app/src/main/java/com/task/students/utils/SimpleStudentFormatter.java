package com.task.students.utils;

import com.task.students.Student;

public class SimpleStudentFormatter implements IStudentDataFormatter {
    @Override
    public String format(Student student) {
        return student.getName() + " " + student.getLastName();
    }
}
