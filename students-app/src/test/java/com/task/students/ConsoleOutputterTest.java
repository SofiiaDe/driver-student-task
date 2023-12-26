package com.task.students;

import com.task.students.utils.ConsoleOutputter;
import com.task.students.utils.IOutputter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleOutputterTest {

    private ByteArrayOutputStream outputStreamCaptor;
    private IOutputter consoleOutputter;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        consoleOutputter = new ConsoleOutputter();
    }

    @Test
    @DisplayName("Test print method")
    void testPrint() {
        System.setOut(new PrintStream(outputStreamCaptor));
        String text = "Hello, world!";
        consoleOutputter.print(text);
//        assertEquals(text + "\n", outputStreamCaptor.toString());
        assertEquals(text, outputStreamCaptor.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "Hello, world!", "12345"})
    @DisplayName("Test print method with different inputs")
    void testPrintWithDifferentInputs(String text) {
        System.setOut(new PrintStream(outputStreamCaptor));
        consoleOutputter.print(text);
//        assertEquals(text + "\n", outputStreamCaptor.toString());
        assertEquals(text, outputStreamCaptor.toString());
    }


}
