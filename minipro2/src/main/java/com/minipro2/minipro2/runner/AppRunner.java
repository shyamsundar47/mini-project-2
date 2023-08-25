package com.minipro2.minipro2.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.minipro2.minipro2.repository.EligibilityDetailsRepo;

@Component
public class AppRunner implements ApplicationRunner{

    @Autowired
    EligibilityDetailsRepo repo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // EligibilityDetails detail = new EligibilityDetails();
        // detail.setName("Shyamsundar");
        // detail.setMobile((long) 987456321);
        // detail.setEmail("shyam@gmail.com");
        // detail.setGender('M');
        // detail.setSsn((long) 123654789);
        // detail.setPlanName("Medical");
        // detail.setPlanStatus("Approved");
        // repo.save(detail);
        
        // EligibilityDetails detail1 = new EligibilityDetails();
        // detail1.setName("Sarvesh");
        // detail1.setMobile((long) 789585848);
        // detail1.setEmail("sarvesh@gmail.com");
        // detail1.setGender('M');
        // detail1.setSsn((long) 896585698);
        // detail1.setPlanName("Motor");
        // detail1.setPlanStatus("denied");
        // repo.save(detail1);
    }
    
}
