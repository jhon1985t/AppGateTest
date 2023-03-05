package com.jhonjto.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import com.jhonjto.common.Logger;
import com.jhonjto.domain.interfaces.ConnectionDataSource;
import com.jhonjto.domain.usecases.ValidationUsersUseCase;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class DomainUnitTest {

    private ValidationUsersUseCase validationUsersUseCase;

    @Mock
    private ConnectionDataSource connectionDataSource;
    @Mock
    private Logger logger = new NoOpLogger();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        validationUsersUseCase = new ValidationUsersUseCase(connectionDataSource, logger);
    }

    @Test
    public void testExecute() throws Exception {
        String result = "{\n" +
                "  \"timeZone\": \"America/New_York\",\n" +
                "  \"currentLocalTime\": \"2023-02-20T08:51:43.2121759\",\n" +
                "  \"currentUtcOffset\": {\n" +
                "    \"seconds\": -18000,\n" +
                "    \"milliseconds\": -18000000,\n" +
                "    \"ticks\": -180000000000,\n" +
                "    \"nanoseconds\": -18000000000000\n" +
                "  },\n" +
                "  \"standardUtcOffset\": {\n" +
                "    \"seconds\": -18000,\n" +
                "    \"milliseconds\": -18000000,\n" +
                "    \"ticks\": -180000000000,\n" +
                "    \"nanoseconds\": -18000000000000\n" +
                "  },\n" +
                "  \"hasDayLightSaving\": true,\n" +
                "  \"isDayLightSavingActive\": false,\n" +
                "  \"dstInterval\": {\n" +
                "    \"dstName\": \"EDT\",\n" +
                "    \"dstOffsetToUtc\": {\n" +
                "      \"seconds\": -14400,\n" +
                "      \"milliseconds\": -14400000,\n" +
                "      \"ticks\": -144000000000,\n" +
                "      \"nanoseconds\": -14400000000000\n" +
                "    },\n" +
                "    \"dstOffsetToStandardTime\": {\n" +
                "      \"seconds\": 3600,\n" +
                "      \"milliseconds\": 3600000,\n" +
                "      \"ticks\": 36000000000,\n" +
                "      \"nanoseconds\": 3600000000000\n" +
                "    },\n" +
                "    \"dstStart\": \"2023-03-12T07:00:00Z\",\n" +
                "    \"dstEnd\": \"2023-11-05T06:00:00Z\",\n" +
                "    \"dstDuration\": {\n" +
                "      \"days\": 237,\n" +
                "      \"nanosecondOfDay\": 82800000000000,\n" +
                "      \"hours\": 23,\n" +
                "      \"minutes\": 0,\n" +
                "      \"seconds\": 0,\n" +
                "      \"milliseconds\": 0,\n" +
                "      \"subsecondTicks\": 0,\n" +
                "      \"subsecondNanoseconds\": 0,\n" +
                "      \"bclCompatibleTicks\": 205596000000000,\n" +
                "      \"totalDays\": 237.95833333333334,\n" +
                "      \"totalHours\": 5711,\n" +
                "      \"totalMinutes\": 342660,\n" +
                "      \"totalSeconds\": 20559600,\n" +
                "      \"totalMilliseconds\": 20559600000,\n" +
                "      \"totalTicks\": 205596000000000,\n" +
                "      \"totalNanoseconds\": 20559600000000000\n" +
                "    }\n" +
                "  }\n" +
                "}";

        Mockito.when(connectionDataSource.getValidationsUser(38.9, -77.03)).thenReturn(result);

        assertEquals(result, validationUsersUseCase.execute(38.9, -77.03));
    }
}