package com.task.students;

import com.task.students.utils.IOutputter;
import com.task.students.utils.IStudentDataFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Department {

    private Map<Student, Integer> studentGradesMap;
    private final IOutputter iOutputter;
    private final IStudentDataFormatter studentDataFormatter;

    public Department(IOutputter iOutputter, IStudentDataFormatter studentDataFormatter) {
        this.iOutputter = iOutputter;
        this.studentDataFormatter = studentDataFormatter;
    }

    private Map<Student, Integer> getStudentGradesMap() {
        if (studentGradesMap == null) {
            studentGradesMap = new HashMap<>();
        }
        return studentGradesMap;
    }

    public void addStudentGrade(Student student, Integer grade) {
        removeStudentIfLastNameExist(student.getLastName());
        getStudentGradesMap().put(student, grade);
    }

    public void printAllStudentsGrades() {
        studentGradesMap.forEach((key, value) -> iOutputter.print(
                studentDataFormatter.format(key) + ": " + value));
    }

    private void removeStudentIfLastNameExist(String lastName) {
        Optional<Student> optionalStudent = getStudentGradesMap().keySet().stream()
                .filter(s -> s.getLastName().equals(lastName))
                .findFirst();
        optionalStudent.ifPresent(value -> getStudentGradesMap().remove(value));
    }

}