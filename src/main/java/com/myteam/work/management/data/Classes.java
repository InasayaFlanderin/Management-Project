package com.myteam.work.management.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classes {
    private int id;
    private String className;
    private boolean classType;
    private Subject subject;
}
