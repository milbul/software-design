package service;

import event.*;
import storage.EventStorage;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class TurnstileService {
    private final EventStorage eventStorage;
    private final Clock clock;

    public TurnstileService(Clock clock, EventStorage eventStorage) {
        this.eventStorage = eventStorage;
        this.clock = clock;
    }

    public void visit(int id) {
        List<Event> events = eventStorage.getAccountEvent(id);
        if (events == null) {
            throw new RuntimeException("Not found event for account, id: " + id);
        }
        if (!(events.get(0) instanceof CreateAccountEvent)) {
            throw new RuntimeException("Not found event for creation account, id: " + id);
        }
        LocalDateTime expiration = null;
        for (Event event : events) {
            if (event instanceof CreateAccountEvent) {
                expiration = ((CreateAccountEvent) event).getCreationDate();
            }
            if (event instanceof ExtendExpirationEvent) {
                expiration = ((ExtendExpirationEvent) event).getExpirationDate();
            }
        }
        if (expiration.isBefore(LocalDateTime.now(clock))) {
            throw new RuntimeException("Expired account, id: " + id);
        }
        eventStorage.save(id, new VisitEvent(LocalDateTime.now(clock)));
    }

    public void exit(int id) {
        eventStorage.save(id, new ExitEvent(LocalDateTime.now(clock)));
    }
}
