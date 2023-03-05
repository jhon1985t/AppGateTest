package com.jhonjto.data.remote;

public class HttpServiceUrl {

    static String getValidations(Double latitude, Double longitude) {
        return "/coordinate?latitude="+latitude+"&longitude="+longitude+"";
    }
}
