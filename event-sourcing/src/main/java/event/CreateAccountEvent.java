package event;

import java.time.LocalDateTime;

public class CreateAccountEvent implements Event {
    private final LocalDateTime creationDate;

    public CreateAccountEvent(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
