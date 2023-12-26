package com.task.students;

import com.task.students.utils.IOutputter;
import com.task.students.utils.IStudentDataFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentTest {

    private Department department;

    @Mock
    private IOutputter iOutputter;

    @Mock
    private IStudentDataFormatter studentDataFormatter;

    @BeforeEach
    void setUp() {
        department = new Department(iOutputter, studentDataFormatter);
    }

    @Test
    @DisplayName("Test addStudentGrade method")
    void testAddStudentGrade() {
        Student student = new Student("John", "Doe");
        Integer grade = 90;
        department.addStudentGrade(student, grade);
        Map<Student, Integer> expectedGradesMap = new HashMap<>();
        expectedGradesMap.put(student, grade);
        assertNotNull(expectedGradesMap);
        assertEquals(1, expectedGradesMap.size());
        assertEquals(90, expectedGradesMap.get(student));
    }

    @Test
    @DisplayName("Test printAllStudentsGrades method")
    void testPrintAllStudentsGrades() {
        Student student1 = new Student("John", "Doe");
        Student student2 = new Student("Jane", "Tomson");
        Integer grade1 = 90;
        Integer grade2 = 80;
        department.addStudentGrade(student1, grade1);
        department.addStudentGrade(student2, grade2);
        when(studentDataFormatter.format(student1)).thenReturn("John Doe");
        when(studentDataFormatter.format(student2)).thenReturn("Jane Tomson");
        department.printAllStudentsGrades();
        verify(iOutputter).print("John Doe: 90");
        verify(iOutputter).print("Jane Tomson: 80");
    }

    @Test
    @DisplayName("Test printAllStudentsGrades method when same last name")
    void testPrintAllStudentsGradesSameLastName() {
        Student student1 = new Student("John", "Doe");
        Student student2 = new Student("Jane", "Doe");
        Integer grade1 = 90;
        Integer grade2 = 80;
        department.addStudentGrade(student1, grade1);
        department.addStudentGrade(student2, grade2);
        when(studentDataFormatter.format(student2)).thenReturn("Jane Doe");
        department.printAllStudentsGrades();
        verify(iOutputter).print("Jane Doe: 80");
    }

    @ParameterizedTest
    @CsvSource({
            "Doe, John",
            "Doe, Jane",
            "Smith, John"
    })
    @DisplayName("Test removeStudentIfLastNameExist method")
    void testRemoveStudentIfLastNameExist(String name, String lastName) {
        Student student1 = new Student("John", "Doe");
        Student student2 = new Student("Jane", "Doe");
        Integer grade1 = 90;
        Integer grade2 = 80;
        department.addStudentGrade(student1, grade1);
        department.addStudentGrade(student2, grade2);
        Map<Student, Integer> studentGradesMap = new HashMap<>();
        studentGradesMap.put(student1, grade1);
        studentGradesMap.put(student2, grade2);
        Optional<Student> optionalStudent = studentGradesMap.keySet().stream()
                .filter(s -> s.getLastName().equals(lastName))
                .findFirst();
        assertFalse(optionalStudent.isPresent());
    }

}