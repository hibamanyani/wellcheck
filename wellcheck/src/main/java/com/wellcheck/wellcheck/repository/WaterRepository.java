package com.wellcheck.wellcheck.repository;

import com.wellcheck.wellcheck.model.WaterLog;
import com.wellcheck.wellcheck.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WaterRepository extends JpaRepository<WaterLog, Long> {
    Optional<WaterLog> findByUserAndDate(User user, LocalDate date);
    List<WaterLog> findByUserOrderByDateDesc(User user);
}