import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;

public class SettableClock extends Clock {
    private Instant now;

    public SettableClock() {
        now = Instant.now();
    }

    public void plus(long amountToAdd, TemporalUnit unit) {
        now = now.plus(amountToAdd, unit);
    }

    @Override
    public ZoneId getZone() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Instant instant() {
        return now;
    }
}
