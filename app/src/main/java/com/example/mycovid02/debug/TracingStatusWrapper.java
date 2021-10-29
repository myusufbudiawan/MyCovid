package com.example.mycovid02.debug;

import com.example.mycovid02.tracing.NotificationState;
import com.example.mycovid02.tracing.TracingErrorStateHelper;
import com.example.mycovid02.tracing.TracingState;
import com.example.mycovid02.tracing.TracingStatusInterface;

import org.dpppt.android.sdk.InfectionStatus;
import org.dpppt.android.sdk.TracingStatus;
import org.dpppt.android.sdk.internal.database.models.ExposureDay;

import java.util.List;

public class TracingStatusWrapper implements TracingStatusInterface {

    private TracingStatus status;

    @Override
    public void setStatus(TracingStatus status) {
        this.status = status;
    }

    @Override
    public boolean isReportedAsInfected() {
        return status.getInfectionStatus() == InfectionStatus.INFECTED;
    }

    @Override
    public List<ExposureDay> getExposureDays() {
        return status.getExposureDays();
    }

    @Override
    public boolean wasContactReportedAsExposed() {
        return status.getInfectionStatus() == InfectionStatus.EXPOSED;
    }

    @Override
    public TracingState getTracingState() {
        boolean tracingOff = !(status.isAdvertising() || status.isReceiving());
        return tracingOff ? TracingState.NOT_ACTIVE : TracingState.ACTIVE;
    }

    @Override
    public NotificationState getNotificationState() {
        if (isReportedAsInfected()) {
            return NotificationState.POSITIVE_TESTED;
        } else if (wasContactReportedAsExposed()) {
            return NotificationState.EXPOSED;
        } else {
            return NotificationState.NO_REPORTS;
        }
    }

    @Override
    public TracingStatus.ErrorState getTracingErrorState() {
        boolean hasError = status.getErrors().size() > 0;

        if(hasError) {
            return TracingErrorStateHelper.getErrorState(status.getErrors());
        }
        return null;
    }

    @Override
    public TracingStatus.ErrorState getReportErrorState() {
        return null;
    }

    @Override
    public long getDaysSinceExposure() {
        return 0;
    }
}
