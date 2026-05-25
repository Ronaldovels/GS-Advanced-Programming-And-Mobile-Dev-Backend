package br.com.fiap.space_mission.controller;

import br.com.fiap.space_mission.model.Sensor;
import br.com.fiap.space_mission.repository.SensorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorRepository sensorRepository;

    public SensorController(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @GetMapping
    public List<Sensor> findAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type) {
        if (status != null) return sensorRepository.findByStatus(status);
        if (type != null) return sensorRepository.findByType(type);
        return sensorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> findById(@PathVariable Long id) {
        return sensorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sensor> create(@RequestBody Sensor sensor) {
        Sensor saved = sensorRepository.save(sensor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> update(@PathVariable Long id, @RequestBody Sensor sensor) {
        if (!sensorRepository.existsById(id)) return ResponseEntity.notFound().build();
        sensor.setId(id);
        return ResponseEntity.ok(sensorRepository.save(sensor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!sensorRepository.existsById(id)) return ResponseEntity.notFound().build();
        sensorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
