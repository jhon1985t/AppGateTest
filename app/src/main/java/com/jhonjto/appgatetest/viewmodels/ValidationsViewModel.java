package com.jhonjto.appgatetest.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jhonjto.appgatetest.common.Event;
import com.jhonjto.common.Logger;
import com.jhonjto.domain.usecases.ValidationUsersUseCase;

import java.util.concurrent.ExecutorService;

public class ValidationsViewModel extends ViewModel {

    private final ValidationUsersUseCase validationUsersUseCase;

    private final Logger logger;

    private final ExecutorService ioExecutorService;

    private final ExecutorService mainExecutorService;

    private final MutableLiveData<Event<String>> searchHistoryItems = new MutableLiveData<>();

    public ValidationsViewModel(
            ValidationUsersUseCase validationUsersUseCase,
            Logger logger,
            ExecutorService ioExecutorService,
            ExecutorService mainExecutorService) {
        this.validationUsersUseCase = validationUsersUseCase;
        this.logger = logger;
        this.ioExecutorService = ioExecutorService;
        this.mainExecutorService = mainExecutorService;
    }

    public void onSearchHistoryItemClick(Double latitude, Double longitude) {
        mainExecutorService.execute(() -> {
            doSearch(latitude, longitude);
        });
    }

    private void doSearch(Double latitude, Double longitude) {
        ioExecutorService.execute(() -> {
            try {
                String result = validationUsersUseCase.execute(latitude, longitude);

                mainExecutorService.execute(() -> {
                    searchHistoryItems.setValue(new Event<>(result));
                });
            } catch (Exception e) {
                logger.warn("An error occurred while searching users", e);

                mainExecutorService.execute(() -> {
                });
            }

            mainExecutorService.execute(() -> {
            });
        });
    }

    public LiveData<Event<String>> getSearchHistoryItems() {
        return searchHistoryItems;
    }
}
