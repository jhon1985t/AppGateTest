package com.jhonjto.appgatetest.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jhonjto.appgatetest.Interceptors.ServiceLocator;
import com.jhonjto.appgatetest.viewmodels.ValidationsViewModel;
import com.jhonjto.domain.usecases.ValidationUsersUseCase;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private ServiceLocator serviceLocator;

    public ViewModelFactory(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ValidationsViewModel.class)) {
            return (T) new ValidationsViewModel(
                    new ValidationUsersUseCase(
                            serviceLocator.connectionDataSource(),
                            serviceLocator.getLogger(ValidationUsersUseCase.class)
                    ),
                    serviceLocator.getLogger(ValidationsViewModel.class),
                    serviceLocator.getIoExecutorService(),
                    serviceLocator.getMainExecutorService()
            );
        }
        throw new IllegalArgumentException("unknown model class $modelClass");
    }
}
