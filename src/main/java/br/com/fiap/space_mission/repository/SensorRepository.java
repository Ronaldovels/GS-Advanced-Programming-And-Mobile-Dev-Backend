package br.com.fiap.space_mission.repository;

import br.com.fiap.space_mission.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findByStatus(String status);

    List<Sensor> findByType(String type);
}
