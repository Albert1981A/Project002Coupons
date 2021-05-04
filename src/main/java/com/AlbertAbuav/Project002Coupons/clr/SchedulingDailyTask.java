package com.AlbertAbuav.Project002Coupons.clr;

import com.AlbertAbuav.Project002Coupons.utils.ArtUtils;
import com.AlbertAbuav.Project002Coupons.utils.Colors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(6)
public class SchedulingDailyTask implements CommandLineRunner {

    @Override
    public void run(String... args) {

        Colors.setYellowBoldPrint(ArtUtils.SCHEDULING_DAILY_TASK);


        Colors.separation();
        Colors.setRedBoldBrightPrint("End");

    }


}
