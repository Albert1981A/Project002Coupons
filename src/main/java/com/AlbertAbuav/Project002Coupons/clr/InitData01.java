package com.AlbertAbuav.Project002Coupons.clr;

import com.AlbertAbuav.Project002Coupons.beans.Company;
import com.AlbertAbuav.Project002Coupons.beans.Coupon;
import com.AlbertAbuav.Project002Coupons.beans.Customer;
import com.AlbertAbuav.Project002Coupons.exception.invalidAdminException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCompanyException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCustomerException;
import com.AlbertAbuav.Project002Coupons.login.ClientType;
import com.AlbertAbuav.Project002Coupons.login.LoginManager;
import com.AlbertAbuav.Project002Coupons.service.AdminService;
import com.AlbertAbuav.Project002Coupons.service.CompanyService;
import com.AlbertAbuav.Project002Coupons.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(1)
public class InitData01 implements CommandLineRunner {

    private final FactoryUtils factoryUtils;
    private final ChartUtils chartUtils;

    /**
     * Invoking the LoginManager to login to the requested facade
     */
    private final LoginManager loginManager;
    private AdminService adminService;

    @Override
    public void run(String... args) {

        Colors.setRedBoldBrightPrint("START");
        Colors.separation();

        Colors.setGreenBoldPrint(ArtUtils.INIT_DATA_01);

        TestUtils.testAdminInfo("Adding Companies");

        try {
            adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        Company company1 = factoryUtils.createCompany();
        Company company2 = factoryUtils.createCompany();
        Company company3 = factoryUtils.createCompany();
        Company company4 = factoryUtils.createCompany();
        Company company5 = factoryUtils.createCompany();

        Coupon coupon1 = factoryUtils.createCouponOfACompany(1);
        Coupon coupon2 = factoryUtils.createCouponOfACompany(1);
        Coupon coupon3 = factoryUtils.createCouponOfACompany(2);
        Coupon coupon4 = factoryUtils.createCouponOfACompany(2);
        Coupon coupon5 = factoryUtils.createCouponOfACompany(3);
        Coupon coupon6 = factoryUtils.createCouponOfACompany(3);

        List<Coupon> coupons1 = new ArrayList<>(Arrays.asList(coupon1, coupon2));
        List<Coupon> coupons2 = new ArrayList<>(Arrays.asList(coupon3, coupon4));
        List<Coupon> coupons3 = new ArrayList<>(Arrays.asList(coupon5, coupon6));

        company1.setCoupons(coupons1);
        company2.setCoupons(coupons2);
        company3.setCoupons(coupons3);

        adminService.addCompany(company1);
        adminService.addCompany(company2);
        adminService.addCompany(company3);
        adminService.addCompany(company4);
        adminService.addCompany(company5);

        chartUtils.printCompanies(adminService.getAllCompanies());

        TestUtils.testAdminInfo("Adding Customers");

        Customer customer1 = factoryUtils.createCustomer();
        Customer customer2 = factoryUtils.createCustomer();
        Customer customer3 = factoryUtils.createCustomer();
        Customer customer4 = factoryUtils.createCustomer();
        Customer customer5 = factoryUtils.createCustomer();

        List<Coupon> customerCoupons1 = new ArrayList<>(Arrays.asList(coupon2, coupon3));
        List<Coupon> customerCoupons2 = new ArrayList<>(Arrays.asList(coupon3, coupon4));
        List<Coupon> customerCoupons3 = new ArrayList<>(Arrays.asList(coupon4, coupon5));

        adminService.addCustomer(customer1);
        adminService.addCustomer(customer2);
        adminService.addCustomer(customer3);
        adminService.addCustomer(customer4);
        adminService.addCustomer(customer5);

        customer1.setCoupons(customerCoupons1);
        customer2.setCoupons(customerCoupons2);
        customer3.setCoupons(customerCoupons3);

        adminService.updateCustomer(customer1);
        adminService.updateCustomer(customer2);
        adminService.updateCustomer(customer3);

        chartUtils.printCustomers(adminService.getAllCustomers());

    }

}
