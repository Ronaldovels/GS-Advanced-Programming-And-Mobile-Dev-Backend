package br.com.fiap.space_mission.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String severity;

    @Column(name = "monitored_system", nullable = false)
    private String monitoredSystem;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @PrePersist
    public void prePersist() {
        if (occurredAt == null) {
            occurredAt = LocalDateTime.now();
        }
    }
}
