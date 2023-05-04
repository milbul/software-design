package model;

import java.time.LocalDateTime;

public class Account {
    private final int id;
    private LocalDateTime expirationDate;
    private LocalDateTime lastVisitDate;

    public Account(int id, LocalDateTime expirationDate) {
        this.id = id;
        this.expirationDate = expirationDate;
        lastVisitDate = null;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public LocalDateTime getLastVisitDate() {
        return lastVisitDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setLastVisitDate(LocalDateTime lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }
}
