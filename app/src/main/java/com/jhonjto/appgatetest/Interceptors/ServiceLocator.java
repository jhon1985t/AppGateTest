package com.jhonjto.appgatetest.Interceptors;

import com.jhonjto.common.Logger;
import com.jhonjto.domain.interfaces.ConnectionDataSource;

import java.util.concurrent.ExecutorService;

public interface ServiceLocator {

    ConnectionDataSource connectionDataSource();

    ExecutorService getIoExecutorService();

    ExecutorService getMainExecutorService();

    Logger getLogger(Class<?> cls);
}
