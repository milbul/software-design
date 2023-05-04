package service;

import event.Event;
import event.ExitEvent;
import event.VisitEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ReportService {
    private final Map<Integer, LocalDateTime> lastVisits = new HashMap<>();
    private final Map<LocalDate, List<Integer>> dailyStats = new HashMap<>();

    public void handleEvent(int id, Event event) {
        if (event instanceof VisitEvent) {
            lastVisits.put(id, ((VisitEvent) event).getVisitDate());
        } else if (event instanceof ExitEvent) {
            int duration = (int) ChronoUnit.MINUTES.between(lastVisits.get(id), ((ExitEvent) event).getExitDate());
            LocalDate date = lastVisits.get(id).toLocalDate();
            if (!dailyStats.containsKey(date)) {
                dailyStats.put(date, new ArrayList<>());
            }
            dailyStats.get(date).add(duration);
        }
    }

    public double getAverageDuration() {
        return dailyStats.values()
                .stream()
                .flatMap(Collection::stream)
                .mapToDouble(i -> (double) i)
                .average()
                .orElse(0.0);
    }

    public double getAverageDailyVisits() {
        int count = dailyStats.values()
                .stream()
                .mapToInt(List::size)
                .sum();
        return dailyStats.isEmpty() ? 0.0 : ((double) count) / dailyStats.size();
    }
}
