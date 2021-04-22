package com.AlbertAbuav.Project002Coupons.repositories;

import com.AlbertAbuav.Project002Coupons.beans.Coupon;
import com.AlbertAbuav.Project002Coupons.beans.CustomerVsCoupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    List<Coupon> findByCompanyID(int companyID);

    /**
     * 10% Query("...")
     * HQL = Hibernate Query Language
     */
    @Query(value = "SELECT * FROM `couponsystem002`.`customer_coupons` WHERE (`customer_id` = ?1);", nativeQuery = true)
    List<CustomerVsCoupons> getAllCustomersCoupons(int customerID);

}
