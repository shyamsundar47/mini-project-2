package com.minipro2.minipro2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minipro2.minipro2.model.EligibilityDetails;

@Repository
public interface EligibilityDetailsRepo extends JpaRepository<EligibilityDetails,Integer>{
    @Query(value="SELECT DISTINCT(plan_name) from eligibility_details" ,nativeQuery = true)
    public List<String> findAllPlanNames();

    @Query(value="SELECT DISTINCT(plan_status) from eligibility_details",nativeQuery = true)
    public List<String> findAllPlanStatus();

}
