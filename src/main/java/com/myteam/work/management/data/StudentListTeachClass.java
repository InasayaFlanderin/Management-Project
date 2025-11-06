package com.myteam.work.management.data;

import java.util.Scanner;

public class StudentListTeachClass {
    private Student student;
    private TeachClass classes;
    private float test1;
    private float test2;
    private float endTest;
    private float score;
    private float normalizedScore ;
    private String rate;
    public StudentListTeachClass() {
    }
    public StudentListTeachClass(Student student, TeachClass classes, float test1, float test2, float endTest,
            float score, float normalizedScore, String rate) {
        this.student = student;
        this.classes = classes;
        this.test1 = test1;
        this.test2 = test2;
        this.endTest = endTest;
        this.score = score;
        this.normalizedScore = normalizedScore;
        this.rate = rate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TeachClass getClasses() {
        return classes;
    }

    public void setClasses(TeachClass classes) {
        this.classes = classes;
    }

    public float getTest1() {
        return test1;
    }

    public void setTest1(float test1) {
        this.test1 = test1;
    }

    public float getTest2() {
        return test2;
    }

    public void setTest2(float test2) {
        this.test2 = test2;
    }

    public float getEndTest() {
        return endTest;
    }

    public void setEndTest(float endTest) {
        this.endTest = endTest;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getNormalizedScore() {
        return normalizedScore;
    }

    public void setNormalizedScore(float normalizedScore) {
        this.normalizedScore = normalizedScore;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public static float Score()
    {
        float test1, test2, endTest;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter test1's score: ");
        test1 = scanner.nextFloat();

        System.out.println("Enter test2's score: ");
        test2 = scanner.nextFloat();

        System.out.println("Enter endTest's score: ");
        endTest = scanner.nextFloat();

        float calculateScore = 0;

        calculateScore = 0.25f*test1 + 0.25f*test2 + 0.5f*endTest;

        return calculateScore;

    }

    public static float NormalizedScore(){
        if(Score() >= 8.5)
            return 4.0f;
        if(Score() >= 8.0)
            return 3.5f;
        if(Score() >= 7.0)
            return 3.0f;
        if(Score() >= 6.5)
            return 2.5f;
        if(Score() >= 5.5)
            return 2.0f;
        if(Score() >= 5.0)
            return 1.5f;
        if(Score() >= 4.0)
            return 1.0f;
        return 0;
    }

    public static String Rate(){
        if(Score() >= 8.5)
            return "A";
        if(Score() >= 8.0)
            return "B+";
        if(Score() >= 7.0)
            return "B";
        if(Score() >= 6.5)
            return "C+";
        if(Score() >= 5.5)
            return "C";
        if(Score() >= 5.0)
            return "D+";
        if(Score() >= 4.0)
            return "D";
        return "F";
    }
}
