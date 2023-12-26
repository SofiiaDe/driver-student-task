package com.task.students;

import lombok.*;

import java.util.Objects;

/**
 *  StudentHashCodeSample is an alternative for Student class. This implementation of the equals() and hashCode() methods
 *  uses only lastName field to compare objects of StudentHashCodeSample type.
 */
@Getter
@AllArgsConstructor
@Builder
public class StudentHashCodeSample {

    private String name;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentHashCodeSample studentHashCodeSample = (StudentHashCodeSample) o;
        return Objects.equals(lastName, studentHashCodeSample.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName);
    }
}