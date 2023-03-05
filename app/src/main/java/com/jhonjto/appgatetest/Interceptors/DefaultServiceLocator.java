package com.jhonjto.appgatetest.Interceptors;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.jhonjto.appgatetest.BuildConfig;
import com.jhonjto.common.Logger;
import com.jhonjto.data.logger.AndroidLogger;
import com.jhonjto.data.remote.HttpUrlConnectionDataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultServiceLocator implements ServiceLocator {

    private static DefaultServiceLocator instance;

    private final Application application;

    private HttpUrlConnectionDataSource httpUrlConnectionDataSource;

    private ExecutorService ioExecutorService;

    private ExecutorService mainExecutorService;

    private Map<Class<?>, AndroidLogger> loggers = new HashMap<>();

    public static DefaultServiceLocator getInstance(Application application) {
        if (instance == null) {
            instance = new DefaultServiceLocator(application);
        }
        return instance;
    }

    public DefaultServiceLocator(Application application) {
        this.application = application;
    }


    @Override
    public HttpUrlConnectionDataSource connectionDataSource() {
        if (httpUrlConnectionDataSource == null) {
            httpUrlConnectionDataSource = new HttpUrlConnectionDataSource();
        }

        return new HttpUrlConnectionDataSource();
    }

    @Override
    public ExecutorService getIoExecutorService() {
        if (ioExecutorService == null) {
            ioExecutorService = Executors.newCachedThreadPool();
        }
        return ioExecutorService;
    }

    @Override
    public ExecutorService getMainExecutorService() {
        if (mainExecutorService == null) {
            mainExecutorService = new MainThreadExecutorService();
        }
        return mainExecutorService;
    }

    @Override
    public Logger getLogger(Class<?> cls) {
        AndroidLogger logger = loggers.get(cls);
        if (logger == null) {
            loggers.put(cls, logger = new AndroidLogger(cls));
        }
        return logger;
    }
}
