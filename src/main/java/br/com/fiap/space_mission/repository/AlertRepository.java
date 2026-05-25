package br.com.fiap.space_mission.repository;

import br.com.fiap.space_mission.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findBySeverity(String severity);

    List<Alert> findByStatus(String status);

    List<Alert> findByAlertType(String alertType);

    List<Alert> findBySensorId(Long sensorId);

    List<Alert> findByEventId(Long eventId);
}
