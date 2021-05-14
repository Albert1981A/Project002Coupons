package com.AlbertAbuav.Project002Coupons.clr;

import com.AlbertAbuav.Project002Coupons.beans.Customer;
import com.AlbertAbuav.Project002Coupons.exception.invalidAdminException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCompanyException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCustomerException;
import com.AlbertAbuav.Project002Coupons.login.ClientType;
import com.AlbertAbuav.Project002Coupons.login.LoginManager;
import com.AlbertAbuav.Project002Coupons.service.AdminService;
import com.AlbertAbuav.Project002Coupons.service.CustomerService;
import com.AlbertAbuav.Project002Coupons.utils.ArtUtils;
import com.AlbertAbuav.Project002Coupons.utils.ChartUtils;
import com.AlbertAbuav.Project002Coupons.utils.Colors;
import com.AlbertAbuav.Project002Coupons.utils.TestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(4)
public class CustomerServiceTest implements CommandLineRunner {

    private final ChartUtils chartUtils;

    /**
     * Invoking the LoginManager to login to the requested facade
     */
    private final LoginManager loginManager;
    private AdminService adminService;
    private CustomerService customerService;
    private CustomerService customerService2;

    @Override
    public void run(String... args) {

        Colors.setPurpleBoldPrint(ArtUtils.CUSTOMER_SERVICE);

        /**
         * Login to Admin Service
         */
        try {
            adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCustomerInfo("Login to Customer number 2");

        Customer toConnect2 = null;
        try {
            toConnect2 = adminService.getSingleCustomer(2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        /**
         * Login to Customer Service
         */
        try {
            customerService = (CustomerService) loginManager.login(toConnect2.getEmail(), toConnect2.getPassword(), ClientType.CUSTOMER);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCustomerInfo("Login to Customer number 4 (Customer with no coupons)");

        Customer toConnect4 = null;
        try {
            toConnect4 = adminService.getSingleCustomer(4);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        /**
         * Login to Customer Service
         */
        try {
            customerService2 = (CustomerService) loginManager.login(toConnect4.getEmail(), toConnect4.getPassword(), ClientType.CUSTOMER);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCustomerInfo("Get the logged Customer details");

        chartUtils.printCustomer(customerService.getTheLoggedCustomerDetails());

        TestUtils.testCustomerInfo("adding Coupon to Customer");

        TestUtils.testCustomerInfo("Get all Customer Coupons");

        try {
            chartUtils.printCoupons(customerService.getAllCustomerCoupons());
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCustomerInfo("Get all Customer Coupons of a Customer that have no coupons");

        try {
            chartUtils.printCoupons(customerService2.getAllCustomerCoupons());
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCustomerInfo("Get all Customer Coupons of a specific Category");

        try {
            chartUtils.printCoupons(customerService.getAllCustomerCouponsOfSpecificCategory(toConnect2.getCoupons().get(0).getCategory()));
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCustomerInfo("Get all Customer Coupons of a specific Category of a Customer that have no coupons");

        try {
            chartUtils.printCoupons(customerService2.getAllCustomerCouponsOfSpecificCategory(toConnect2.getCoupons().get(0).getCategory()));
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCustomerInfo("Get all Customer Coupons up to a maximum Price");
        System.out.println("The customer coupons:");
        chartUtils.printCoupons(toConnect2.getCoupons());
        double maxPrice = toConnect2.getCoupons().get(0).getPrice();
        System.out.println("The maxPrice is: " + maxPrice);
        System.out.println("Getting the coupons:");
        try {
            chartUtils.printCoupons(customerService.getAllCustomerCouponsUpToMaxPrice(maxPrice));
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCustomerInfo("Get all Customer Coupons up to a maximum Price of a Customer that have no coupons");

        try {
            chartUtils.printCoupons(customerService2.getAllCustomerCouponsUpToMaxPrice(toConnect2.getCoupons().get(0).getPrice()));
        } catch (invalidCustomerException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCustomerInfo("Find all Customers by a Coupon ID");

        chartUtils.printCustomers(customerService.findAllCustomersByCouponId(3));
    }
}
