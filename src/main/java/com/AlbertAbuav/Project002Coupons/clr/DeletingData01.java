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
import com.AlbertAbuav.Project002Coupons.utils.ArtUtils;
import com.AlbertAbuav.Project002Coupons.utils.ChartUtils;
import com.AlbertAbuav.Project002Coupons.utils.Colors;
import com.AlbertAbuav.Project002Coupons.utils.TestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(5)
public class DeletingData01 implements CommandLineRunner {

    private final ChartUtils chartUtils;

    /**
     * Invoking the LoginManager to login to the requested facade
     */
    private final LoginManager loginManager;
    private AdminService adminService;
    private CompanyService companyService;

    @Override
    public void run(String... args) {

        Colors.setGreenBoldPrint(ArtUtils.DELETING_DATA_01);

        try {
            adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCompanyInfo("Login to Company id-1");

        Company toConnect = null;
        try {
            toConnect = adminService.getSingleCompany(1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        /**
         * Login to Company Service
         */
        try {
            companyService = (CompanyService) loginManager.login(toConnect.getEmail(), toConnect.getPassword(), ClientType.COMPANY);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCompanyInfo("Delete a Company Coupon");

        Coupon couponToDelete = null;
        try {
            couponToDelete = companyService.getSingleCoupon(3);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("This is the Coupon to delete:");
        chartUtils.printCoupon(couponToDelete);
        System.out.println();
        try {
            companyService.deleteCoupon(couponToDelete);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println("The company after deleting coupon id-" + couponToDelete.getId());
        chartUtils.printCompany(companyService.getTheLoggedCompanyDetails());

        TestUtils.testAdminInfo("Delete Company");

        Company companyToDelete = null;
        try {
            companyToDelete = adminService.getSingleCompany(1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("This is the company to delete:");
        chartUtils.printCompany(companyToDelete);
        System.out.println();
        try {
            adminService.deleteCompany(companyToDelete);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println("All Companies that were left after deleting Company id-" + companyToDelete.getId());
        try {
            chartUtils.printCompanies(adminService.getAllCompanies());
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testAdminInfo("Delete Customer");

        Customer customerToDelete = null;
        try {
            customerToDelete = adminService.getSingleCustomer(1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("This is the Customer to delete:");
        chartUtils.printCustomer(customerToDelete);
        try {
            adminService.deleteCustomer(customerToDelete);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The Customers that were left after deleting Customer id-" + customerToDelete.getId());
        try {
            chartUtils.printCustomers(adminService.getAllCustomers());
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

    }

}
