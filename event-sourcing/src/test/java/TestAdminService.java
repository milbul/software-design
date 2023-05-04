import model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.AdminService;
import storage.EventStorage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TestAdminService {
    private LocalDateTime now;
    private SettableClock clock;
    private AdminService adminService;

    @Before
    public void before() {
        now = LocalDateTime.now();
        clock = new SettableClock(now);
        EventStorage eventStorage = new EventStorage();
        adminService = new AdminService(clock, eventStorage);
    }

    @Test
    public void test() {
        adminService.createAccount(1);
        adminService.createAccount(2);
        adminService.extendExpiration(1, now.plus(10, ChronoUnit.DAYS));
        clock.plus(5, ChronoUnit.DAYS);
        Account account = adminService.getAccountInformation(1);

        Assert.assertEquals(1, account.getId());
        Assert.assertEquals(now.plus(10, ChronoUnit.DAYS), account.getExpirationDate());
    }
}
