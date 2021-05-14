package com.AlbertAbuav.Project002Coupons.service;

import com.AlbertAbuav.Project002Coupons.beans.Category;
import com.AlbertAbuav.Project002Coupons.beans.Coupon;
import com.AlbertAbuav.Project002Coupons.beans.Customer;
import com.AlbertAbuav.Project002Coupons.exception.invalidCustomerException;
import com.AlbertAbuav.Project002Coupons.repositories.CompanyRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CouponRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CustomerRepository;
import com.AlbertAbuav.Project002Coupons.utils.ChartUtils;
import com.AlbertAbuav.Project002Coupons.utils.Colors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class CustomerService extends ClientFacade {

    @Autowired
    private ChartUtils chartUtils;

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
        Colors.setPurpleBoldPrint("The logged Customer is: | ");
        chartUtils.printCustomer(logged);
        System.out.println();
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
        couponRepository.save(coupon);
    }

    /**
     * Get all coupons purchased by the customer.
     * That means, all the coupons purchased by the customer who made the login.
     *
     * @return List
     */
    public List<Coupon> getAllCustomerCoupons() throws invalidCustomerException {
        if (!couponRepository.existsByCustomers_Id(customerID)) {
            throw new invalidCustomerException("Their is no Coupons to the Logged customer");
        }
        return couponRepository.findAllByCustomers_Id(customerID);
    }

    /**
     * Get all coupons from a specific category purchased by the customer.
     * That means, only coupons from a specific category of the customer who made the login.
     *
     * @param category Category
     * @return List
     */
    public List<Coupon> getAllCustomerCouponsOfSpecificCategory(Category category) throws invalidCustomerException {
        if (!couponRepository.existsByCustomers_IdAndCategory(customerID, category)) {
            throw new invalidCustomerException("Their is no Coupons of that category to the Logged customer");
        }
        return couponRepository.findAllByCustomers_IdAndCategory(customerID, category);
    }

    /**
     * Get all coupons up to the maximum price set by the customer purchased.
     * That means, return only coupons up to the maximum price set by the customer who made the login.
     *
     * @param maxPrice double
     * @return List coupons
     */
    public List<Coupon> getAllCustomerCouponsUpToMaxPrice(double maxPrice) throws invalidCustomerException {
        if (!couponRepository.existsByCustomers_IdAndPriceLessThan(customerID, maxPrice)) {
            throw new invalidCustomerException("Their is no Coupons fo that max price to the Logged customer");
        }
        return couponRepository.findAllByCustomers_IdAndPriceLessThan(customerID, maxPrice);
    }

    /**
     * Get customer details.
     * That means, the details of the customer who performed the login.
     *
     * @return customer
     */
    public Customer getTheLoggedCustomerDetails() {
        return customerRepository.getOne(customerID);
    }

    /**
     * Get the customers of a single coupon.
     *
     * @return List customers
     */
    public List<Customer> findAllCustomersByCouponId(int couponID) {
        return customerRepository.findAllByCoupons_Id(couponID);
    }



}
