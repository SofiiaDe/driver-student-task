package com.task.students;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Data lombok annotation contains implicit @EqualsAndHashCode which uses all non-static, non-transient fields (i.e., name and lastName)
 */
@Data
@AllArgsConstructor
@Builder
public class Student {

    private String name;
    private String lastName;

}
