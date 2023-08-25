package com.minipro2.minipro2.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ELIGIBILITY_DETAILS")
@Data
public class EligibilityDetails  {

    @Id
    @GeneratedValue
    private Integer eligId;
    private String name;
    private Long mobile;
    private String email;
    private Character gender;
    private Long ssn;
    private String planName;
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private LocalDate createDate;
    private LocalDate updateDate;
    private String createdBy;
    private String updatedBy;

}
