package com.myteam.work;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.myteam.work.management.data.Information;
import com.myteam.work.management.data.Semester;
import com.myteam.work.management.data.Student;
import com.myteam.work.management.data.Subject;
import com.myteam.work.management.data.TeachClass;
import com.myteam.work.management.data.User;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Test
    void TestSubject()
    {
        Subject s  = new Subject(1, (short)3, true, "Data Structures", Arrays.asList(0));
        assertEquals(1, s.getId());
        assertEquals(3, s.getCredits());
        assertEquals(true, s.isRequired());
        assertEquals("Data Structures", s.getSubjectName());
        assertEquals(Arrays.asList(0), s.getPrerequisites());
    }

    @Test
    void TestSemester()
    {
        Semester sem = new Semester(2, (short)1, (short)2024);
        assertEquals(2, sem.getId());
        assertEquals(1, sem.getSemester());
        assertEquals(2024, sem.getYear());
    }

    @Test
    void TestStudent()
    {
        Student st = new Student(1, "Alice", "01", "01", "2000", "Wonderland", false, (short)1, 2.0f);
        assertEquals(1, st.getId());
        assertEquals("Alice", st.getInfo().getName());
        assertEquals("Wonderland", st.getInfo().getPlaceOfBirth());
        assertEquals(false, st.getInfo().isSex());
        assertEquals(1, st.getGeneration());
        assertEquals(2.0f, st.getGpa());
    }

    @Test
    void TestTeachClass()
    {
        TeachClass tc = new TeachClass(1, 1, "Class A", 101, 3.5f,
            Arrays.asList(1, 2), 
            Arrays.asList(8.0f, 9.0f), 
            Arrays.asList(7.5f, 8.5f), 
            Arrays.asList(9.0f, 10.0f), 
            Arrays.asList(8.5f, 9.5f), 
            Arrays.asList(8.0f, 9.0f), 
            Arrays.asList("A", "A"));

        assertEquals(1, tc.getId());
        assertEquals(1, tc.getSemester());
        assertEquals("Class A", tc.getClassName());
        assertEquals(101, tc.getSubject());
        assertEquals(3.5f, tc.getGpa());
        assertEquals(Arrays.asList(1, 2), tc.getStudents());
        assertEquals(Arrays.asList(8.0f, 9.0f), tc.getFirstTests());
        assertEquals(Arrays.asList(7.5f, 8.5f), tc.getSecondTests());
        assertEquals(Arrays.asList(9.0f, 10.0f), tc.getEndTests());
        assertEquals(Arrays.asList(8.5f, 9.5f), tc.getScores());
        assertEquals(Arrays.asList(8.0f, 9.0f), tc.getNormalizedScores());
        assertEquals(Arrays.asList("A", "A"), tc.getRates());
    }

    @Test
    void TestUser()
    {
        User u = new User(1, "Bob", "02", "02", "1990", "Builderland", true, "bob123", "securePass", false, Arrays.asList(1, 2), Arrays.asList(101, 102));
        assertEquals(1, u.getId());
        assertEquals("Bob", u.getInfo().getName());
        assertEquals("Builderland", u.getInfo().getPlaceOfBirth());
        assertEquals(true, u.getInfo().isSex());
        assertEquals("bob123", u.getAuthName());
        assertEquals("securePass", u.getAuthPassword());
        assertEquals(false, u.isRole());
        assertEquals(Arrays.asList(1, 2), u.getSubjects());
        assertEquals(Arrays.asList(101, 102), u.getClasses());
    }

    @Test
    void TestInformation()
    {
        Information info = new Information("Charlie", "03", "03", "1985", "Chocolate Factory", true);
        assertEquals("Charlie", info.getName());
        assertEquals("03-03-1985", info.getBirth().toString());
        assertEquals("Chocolate Factory", info.getPlaceOfBirth());
        assertEquals(true, info.isSex());
    }
}

