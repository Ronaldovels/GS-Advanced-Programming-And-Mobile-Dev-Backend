package br.com.fiap.space_mission.controller;

import br.com.fiap.space_mission.model.Alert;
import br.com.fiap.space_mission.repository.AlertRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @GetMapping
    public List<Alert> findAll(
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String alertType,
            @RequestParam(required = false) Long sensorId,
            @RequestParam(required = false) Long eventId) {
        if (severity != null) return alertRepository.findBySeverity(severity);
        if (status != null) return alertRepository.findByStatus(status);
        if (alertType != null) return alertRepository.findByAlertType(alertType);
        if (sensorId != null) return alertRepository.findBySensorId(sensorId);
        if (eventId != null) return alertRepository.findByEventId(eventId);
        return alertRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alert> findById(@PathVariable Long id) {
        return alertRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Alert> create(@RequestBody Alert alert) {
        Alert saved = alertRepository.save(alert);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alert> update(@PathVariable Long id, @RequestBody Alert alert) {
        if (!alertRepository.existsById(id)) return ResponseEntity.notFound().build();
        alert.setId(id);
        return ResponseEntity.ok(alertRepository.save(alert));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!alertRepository.existsById(id)) return ResponseEntity.notFound().build();
        alertRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
