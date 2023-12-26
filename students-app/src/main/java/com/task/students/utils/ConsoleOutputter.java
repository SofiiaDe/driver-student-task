package com.task.students.utils;

public class ConsoleOutputter implements IOutputter {
    @Override
    public void print(String text) {
        System.out.println(text);
    }
}
