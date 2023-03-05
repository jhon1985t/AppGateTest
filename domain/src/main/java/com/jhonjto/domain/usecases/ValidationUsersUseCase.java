package com.jhonjto.domain.usecases;

import com.jhonjto.common.Logger;
import com.jhonjto.domain.interfaces.ConnectionDataSource;

public class ValidationUsersUseCase {

    private final ConnectionDataSource connectionDataSource;
    private Logger logger;

    public ValidationUsersUseCase (ConnectionDataSource connectionDataSource, Logger logger) {
        this.connectionDataSource = connectionDataSource;
        this.logger = logger;
    }

    public String execute(Double latitude, Double longitude) throws Exception {
        return connectionDataSource.getValidationsUser(latitude, longitude);
    }
}
