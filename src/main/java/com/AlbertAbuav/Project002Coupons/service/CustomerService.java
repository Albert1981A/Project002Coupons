package com.AlbertAbuav.Project002Coupons.service;

import com.AlbertAbuav.Project002Coupons.beans.Category;
import com.AlbertAbuav.Project002Coupons.beans.Coupon;
import com.AlbertAbuav.Project002Coupons.beans.Customer;
import com.AlbertAbuav.Project002Coupons.exception.invalidCustomerException;
import com.AlbertAbuav.Project002Coupons.repositories.CompanyRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CouponRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Scope("prototype")
public class CustomerService extends ClientFacade {

    private int customerID;

    public CustomerService(CompanyRepository companyRepository, CustomerRepository customerRepository, CouponRepository couponRepository) {
        super(companyRepository, customerRepository, couponRepository);
    }

    /**
     * Login method, the method is implemented from the abstract class "ClientFacade"
     *
     * @param email    String
     * @param password String
     * @return boolean
     */
    @Override
    public boolean login(String email, String password) throws invalidCustomerException {
        if (!customerRepository.existsByEmailAndPassword(email, password)) {
            throw new invalidCustomerException("Could not login. One or both parameters are incorrect!");
        }
        Customer logged = customerRepository.findByEmailAndPassword(email, password);
        System.out.println("The logged Customer is: | " + logged);
        customerID = logged.getId();
        return true;
    }

    /**
     * Purchase a coupon.
     * You cannot purchase the same coupon more than once.
     * The coupon cannot be purchased if its quantity is 0.
     * The coupon cannot be purchased if its expiration date has already been reached.
     * After the purchase, the quantity in stock of the coupon must be reduced by 1.
     *
     * @param coupon Coupon
     */
    public void addCoupon(Coupon coupon) {
    }

    /**
     * Get all coupons purchased by the customer.
     * That means, all the coupons purchased by the customer who made the login.
     *
     * @return List
     */
    public List<Coupon> getAllCustomerCoupons() {
        return null;
    }

    /**
     * Get all coupons from a specific category purchased by the customer.
     * That means, only coupons from a specific category of the customer who made the login.
     *
     * @param category Category
     * @return List
     */
    public List<Coupon> getAllCustomerCouponsOfSpecificCategory(Category category) {
        return null;
    }

    /**
     * Get all coupons up to the maximum price set by the customer purchased.
     * That means, return only coupons up to the maximum price set by the customer who made the login.
     *
     * @param maxPrice double
     * @return List
     */
    public List<Coupon> getAllCustomerCouponsUpToMaxPrice(double maxPrice) {
        return null;
    }

    /**
     * Get customer details.
     * That means, the details of the customer who performed the login.
     *
     * @return List
     */
    public Customer getTheLoggedCustomerDetails() {
        return null;
    }



}
