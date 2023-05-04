package event;

import java.time.LocalDateTime;

public class VisitEvent implements Event {
    private final LocalDateTime visitDate;

    public VisitEvent(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }
}
