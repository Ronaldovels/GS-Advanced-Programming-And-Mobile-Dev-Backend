package br.com.fiap.space_mission.repository;

import br.com.fiap.space_mission.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findBySeverity(String severity);

    List<Event> findByEventType(String eventType);

    List<Event> findByMonitoredSystem(String monitoredSystem);

    List<Event> findBySensorId(Long sensorId);
}
