package com.myteam.work;

import com.myteam.work.management.dao.SubjectDao;
import com.myteam.work.management.data.Subject;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String[] args) {
        Subject s = new Subject((short) 3, true, "DSA");
        SubjectDao.getInstance().insert(s);
    }
}
