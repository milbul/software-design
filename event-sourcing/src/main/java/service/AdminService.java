package service;

import event.CreateAccountEvent;
import event.Event;
import event.ExtendExpirationEvent;
import event.VisitEvent;
import model.Account;
import storage.EventStorage;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class AdminService {
    private final Clock clock;
    private final EventStorage eventStorage;

    public AdminService(Clock clock, EventStorage eventStorage) {
        this.clock = clock;
        this.eventStorage = eventStorage;
    }

    public void createAccount(int id) {
        eventStorage.save(id, new CreateAccountEvent(LocalDateTime.now(clock)));
    }

    public void extendExpiration(int id, LocalDateTime newDate) {
        eventStorage.save(id, new ExtendExpirationEvent(newDate));
    }

    public Account getAccountInformation(int id) {
        List<Event> events = eventStorage.getAccountEvent(id);
        if (!(events.get(0) instanceof CreateAccountEvent)) {
            throw new RuntimeException("Account is not created");
        }
        Account account = null;
        for (Event event : events) {
            if (event instanceof CreateAccountEvent) {
                account = new Account(id, ((CreateAccountEvent) event).getCreationDate());
            }
            if (event instanceof ExtendExpirationEvent) {
                assert account != null;
                account.setExpirationDate(((ExtendExpirationEvent) event).getExpirationDate());
            }
            if (event instanceof VisitEvent) {
                assert account != null;
                account.setLastVisitDate(((VisitEvent) event).getVisitDate());
            }
        }
        return account;
    }
}
