package com.AlbertAbuav.Project002Coupons.repositories;

import com.AlbertAbuav.Project002Coupons.beans.Category;
import com.AlbertAbuav.Project002Coupons.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    List<Coupon> findByCompanyID(int companyID);
    List<Coupon> findByCompanyIDAndCategory(int companyID, Category category);
    List<Coupon> findByCompanyIDAndPriceLessThan(int companyID, double maxPrice);
    List<Coupon> findAllByCustomers_Id(int customerID);
    List<Coupon> findAllByCustomers_IdAndCategory(int customerID, int category);
    List<Coupon> findAllByCustomers_IdAndPriceLessThan(int customerID, double maxPrice);

    /**
     * 10% Query("...")
     * HQL = Hibernate Query Language
     */
//    @Query(value = "SELECT * FROM `couponsystem002`.`customer_coupons` WHERE (`customer_id` = ?1);", nativeQuery = true)
//    List<CustomerVsCoupons> findAllByCustomer_Id(int customerID);

//    @Query(value = "SELECT * FROM `couponsystem002`.`customer_coupons` WHERE (`coupons_id` = ?1);", nativeQuery = true)
//    List<CustomerVsCoupons> getAllCustomersOfASingleCouponByCouponId(int couponID);

}
