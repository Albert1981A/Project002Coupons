package com.AlbertAbuav.Project002Coupons.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVsCoupons {

    private int customerID;
    private int couponID;

}
