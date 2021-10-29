package com.example.mycovid02.tracing;

import org.dpppt.android.sdk.TracingStatus;
import org.dpppt.android.sdk.internal.database.models.ExposureDay;

import java.util.List;

public interface TracingStatusInterface {

    void setStatus(TracingStatus status);

    boolean isReportedAsInfected();

    List<ExposureDay> getExposureDays();

    boolean wasContactReportedAsExposed();

    TracingState getTracingState();

    NotificationState getNotificationState();

    TracingStatus.ErrorState getTracingErrorState();

    TracingStatus.ErrorState getReportErrorState();

    long getDaysSinceExposure();
}
