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
import java.util.Objects;

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
    public void addCoupon(Coupon coupon) throws invalidCompanyException {
        if (Objects.isNull(coupon)) {
            throw new invalidCompanyException("There is no coupon like you entered");
        }
        if (!companyRepository.existsById(coupon.getCompanyID())) {
            throw new invalidCompanyException("The Company id \"" + coupon.getCompanyID() + "\" doesn't appears in the data base");
        }
        if (couponRepository.existsByCompanyIDAndTitle(coupon.getCompanyID(), coupon.getTitle())) {
            throw new invalidCompanyException("Cannot add a coupon with the same title to an existing coupon of the same company!");
        }
        Company company = companyRepository.getOne(coupon.getCompanyID());
        company.getCoupons().add(coupon);
        companyRepository.saveAndFlush(company);
    }

    /**
     * Update an existing coupon.
     * The coupon id could not be updated.
     * The company id could not be updated.
     *
     * @param coupon Coupon
     */
    public void updateCoupon(Coupon coupon) throws invalidCompanyException {
        if (Objects.isNull(coupon)) {
            throw new invalidCompanyException("There is no coupon like you entered!");
        }
        if (coupon.getCompanyID() != companyID) {
            throw new invalidCompanyException("The \"company id\" cannot be updated");
        }
        Company companyToUpdate = companyRepository.getOne(coupon.getCompanyID());
        List<Coupon> companyCoupons = companyToUpdate.getCoupons();
        int count = 0;
        if (couponRepository.existsByTitle(coupon.getTitle())) {
            for (Coupon coupon1 : companyCoupons) {
                if (coupon1.getId() == coupon.getId() && coupon1.getTitle().equals(coupon.getTitle())) {
                    companyCoupons.set(count, coupon);
                    companyToUpdate.setCoupons(companyCoupons);
                    companyRepository.saveAndFlush(companyToUpdate);
                    return;
                }
                count++;
            }
            for (Coupon coupon1 : companyCoupons) {
                if (coupon1.getId() != coupon.getId() && coupon1.getTitle().equals(coupon.getTitle())) {
                    throw new invalidCompanyException("The \"coupon id\" cannot be updated");
                }
            }
        }
        int count2 = 0;
        for (Coupon coupon1 : companyCoupons) {
            if (coupon1.getId() == coupon.getId() && !coupon1.getCategory().equals(coupon.getCategory())) {
                throw new invalidCompanyException("The \"coupon id\" cannot be updated");
            } else if (coupon1.getId() == coupon.getId()) {
                companyCoupons.set(count2, coupon);
                companyToUpdate.setCoupons(companyCoupons);
                companyRepository.saveAndFlush(companyToUpdate);
                count2++;
            }
        }
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
        return couponRepository.findByCompanyIDAndCategory(companyID, category);
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
    public Coupon getSingleCoupon(int id) throws invalidCompanyException {
        if (!couponRepository.existsById(id)) {
            throw new invalidCompanyException("Their is no coupon for the couponID: \"" + id + "\" you entered!");
        }
        if (!couponRepository.existsByCompanyIDAndId(companyID, id)) {
            throw new invalidCompanyException("This coupon id doesn't belong to the connected company");
        }
        return couponRepository.getOne(id);
    }

    /**
     * Get all company customers of a single Coupon
     *
     * @param couponID int
     * @return List
     */
    public List<Customer> getAllCompanyCustomersOfASingleCouponByCouponId(int couponID) throws invalidCompanyException {
        if (!couponRepository.existsByCompanyIDAndId(companyID, couponID)) {
            throw new invalidCompanyException("This coupon doesn't belong to the logged company");
        }
        return customerRepository.findAllByCoupons_Id(couponID);
    }

}
