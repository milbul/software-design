import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.AdminService;
import service.ReportService;
import service.TurnstileService;
import storage.EventStorage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TestReportService {
    private LocalDateTime now;
    private SettableClock clock;
    private EventStorage eventStorage;
    private AdminService adminService;
    private TurnstileService turnstileService;
    private ReportService reportService;
    private static final double EPS = 1e-4;

    @Before
    public void before() {
        now = LocalDateTime.now();
        clock = new SettableClock(now);
        eventStorage = new EventStorage();
        adminService = new AdminService(clock, eventStorage);
        turnstileService = new TurnstileService(clock, eventStorage);
        reportService = new ReportService();
    }

    @Test
    public void testStats() {
        eventStorage.subscribe(reportService);
        adminService.createAccount(1);
        adminService.createAccount(2);
        adminService.extendExpiration(1, now.plus(30, ChronoUnit.DAYS));
        adminService.extendExpiration(2, now.plus(30, ChronoUnit.DAYS));

        Assert.assertEquals(0.0, reportService.getAverageDailyVisits(), EPS);

        turnstileService.visit(1);
        turnstileService.visit(2);
        clock.plus(1, ChronoUnit.HOURS);
        turnstileService.exit(1);

        clock.plus(3, ChronoUnit.HOURS);
        turnstileService.exit(2);

        clock.plus(1, ChronoUnit.DAYS);
        turnstileService.visit(1);
        clock.plus(1, ChronoUnit.HOURS);
        turnstileService.exit(1);

        clock.plus(2, ChronoUnit.DAYS);

        turnstileService.visit(2);
        clock.plus(2, ChronoUnit.HOURS);
        turnstileService.exit(2);

        Assert.assertEquals(4.0 / 3, reportService.getAverageDailyVisits(), EPS);
        Assert.assertEquals(2 * 60, reportService.getAverageDuration(), EPS);
    }
}
