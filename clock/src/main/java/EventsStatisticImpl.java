import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventsStatisticImpl implements EventsStatistic {
    Map<String, List<Instant>> events = new HashMap<>();
    private final Clock clock;

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    public void incEvent(String name) {
        if (!events.containsKey(name)) {
            events.put(name, new ArrayList<>());
        }
        events.get(name).add(clock.instant());
    }

    public double getEventStatisticByName(String name) {
        if (!events.containsKey(name)) {
            return 0.0;
        }
        Instant current = clock.instant();
        Instant hourAgo = current.minus(1, ChronoUnit.HOURS);
        return events.get(name).stream()
                .filter(ev -> ev.isAfter(hourAgo))
                .count() / 60d;
    }

    public Map<String, Double> getAllEventStatistic() {
        Map<String, Double> stats = events.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, en -> getEventStatisticByName(en.getKey())));
        stats.values().removeIf(value -> value == 0d);
        return stats;
    }

    public void printStatistic() {
        Map<String, Double> stats = getAllEventStatistic();
        for (String name : stats.keySet()) {
            System.out.printf("rpm for %s is %f%n", name, stats.get(name));
        }
    }
}