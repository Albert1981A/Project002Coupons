package com.AlbertAbuav.Project002Coupons.service;

import com.AlbertAbuav.Project002Coupons.beans.*;
import com.AlbertAbuav.Project002Coupons.exception.invalidCompanyException;
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
public class CompanyService extends ClientFacade {

    @Autowired
    private ChartUtils chartUtils;

    private int companyID;

    public CompanyService(CompanyRepository companyRepository, CustomerRepository customerRepository, CouponRepository couponRepository) {
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
    public boolean login(String email, String password) throws invalidCompanyException {
        if (!companyRepository.existsByEmailAndPassword(email, password)) {
            throw new invalidCompanyException("Could not login. One or both parameters are incorrect!");
        }
        Company logged = companyRepository.findByEmailAndPassword(email, password);
        Colors.setCyanBoldPrint("The logged Company is: | ");
        chartUtils.printCompany(logged);
        System.out.println();
        companyID = logged.getId();
        return true;
    }

    /**
     * Add a new coupon.
     * Do not add a coupon with the same title to an existing coupon of the same company.
     * It is ok to add a coupon with the same title to another company's coupon.
     *
     * @param coupon Coupon
     */
    public void addCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }

    /**
     * Update an existing coupon.
     * The coupon id could not be updated.
     * The company id could not be updated.
     *
     * @param coupon Coupon
     */
    public void updateCoupon(Coupon coupon) {
        couponRepository.saveAndFlush(coupon);
    }

    /**
     * Delete an existing coupon.
     * The purchase history of the coupon by customers must also be deleted.
     *
     * @param coupon Coupon
     */
    public void deleteCoupon(Coupon coupon) {
        couponRepository.delete(coupon);
    }

    /**
     * Get all company coupons.
     * That means, all the coupons of the company that made the login.
     *
     * @return List
     */
    public List<Coupon> getAllCompanyCoupons() {
        return couponRepository.findByCompanyID(companyID);
    }

    /**
     * Get all coupons from a specific category of the company.
     * That means, only coupons from a specific category of the company that made the login.
     *
     * @param category Category
     * @return List
     */
    public List<Coupon> getAllCompanyCouponsOfSpecificCategory(Category category) {
        return couponRepository.findByCompanyIDAndCategory(companyID, category.ordinal());
    }

    /**
     * Get all coupons up to the maximum price set by the company.
     * That means, only coupons up to the maximum price set by the company that performed the login.
     *
     * @param maxPrice double
     * @return List
     */
    public List<Coupon> getAllCompanyCouponsUpToMaxPrice(double maxPrice) {
        return couponRepository.findByCompanyIDAndPriceLessThan(companyID, maxPrice);
    }

    /**
     * Get company details.
     * That means, the details of the company that performed the login.
     *
     * @return Company
     */
    public Company getTheLoggedCompanyDetails() {
        return companyRepository.getOne(companyID);
    }

    /**
     * The method return a single coupon of the logged company by id.
     *
     * @param id int
     * @return Coupon
     */
    public Coupon getSingleCoupon(int id) {
        return couponRepository.getOne(id);
    }

    /**
     * Get all company customers of a single Coupon
     *
     * @param couponID int
     * @return List
     */
    public List<Customer> getAllCompanyCustomersOfASingleCouponByCouponId(int couponID) {
        return customerRepository.findAllByCoupons_Id(couponID);
    }

}
