package com.AlbertAbuav.Project002Coupons.service;

import com.AlbertAbuav.Project002Coupons.beans.Company;
import com.AlbertAbuav.Project002Coupons.exception.invalidCompanyException;
import com.AlbertAbuav.Project002Coupons.repositories.CompanyRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CouponRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CustomerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class CompanyService extends ClientFacade {

    private int companyID;

    public CompanyService(CompanyRepository companyRepository, CustomerRepository customerRepository, CouponRepository couponRepository) {
        super(companyRepository, customerRepository, couponRepository);
    }


    @Override
    public boolean login(String email, String password) throws invalidCompanyException {
        if (!companyRepository.existsByEmailAndPassword(email, password)) {
            throw new invalidCompanyException("Could not login. One or both parameters are incorrect!");
        }
        Company logged = companyRepository.findByEmailAndPassword(email, password);
        System.out.println("The logged Company is: | " + logged);
        companyID = logged.getId();
        return true;
    }



}
