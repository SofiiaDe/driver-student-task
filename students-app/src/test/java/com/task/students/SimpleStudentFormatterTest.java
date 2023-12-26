package com.task.students;

import com.task.students.utils.IStudentDataFormatter;
import com.task.students.utils.SimpleStudentFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleStudentFormatterTest {

    private IStudentDataFormatter simpleStudentFormatter;

    @BeforeEach
    void setUp() {
        simpleStudentFormatter = new SimpleStudentFormatter();
    }

    @ParameterizedTest
    @CsvSource({
            "John, Doe, John Doe",
            "Jane, Smith, Jane Smith",
            "Bob, Johnson, Bob Johnson"
    })
    @DisplayName("Test format method")
    void testFormat(String firstName, String lastName, String expectedFormattedName) {
        Student student = new Student(firstName, lastName);
        String formattedName = simpleStudentFormatter.format(student);
        assertEquals(expectedFormattedName, formattedName);
    }

}
