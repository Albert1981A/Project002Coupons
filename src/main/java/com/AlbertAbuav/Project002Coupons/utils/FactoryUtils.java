package com.AlbertAbuav.Project002Coupons.utils;

import com.AlbertAbuav.Project002Coupons.beans.Category;
import com.AlbertAbuav.Project002Coupons.beans.Company;
import com.AlbertAbuav.Project002Coupons.beans.Coupon;
import com.AlbertAbuav.Project002Coupons.beans.Customer;
import com.AlbertAbuav.Project002Coupons.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
public class FactoryUtils {

    private static int COUNT1 = 1;
    private static int COUNT2 = 1;

    @Autowired
    private CompanyRepository companyRepository;
    /**
     * This method generate a random customer first name.
     * @return String
     */
    public String generateFirstName() {
        FirstName firstName = FirstName.values()[(int)(Math.random() * FirstName.values().length)];
        return firstName.toString();
    }

    /**
     * This method generate a random customer last name.
     * @return String
     */
    public String generateLastName() {
        LastName lastName = LastName.values()[(int)(Math.random() * LastName.values().length)];
        return lastName.toString();
    }

    /**
     * This method sends a random company name.
     * There can be no two identical company names!
     * The method checks in the data base if the name exists before sending it.
     * If the name exist it's changes.
     * @return String
     */
    public String generateCompanyName() {
        String companyName = CompaniesType.values()[(int)(Math.random() * CompaniesType.values().length)].toString();
        List<Company> companyList = companyRepository.findAll();
        if (companyList.size() == 0) {
            return companyName;
        }
        boolean isExist = true;
        while (isExist) {
            for (Company company : companyList) {
                if (companyName.equals(company.getName())) {
                    isExist = true;
                    companyName = CompaniesType.values()[(int)(Math.random() * CompaniesType.values().length)].toString();
                    break;
                } else {
                    isExist = false;
                }
            }
        }
        return companyName;
    }

    /**
     * This method generate a random ending for a customers mail.
     * @return String
     */
    public String generateCustomerEmailType() {
        EmailType emailType = EmailType.values()[(int)(Math.random() * EmailType.values().length)];
        return "@" + emailType.toString() + ".com";
    }

    /**
     * This method generate a random 8 figure password.
     * @return String
     */
    public String createPassword() {
        String name = UUID.randomUUID().toString();
        char[] nameChar = name.toCharArray();
        char[] password = new char[8];
        for (int i = 0; i < password.length; i++) {
            password[i] = nameChar[i];
        }
        return String.valueOf(password);
    }


    public Customer createCustomer() {
        String firstName = generateFirstName();
        String email = createEmail(firstName);
        return Customer.builder()
                .firstName(firstName)
                .lastName(generateLastName())
                .email(email)
                .password(createPassword())
                .build();
    }

    public String createEmail(String firstName) {
        String email = firstName + (COUNT1++) + generateCustomerEmailType();
        return email.toLowerCase(Locale.ROOT);
    }

    public Company createCompany() {
        String name = generateCompanyName();
        String email = createCompanyEmail(name);
        return Company.builder()
                .name(name)
                .email(email)
                .password(createPassword())
                .build();
    }

    public String createCompanyEmail(String name) {
        String email = generateFirstName()+"@" + name + ".com";
        return email.toLowerCase(Locale.ROOT);
    }

    public Coupon createCoupon() {
        return Coupon.builder()
                .companyID(COUNT2)
                .category(Category.values()[(int)(Math.random()*Category.values().length)])
                .title("Title: " + COUNT2)
                .description("Description: " + COUNT2)
                .startDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(3)))
                .endDate(DateUtils.javaDateFromLocalDate(LocalDate.now().plusDays(7)))
                .amount((int)(Math.random()*21)+30)
                .price((int)(Math.random()*41)+60)
                .image("Image: " + COUNT2++)
                .build();
    }

    public Coupon createCouponOfACompany(int companyID) {
        return Coupon.builder()
                .companyID(companyID)
                .category(Category.values()[(int)(Math.random()*Category.values().length)])
                .title("Title: " + COUNT2)
                .description("Description: " + COUNT2)
                .startDate(DateUtils.javaDateFromLocalDate(LocalDate.now().minusDays(3)))
                .endDate(DateUtils.javaDateFromLocalDate(LocalDate.now().plusDays(7)))
                .amount((int)(Math.random()*21)+30)
                .price((int)(Math.random()*41)+60)
                .image("Image: " + COUNT2++)
                .build();
    }


}
