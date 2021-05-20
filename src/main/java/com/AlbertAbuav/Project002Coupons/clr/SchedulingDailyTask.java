package com.AlbertAbuav.Project002Coupons.clr;

import com.AlbertAbuav.Project002Coupons.beans.Company;
import com.AlbertAbuav.Project002Coupons.beans.Coupon;
import com.AlbertAbuav.Project002Coupons.exception.invalidAdminException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCompanyException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCustomerException;
import com.AlbertAbuav.Project002Coupons.login.ClientType;
import com.AlbertAbuav.Project002Coupons.login.LoginManager;
import com.AlbertAbuav.Project002Coupons.service.AdminService;
import com.AlbertAbuav.Project002Coupons.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Order(6)
public class SchedulingDailyTask implements CommandLineRunner {

    private final FactoryUtils factoryUtils;
    private final ChartUtils chartUtils;

    private final LoginManager loginManager;
    private AdminService adminService;

    @Override
    public void run(String... args) {

        Colors.setYellowBoldPrint(ArtUtils.SCHEDULING_DAILY_TASK);

        TestUtils.testAdminInfo("Connecting to AdminService");

        try {
            adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Inserting new Coupons with Expired Dats:");
        System.out.println();

        Coupon couponTo4 = factoryUtils.createCouponOfACompany(4);
        couponTo4.setEndDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(1)));
        Coupon couponTo5 = factoryUtils.createCouponOfACompany(5);
        couponTo5.setEndDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(1)));
        Coupon couponTo6 = factoryUtils.createCouponOfACompany(6);
        couponTo6.setEndDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(1)));
        Coupon couponTo7 = factoryUtils.createCouponOfACompany(7);
        couponTo7.setEndDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(1)));

        Company companyId4 = null;
        Company companyId5 = null;
        Company companyId6 = null;
        Company companyId7 = null;
        try {
            companyId4 = adminService.getSingleCompany(4);
            companyId5 = adminService.getSingleCompany(5);
            companyId6 = adminService.getSingleCompany(6);
            companyId7 = adminService.getSingleCompany(7);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        companyId4.getCoupons().add(couponTo4);
        companyId5.getCoupons().add(couponTo5);
        companyId6.getCoupons().add(couponTo6);
        companyId7.getCoupons().add(couponTo7);

        try {
            adminService.updateCompany(companyId4);
            adminService.updateCompany(companyId5);
            adminService.updateCompany(companyId6);
            adminService.updateCompany(companyId7);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("The companies with the expired coupon after the injecting them to the data base:");
        try {
            chartUtils.printCompany(adminService.getSingleCompany(4));
            chartUtils.printCompany(adminService.getSingleCompany(5));
            chartUtils.printCompany(adminService.getSingleCompany(6));
            chartUtils.printCompany(adminService.getSingleCompany(7));
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Setting Coupons the Customers purchased to be expired:");
        System.out.println();
        Company companyId2 = null;
        try {
            companyId2 = adminService.getSingleCompany(2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        companyId2.getCoupons().get(1).setEndDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(1)));
        companyId2.getCoupons().get(2).setEndDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(1)));

        try {
            adminService.updateCompany(companyId2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("The company with the updated coupons to be expired:");

        try {
            chartUtils.printCompany(adminService.getSingleCompany(2));
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        Colors.separation();
        Colors.setRedBoldBrightPrint("End");

    }


}
