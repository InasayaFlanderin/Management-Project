package com.myteam.work.management.data;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Humanity {
    private int id;
    private String urName;
    private LocalDate birth;
    private String placeOfBirth;
    private boolean sex;
    
}
