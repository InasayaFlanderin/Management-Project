package com.myteam.work.management.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users extends Humanity{
    private int id;
    private Authentication auth;
    private boolean ur;
    private Department place;

}
