package com.nianien.test.bean;

import java.util.Date;

import lombok.Data;

/**
 * @author skyfalling
 */
@Data
public class Student extends People {

    private String school;
    private String major;
    private Date admissionTime;
    private Date graduationTime;

}
