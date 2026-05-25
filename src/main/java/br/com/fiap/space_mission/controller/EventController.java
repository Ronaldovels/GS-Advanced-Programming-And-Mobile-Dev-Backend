package br.com.fiap.space_mission.controller;

import br.com.fiap.space_mission.model.Event;
import br.com.fiap.space_mission.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<Event> findAll(
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String eventType,
            @RequestParam(required = false) String monitoredSystem,
            @RequestParam(required = false) Long sensorId) {
        if (severity != null) return eventRepository.findBySeverity(severity);
        if (eventType != null) return eventRepository.findByEventType(eventType);
        if (monitoredSystem != null) return eventRepository.findByMonitoredSystem(monitoredSystem);
        if (sensorId != null) return eventRepository.findBySensorId(sensorId);
        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        Event saved = eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody Event event) {
        if (!eventRepository.existsById(id)) return ResponseEntity.notFound().build();
        event.setId(id);
        return ResponseEntity.ok(eventRepository.save(event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!eventRepository.existsById(id)) return ResponseEntity.notFound().build();
        eventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
