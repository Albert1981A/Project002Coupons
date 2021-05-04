package com.AlbertAbuav.Project002Coupons.repositories;

import com.AlbertAbuav.Project002Coupons.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByEmailAndPassword(String email, String password);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    boolean existsById(int id);
    Company findByEmailAndPassword(String email, String password);
    Company findByName(String name);
}
