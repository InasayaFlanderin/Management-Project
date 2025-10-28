package com.myteam.work.management.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prequisite {
    private Subject subject;
    private Subject require;
}
