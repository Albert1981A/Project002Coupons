package com.AlbertAbuav.Project002Coupons.clr;

import com.AlbertAbuav.Project002Coupons.beans.Category;
import com.AlbertAbuav.Project002Coupons.beans.Company;
import com.AlbertAbuav.Project002Coupons.beans.Coupon;
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

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(3)
public class CompanyServiceTest implements CommandLineRunner {

    private final ChartUtils chartUtils;

    /**
     * Invoking the LoginManager to login to the requested facade
     */
    private final LoginManager loginManager;
    private AdminService adminService;
    private CompanyService companyService;

    @Override
    public void run(String... args) {

        Colors.setCyanBoldPrint(ArtUtils.COMPANY_SERVICE);

        /**
         * Login to Admin Service
         */
        try {
            adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        } catch (invalidCompanyException | invalidCustomerException | invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCompanyInfo("Login to Company number 2");

        Company toConnect = null;
        try {
            toConnect = adminService.getSingleCompany(2);
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

        TestUtils.testCompanyInfo("Get the logged Company Details");

        TestUtils.testCompanyInfo("Get Single Coupon");

        TestUtils.testCompanyInfo("Adding a Coupon to the company");

        TestUtils.testCompanyInfo("Updating a Companies Coupon");

        TestUtils.testCompanyInfo("Get all the Company Coupons");

        chartUtils.printCoupons(companyService.getAllCompanyCoupons());

        TestUtils.testCompanyInfo("Get all the Company Coupons of a specific Category");

        Category testCategory = companyService.getSingleCoupon(3).getCategory();
        System.out.println("This is the Category for testing: " + testCategory + ", and this is it's ordinal number: " + testCategory.ordinal());

        chartUtils.printCoupons(companyService.getAllCompanyCouponsOfSpecificCategory(testCategory));

        TestUtils.testCompanyInfo("Get all Company Coupons up to a maximum price");

        chartUtils.printCoupons(companyService.getAllCompanyCouponsUpToMaxPrice(100));

        TestUtils.testCompanyInfo("Get all the Company Customers of a single Coupon by Coupon ID");

    }
}
