package com.AlbertAbuav.Project002Coupons.service;

import com.AlbertAbuav.Project002Coupons.beans.Company;
import com.AlbertAbuav.Project002Coupons.beans.Customer;
import com.AlbertAbuav.Project002Coupons.exception.invalidAdminException;
import com.AlbertAbuav.Project002Coupons.repositories.CompanyRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CouponRepository;
import com.AlbertAbuav.Project002Coupons.repositories.CustomerRepository;
import com.AlbertAbuav.Project002Coupons.utils.ChartUtils;
import com.AlbertAbuav.Project002Coupons.utils.Colors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Lazy
public class AdminService extends ClientFacade{

    @Autowired
    private ChartUtils chartUtils;


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
        Colors.setGreenBoldPrint("The logged Admin is: | " + email);
        System.out.println();
        return true;
    }

    /**
     * Adding a new company.
     * It is not possible to add a company with the same name to an existing company.
     * It is not possible to add a company with the same email to an existing company.
     *
     * @param company Company
     */
    public void addCompany(Company company) throws invalidAdminException {
        if (Objects.isNull(company)) {
            throw new invalidAdminException("There is no customer like the one you entered!");
        } else if (companyRepository.existsByName(company.getName())) {
            throw new invalidAdminException("The name of the company you are trying to add already appears in the system.\nCompanies with the same name cannot be added!");
        } else if (companyRepository.existsByEmail(company.getEmail())) {
            throw new invalidAdminException("The email of the company you are trying to add already appears in the system.\nCompanies with the same email cannot be added!");
        }
        companyRepository.save(company);
    }

    /**
     * Update an existing company.
     * The company id could not be updated.
     * The company name could not be updated.
     *
     * @param company Company
     */
    public void updateCompany(Company company) throws invalidAdminException {
        if (Objects.isNull(company)) {
            throw new invalidAdminException("There is no customer like you entered!");
        }
        Company toCompare = companyRepository.findByName(company.getName());
        if (Objects.isNull(toCompare)) {
            throw new invalidAdminException("No company matching the name: \"" + company.getName() + "\", was found:");
        } else if (company.getId() != toCompare.getId()) {
            throw new invalidAdminException("The company id or name cannot be updated");
        }
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
    public List<Company> getAllCompanies() throws invalidAdminException {
        List<Company> companies = companyRepository.findAll();
        if (companies.size() == 0) {
            throw new invalidAdminException("There are no companies in the system");
        }
        return companies;
    }

    /**
     * Get a single Company by id.
     *
     * @param id int
     * @return Company
     */
    public Company getSingleCompany(int id) throws invalidAdminException {
        if (id <= 0) {
            throw new invalidAdminException("There is no id like you enter !");
        }
        if (!companyRepository.existsById(id)) {
            throw new invalidAdminException("No Company was found by this id!");
        }
        return companyRepository.getOne(id);
    }

    /**
     * Adding a new customer.
     * It is not possible to add a customer with the same email to an existing customer.
     *
     * @param customer Customer
     */
    public void addCustomer(Customer customer) throws invalidAdminException {
        if (Objects.isNull(customer)) {
            throw new invalidAdminException("There is no customer like you entered");
        }
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new invalidAdminException("The email of the customer you are trying to add already appears in the system.\nCustomers with the same email cannot be added.");
        }
        customerRepository.save(customer);
    }

    /**
     * Updating an existing customer.
     * Cannot update customer id.
     *
     * @param customer Customer
     */
    public void updateCustomer(Customer customer) throws invalidAdminException {
        if (Objects.isNull(customer)) {
            throw new invalidAdminException("There is no customer like you entered");
        }
        if (!customerRepository.existsByEmail(customer.getEmail())) {
            if (!customerRepository.existsById(customer.getId())) {
                throw new invalidAdminException("There is no customer like you entered in the system or the email is incorrect!");
            }
            customerRepository.saveAndFlush(customer);
        }
        Customer toCompare = customerRepository.findByEmail(customer.getEmail());
        if (customer.getId() != toCompare.getId()) {
            throw new invalidAdminException("The customer id cannot be updated");
        }
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
    public List<Customer> getAllCustomers() throws invalidAdminException {
        List<Customer> customers = customerRepository.findAll();
        if (customers.size() == 0) {
            throw new invalidAdminException("There are no customers in the system");
        }
        return customers;
    }

    /**
     * Get a single customers by id.
     *
     * @param id int
     * @return Customer
     */
    public Customer getSingleCustomer(int id) throws invalidAdminException {
        if (id <= 0) {
            throw new invalidAdminException("There is no id like you enter !");
        }
        if (!customerRepository.existsById(id)) {
            throw new invalidAdminException("There is no customer with the id: " + id);
        }
        return customerRepository.getOne(id);
    }

}
