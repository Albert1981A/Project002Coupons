package com.AlbertAbuav.Project002Coupons.clr;

import com.AlbertAbuav.Project002Coupons.beans.Company;
import com.AlbertAbuav.Project002Coupons.exception.invalidAdminException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCompanyException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCustomerException;
import com.AlbertAbuav.Project002Coupons.login.ClientType;
import com.AlbertAbuav.Project002Coupons.login.LoginManager;
import com.AlbertAbuav.Project002Coupons.service.AdminService;
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
@Order(2)
public class AdminServiceTest implements CommandLineRunner {

    private final ChartUtils chartUtils;

    /**
     * Invoking the LoginManager to login to the requested facade
     */
    private final LoginManager loginManager;
    private AdminService adminService;

    @Override
    public void run(String... args) {

        /**
         * Login to Admin Service
         */
        Colors.setGreenBoldPrint(ArtUtils.ADMIN_SERVICE);

        TestUtils.testAdminInfo("Login to Admin Service");

        try {
            adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testAdminInfo("Attempting to add a Company with an existing name");

        Company companyToTest1 = null;
        try {
            companyToTest1 = Company.builder()
                    .name(adminService.getSingleCompany(7).getName())
                    .password("1234")
                    .email("sample@sample.com")
                    .build();
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        try {
            adminService.addCompany(companyToTest1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testAdminInfo("Attempting to add a Company with an existing email");

        Company companyToTest2 = null;
        try {
            companyToTest2 = Company.builder()
                    .name("sample")
                    .password("1234")
                    .email(adminService.getSingleCompany(7).getEmail())
                    .build();
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        try {
            adminService.addCompany(companyToTest2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testAdminInfo("Attempting to update a Company email");

        Company companyToUpdate1 = null;
        try {
            companyToUpdate1 = adminService.getSingleCompany(7);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The Company before changing the email:");
        chartUtils.printCompany(companyToUpdate1);
        companyToUpdate1.setEmail("email@email.com");
        try {
            adminService.updateCompany(companyToUpdate1);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The Company after changing the email:");
        try {
            chartUtils.printCompany(adminService.getSingleCompany(7));
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testAdminInfo("Attempting to update a Company id");

        Company companyToUpdate2 = null;
        try {
            companyToUpdate2 = adminService.getSingleCompany(8);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The Company before changing the id:");
        chartUtils.printCompany(companyToUpdate2);
        companyToUpdate2.setId(3);
        System.out.println("Attempting to change the id:");
        try {
            adminService.updateCompany(companyToUpdate2);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testAdminInfo("Attempting to update a Company name (using new name)");

        Company companyToUpdate3 = null;
        try {
            companyToUpdate3 = adminService.getSingleCompany(9);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The Company before changing the name:");
        chartUtils.printCompany(companyToUpdate3);
        companyToUpdate3.setName("New-Name");
        System.out.println("Attempting to change the name:");
        try {
            adminService.updateCompany(companyToUpdate3);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testAdminInfo("Attempting to update a Company name (using an existing name)");

        Company companyToUpdate4 = null;
        try {
            companyToUpdate4 = adminService.getSingleCompany(10);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The Company before changing the name:");
        chartUtils.printCompany(companyToUpdate4);
        try {
            companyToUpdate4.setName(adminService.getSingleCompany(3).getName());
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Attempting to change the name:");
        try {
            adminService.updateCompany(companyToUpdate4);
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testAdminInfo("Get all Companies");

        try {
            chartUtils.printCompanies(adminService.getAllCompanies());
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testAdminInfo("Get single Company");

        try {
            chartUtils.printCompany(adminService.getSingleCompany(3));
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testAdminInfo("Adding Customer");

        TestUtils.testAdminInfo("Update Customer");

        TestUtils.testAdminInfo("Get all Customers");

        TestUtils.testAdminInfo("Get single Customer");













    }
}
