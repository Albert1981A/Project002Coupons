package com.AlbertAbuav.Project002Coupons.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class DailyExpiredCoupons {

    @Scheduled(fixedRate = 1000*3 /*1000*60*60*24*/)
    public void doTheThingEveryDay() {
        System.out.println("Moshe");
    }

}
