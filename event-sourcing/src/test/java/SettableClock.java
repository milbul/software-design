import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;

public class SettableClock extends Clock {
    private Instant now;

    public SettableClock(LocalDateTime time) {
        now = time.atZone(ZoneId.systemDefault()).toInstant();
    }

    public void plus(long amountToAdd, TemporalUnit unit) {
        now = now.plus(amountToAdd, unit);
    }

    @Override
    public ZoneId getZone() {
        return ZoneId.systemDefault();
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
