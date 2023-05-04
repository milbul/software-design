package event;

import java.time.LocalDateTime;

public class ExitEvent implements Event {
    private final LocalDateTime exitDate;

    public ExitEvent(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }
}
