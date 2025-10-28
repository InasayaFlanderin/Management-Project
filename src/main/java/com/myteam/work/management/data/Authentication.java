package com.myteam.work.management.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authentication {
    private String authName;
    private String authPass;
    
}
