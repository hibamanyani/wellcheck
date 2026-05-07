package com.wellcheck.wellcheck.repository;

import com.wellcheck.wellcheck.model.SleepLog;
import com.wellcheck.wellcheck.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SleepRepository extends JpaRepository<SleepLog, Long> {
    Optional<SleepLog> findByUserAndDate(User user, LocalDate date);
    List<SleepLog> findByUserOrderByDateDesc(User user);
}
