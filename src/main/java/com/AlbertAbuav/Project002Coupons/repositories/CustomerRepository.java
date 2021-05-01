package com.AlbertAbuav.Project002Coupons.repositories;

import com.AlbertAbuav.Project002Coupons.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmailAndPassword(String email, String password);
    Customer findByEmailAndPassword(String email, String password);
    List<Customer> findAllByCoupons_Id(int couponID);


}
