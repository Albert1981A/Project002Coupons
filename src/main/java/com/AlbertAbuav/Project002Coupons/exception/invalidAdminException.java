package com.AlbertAbuav.Project002Coupons.exception;

import com.AlbertAbuav.Project002Coupons.utils.Colors;

public class invalidAdminException extends Exception {
    public invalidAdminException(String message) {
        super(Colors.RED + "This is an invalid Administrator operation: " + message + Colors.RESET);
    }
}
