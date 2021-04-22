package com.AlbertAbuav.Project002Coupons.service;

import com.AlbertAbuav.Project002Coupons.beans.Company;
import com.AlbertAbuav.Project002Coupons.beans.Customer;
import com.AlbertAbuav.Project002Coupons.beans.CustomerVsCoupons;
import com.AlbertAbuav.Project002Coupons.exception.invalidAdminException;
import com.AlbertAbuav.Project002Coupons.repositories.CompanyRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CouponRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends ClientFacade{

    public AdminService(CompanyRepository companyRepository, CustomerRepository customerRepository, CouponRepository couponRepository) {
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
    public boolean login(String email, String password) throws invalidAdminException {
        if (!(email.equals("admin@admin.com") && password.equals("admin"))) {
            throw new invalidAdminException("Could not login. One or both parameters are incorrect!");
        }
        return true;
    }

    /**
     * Adding a new company.
     * It is not possible to add a company with the same name to an existing company.
     * It is not possible to add a company with the same email to an existing company.
     *
     * @param company Company
     */
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    /**
     * Update an existing company.
     * The company id could not be updated.
     * The company name could not be updated.
     *
     * @param company Company
     */
    public void updateCompany(Company company) {
        companyRepository.saveAndFlush(company);
    }

    /**
     * Deleting an existing company.
     * The coupons created by the company must be deleted as well.
     * The purchase history of the company's coupons by customers must also be deleted.
     *
     * @param company Company
     */
    public void deleteCompany(Company company) {
        companyRepository.delete(company);
    }

    /**
     * Get all Companies.
     *
     * @return List
     */
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    /**
     * Get a single Company by id.
     *
     * @param id int
     * @return Company
     */
    public Company getSingleCompany(int id) {
        return companyRepository.getOne(id);
    }

    /**
     * Adding a new customer.
     * It is not possible to add a customer with the same email to an existing customer.
     *
     * @param customer Customer
     */
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    /**
     * Updating an existing customer.
     * Cannot update customer id.
     *
     * @param customer Customer
     */
    public void updateCustomer(Customer customer) {
        customerRepository.saveAndFlush(customer);
    }

    /**
     * Delete an existing customer.
     * The customer's purchase history should also be deleted.
     *
     * @param customer Customer
     */
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    /**
     * Get all customers.
     *
     * @return List
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Get a single customers by id.
     *
     * @param id int
     * @return Customer
     */
    public Customer getSingleCustomer(int id) {
        return customerRepository.getOne(id);
    }

    public List<CustomerVsCoupons> getAllCustomerVsCoupons(int customerID) {
        return couponRepository.getAllCustomersCoupons(customerID);
    }
}
