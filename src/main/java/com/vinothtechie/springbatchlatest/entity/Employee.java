package com.vinothtechie.springbatchlatest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_details")
public class Employee {

    @Id
    private String employeeId;
    private String fullName;
    private String jobTitle;
    private String department;
    private String businessUnit;
    private String gender;
    private String ethnicity;
    private String age;


}
