package br.com.fiap.space_mission.controller;

import br.com.fiap.space_mission.model.Event;
import br.com.fiap.space_mission.repository.EventRepository;
import br.com.fiap.space_mission.repository.SensorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;
    private final SensorRepository sensorRepository;

    public EventController(EventRepository eventRepository, SensorRepository sensorRepository) {
        this.eventRepository = eventRepository;
        this.sensorRepository = sensorRepository;
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
        resolveReferences(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventRepository.save(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody Event event) {
        if (!eventRepository.existsById(id)) return ResponseEntity.notFound().build();
        event.setId(id);
        resolveReferences(event);
        return ResponseEntity.ok(eventRepository.save(event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!eventRepository.existsById(id)) return ResponseEntity.notFound().build();
        eventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void resolveReferences(Event event) {
        if (event.getSensor() != null && event.getSensor().getId() != null) {
            event.setSensor(sensorRepository.findById(event.getSensor().getId()).orElse(null));
        }
    }
}
