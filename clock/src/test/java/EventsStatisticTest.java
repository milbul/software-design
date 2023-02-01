import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class EventsStatisticTest {
    private SettableClock clock;
    private EventsStatisticImpl stats;
    private static final double EPS = 1e-4;

    @Before
    public void before() {
        clock = new SettableClock();
        stats = new EventsStatisticImpl(clock);
    }

    @Test
    public void oneEventTest() {
        String name = "first";
        stats.incEvent(name);
        stats.incEvent(name);
        Assert.assertEquals(2 / 60d, stats.getEventStatisticByName(name), EPS);
        Assert.assertEquals(0d, stats.getEventStatisticByName("second"), EPS);
    }

    @Test
    public void hourBeforeTest() {
        String name = "first";
        stats.incEvent(name);
        clock.plus(20, ChronoUnit.MINUTES);
        stats.incEvent(name);
        clock.plus(40, ChronoUnit.MINUTES);
        stats.incEvent(name);
        Assert.assertEquals(2 / 60d, stats.getEventStatisticByName(name), EPS);
    }

    @Test
    public void multipleEventsTest() {
        for (int i = 0; i < 50; i++) {
            stats.incEvent(Integer.toString(i));
        }
        clock.plus(30, ChronoUnit.MINUTES);
        for (int i = 50; i < 100; i++) {
            stats.incEvent(Integer.toString(i));
        }
        clock.plus(30, ChronoUnit.MINUTES);
        for (int i = 0; i < 100; i++) {
            stats.incEvent(Integer.toString(i));
        }
        Map<String, Double> statistics = stats.getAllEventStatistic();
        Map<String, Double> expected = new HashMap<>();
        for (int i = 0; i < 50; ++i) {
            expected.put(Integer.toString(i), 1 / 60d);
        }
        for (int i = 50; i < 100; ++i) {
            expected.put(Integer.toString(i), 2 / 60d);
        }
        Assert.assertEquals(statistics, expected);
        clock.plus(60, ChronoUnit.MINUTES);
        Assert.assertTrue(stats.getAllEventStatistic().isEmpty());
    }
}
