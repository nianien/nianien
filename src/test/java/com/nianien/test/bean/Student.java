package com.nianien.test.bean;

import java.util.Date;

/**
 * @author skyfalling
 */
public class Student extends People {

    private String school;
    private String major;
    private Date admissionTime;
    private Date graduationTime;

    public Student(long id) {
        super(id);
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Date getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(Date admissionTime) {
        this.admissionTime = admissionTime;
    }

    public Date getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(Date graduationTime) {
        this.graduationTime = graduationTime;
    }


}
