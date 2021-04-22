package com.AlbertAbuav.Project002Coupons.service;

import com.AlbertAbuav.Project002Coupons.exception.invalidAdminException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCompanyException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCustomerException;
import com.AlbertAbuav.Project002Coupons.repositories.CompanyRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CouponRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class ClientFacade {

    protected final CompanyRepository companyRepository;
    protected final CustomerRepository customerRepository;
    protected final CouponRepository couponRepository;

    public abstract boolean login(String email, String password) throws invalidCompanyException, invalidAdminException, invalidCustomerException;

}
