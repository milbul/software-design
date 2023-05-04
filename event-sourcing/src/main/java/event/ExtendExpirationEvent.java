package event;

import java.time.LocalDateTime;

public class ExtendExpirationEvent implements Event {
    private final LocalDateTime expirationDate;

    public ExtendExpirationEvent(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
