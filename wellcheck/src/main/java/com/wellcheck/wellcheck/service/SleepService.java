package com.wellcheck.wellcheck.service;

import com.wellcheck.wellcheck.model.SleepLog;
import com.wellcheck.wellcheck.model.User;
import com.wellcheck.wellcheck.repository.SleepRepository;
import com.wellcheck.wellcheck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SleepService {

    private final SleepRepository sleepRepository;
    private final UserRepository userRepository;

    public SleepLog getTodayLog(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return sleepRepository.findByUserAndDate(user, LocalDate.now())
            .orElseGet(() -> {
                SleepLog log = new SleepLog();
                log.setUser(user);
                log.setDate(LocalDate.now());
                log.setHoursSlept(0);
                return log;
            });
    }

    public void saveLog(String email, double hours) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Optional<SleepLog> existing = sleepRepository.findByUserAndDate(user, LocalDate.now());
        SleepLog log = existing.orElseGet(() -> {
            SleepLog l = new SleepLog();
            l.setUser(user);
            l.setDate(LocalDate.now());
            return l;
        });
        log.setHoursSlept(hours);
        sleepRepository.save(log);
    }

    public List<SleepLog> getLast7Days(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return sleepRepository.findByUserOrderByDateDesc(user)
            .stream()
            .limit(7)
            .toList();
    }
}