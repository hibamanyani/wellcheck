package com.wellcheck.wellcheck.repository;

import com.wellcheck.wellcheck.model.MoodLog;
import com.wellcheck.wellcheck.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MoodRepository extends JpaRepository<MoodLog, Long> {
    Optional<MoodLog> findByUserAndDate(User user, LocalDate date);
    List<MoodLog> findByUserOrderByDateDesc(User user);
}
