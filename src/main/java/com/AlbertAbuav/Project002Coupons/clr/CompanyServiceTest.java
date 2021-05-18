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
import com.AlbertAbuav.Project002Coupons.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

        TestUtils.testCompanyInfo("Login to Company number - 2");

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

        chartUtils.printCompany(companyService.getTheLoggedCompanyDetails());

        TestUtils.testCompanyInfo("Get Single Coupon");

        try {
            chartUtils.printCoupon(companyService.getSingleCoupon(1));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCompanyInfo("Attempting to get a Single Coupon that doesn't appears in the data base");

        try {
            chartUtils.printCoupon(companyService.getSingleCoupon(12));
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCompanyInfo("Adding a new Coupon to Company id - 3");

        Coupon couponToAdd1 = Coupon.builder()
                .companyID(3)
                .category(Category.VACATIONS_ABROAD)
                .title("New Title1")
                .description("New Description1")
                .startDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(3)))
                .endDate(DateUtils.javaDateFromLocalDate(LocalDate.now().plusDays(7)))
                .amount((int) (Math.random() * 21) + 30)
                .price((int) (Math.random() * 41) + 60)
                .image("New Image1")
                .build();
        System.out.println("Attempting to add this Coupon: ");
        chartUtils.printCoupon(couponToAdd1);
        try {
            companyService.addCoupon(couponToAdd1);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The Companies Coupons after adding the new Coupon:");
        try {
            chartUtils.printCompany(adminService.getSingleCompany(3));
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCompanyInfo("Attempting to add a Coupon to the company that have the same title to an existing coupon of another company");

        Coupon sampleCoupon = null;
        try {
            sampleCoupon = companyService.getSingleCoupon(2);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        Coupon couponToAdd2 = Coupon.builder()
                .companyID(3)
                .category(Category.HOUSEHOLD_PRODUCTS)
                .title(sampleCoupon.getTitle())
                .description("New Description2")
                .startDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(3)))
                .endDate(DateUtils.javaDateFromLocalDate(LocalDate.now().plusDays(7)))
                .amount((int) (Math.random() * 21) + 30)
                .price((int) (Math.random() * 41) + 60)
                .image("New Image2")
                .build();
        System.out.println("Attempting to add this coupon: ");
        chartUtils.printCoupon(couponToAdd2);
        try {
            companyService.addCoupon(couponToAdd2);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The Companies Coupons after adding the new Coupon:");
        try {
            chartUtils.printCompany(adminService.getSingleCompany(3));
        } catch (invalidAdminException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCompanyInfo("Attempting to add a Coupon to the company that have the same title to an existing coupon of this company");

        Coupon couponToAdd3 = Coupon.builder()
                .companyID(sampleCoupon.getCompanyID())
                .category(Category.FOOD_PRODUCTS)
                .title(sampleCoupon.getTitle())
                .description("New Description3")
                .startDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(3)))
                .endDate(DateUtils.javaDateFromLocalDate(LocalDate.now().plusDays(7)))
                .amount((int) (Math.random() * 21) + 30)
                .price((int) (Math.random() * 41) + 60)
                .image("New Image3")
                .build();
        System.out.println("Attempting to add this coupon: ");
        chartUtils.printCoupon(couponToAdd3);
        try {
            companyService.addCoupon(couponToAdd3);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCompanyInfo("Attempting to add a Coupon to a Company that doesn't exist in the data base");

        Coupon couponToAdd4 = Coupon.builder()
                .companyID(12)
                .category(Category.VACATIONS_ABROAD)
                .title("New Title4")
                .description("New Description4")
                .startDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(3)))
                .endDate(DateUtils.javaDateFromLocalDate(LocalDate.now().plusDays(7)))
                .amount((int) (Math.random() * 21) + 30)
                .price((int) (Math.random() * 41) + 60)
                .image("New Image4")
                .build();
        System.out.println("Attempting to add this Coupon: ");
        chartUtils.printCoupon(couponToAdd1);
        try {
            companyService.addCoupon(couponToAdd4);
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }

        TestUtils.testCompanyInfo("Updating a Companies Coupon");

        TestUtils.testCompanyInfo("Get all the Company Coupons");

        chartUtils.printCoupons(companyService.getAllCompanyCoupons());

        TestUtils.testCompanyInfo("Get all the Company Coupons of a specific Category");

        Category testCategory = null;
        try {
            testCategory = companyService.getSingleCoupon(3).getCategory();
        } catch (invalidCompanyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("This is the Category for testing: " + testCategory + ", and this is it's ordinal number: " + testCategory.ordinal());
        chartUtils.printCoupons(companyService.getAllCompanyCouponsOfSpecificCategory(testCategory));

        TestUtils.testCompanyInfo("Get all Company Coupons up to a maximum price");

        chartUtils.printCoupons(companyService.getAllCompanyCouponsUpToMaxPrice(100));

        TestUtils.testCompanyInfo("Get all the Company Customers of a single Coupon by Coupon ID");

    }
}
