import model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.AdminService;
import service.TurnstileService;
import storage.EventStorage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TestTurnstileService {
    private LocalDateTime now;
    private SettableClock clock;
    private EventStorage eventStorage;
    private AdminService adminService;
    private TurnstileService turnstileService;

    @Before
    public void before() {
        now = LocalDateTime.now();
        clock = new SettableClock(now);
        eventStorage = new EventStorage();
        adminService = new AdminService(clock, eventStorage);
        turnstileService = new TurnstileService(clock, eventStorage);
    }

    @Test
    public void testTurnstile() {
        adminService.createAccount(1);
        adminService.createAccount(2);
        adminService.extendExpiration(1, now.plus(30, ChronoUnit.DAYS));
        adminService.extendExpiration(2, now.plus(10, ChronoUnit.DAYS));

        turnstileService.visit(1);
        turnstileService.visit(2);
        clock.plus(1, ChronoUnit.HOURS);
        turnstileService.exit(1);
        turnstileService.exit(2);

        clock.plus(10, ChronoUnit.DAYS);
        turnstileService.visit(1);
        clock.plus(2, ChronoUnit.HOURS);
        turnstileService.exit(1);

        Account account1 = adminService.getAccountInformation(1);
        Account account2 = adminService.getAccountInformation(2);

        Assert.assertEquals(now.plusHours(10 * 24 + 1), account1.getLastVisitDate());
        Assert.assertEquals(now, account2.getLastVisitDate());
        Assert.assertThrows(RuntimeException.class, () -> turnstileService.visit(2));

    }
}
