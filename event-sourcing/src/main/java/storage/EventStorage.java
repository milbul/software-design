package storage;

import event.Event;
import service.ReportService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventStorage {
    private final Map<Integer, List<Event>> events = new HashMap<>();
    private ReportService reportService = new ReportService();

    public List<Event> getAccountEvent(int id) {
        return events.get(id);
    }

    public void save(int id, Event event) {
        if (!events.containsKey(id)) {
            events.put(id, new ArrayList<>());
        }
        events.get(id).add(event);
        reportService.handleEvent(id, event);
    }

    public void subscribe(ReportService reportService) {
        this.reportService = reportService;
        events.forEach((id, accountEvents) ->
                accountEvents.forEach(event ->
                        reportService.handleEvent(id, event)));
    }
}
