package com.AlbertAbuav.Project002Coupons.login;

import com.AlbertAbuav.Project002Coupons.exception.invalidAdminException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCompanyException;
import com.AlbertAbuav.Project002Coupons.exception.invalidCustomerException;
import com.AlbertAbuav.Project002Coupons.service.AdminService;
import com.AlbertAbuav.Project002Coupons.service.ClientFacade;
import com.AlbertAbuav.Project002Coupons.service.CompanyService;
import com.AlbertAbuav.Project002Coupons.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
@RequiredArgsConstructor
public class LoginManager {

    private final ApplicationContext ctx;

    /**
     * This method defines what type of client is trying to connect.
     * It will define whether he is allowed to enter the facade layer.
     * @param email String
     * @param password String
     * @param clientType String
     * @return ClientFacade
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws invalidCompanyException, invalidCustomerException, invalidAdminException {
        ClientFacade clientFacade;
        switch (clientType) {
            case ADMINISTRATOR:
                clientFacade = ctx.getBean(AdminService.class);
                return (clientFacade.login(email, password)) ? clientFacade : null;
            case COMPANY:
                clientFacade = ctx.getBean(CompanyService.class);
                return (clientFacade.login(email, password)) ? clientFacade : null;
            case CUSTOMER:
                clientFacade = ctx.getBean(CustomerService.class);
                return (clientFacade.login(email, password)) ? clientFacade : null;
        }
        return null;
    }
}
