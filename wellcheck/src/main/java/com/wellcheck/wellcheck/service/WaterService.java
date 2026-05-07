package com.wellcheck.wellcheck.service;

import com.wellcheck.wellcheck.model.User;
import com.wellcheck.wellcheck.model.WaterLog;
import com.wellcheck.wellcheck.repository.UserRepository;
import com.wellcheck.wellcheck.repository.WaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WaterService {

    private final WaterRepository waterRepository;
    private final UserRepository userRepository;

    public WaterLog getTodayLog(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return waterRepository.findByUserAndDate(user, LocalDate.now())
            .orElseGet(() -> {
                WaterLog log = new WaterLog();
                log.setUser(user);
                log.setDate(LocalDate.now());
                log.setGlasses(0);
                return log;
            });
    }

    public void saveLog(String email, int glasses, int goal) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Optional<WaterLog> existing = waterRepository.findByUserAndDate(user, LocalDate.now());
        WaterLog log = existing.orElseGet(() -> {
            WaterLog l = new WaterLog();
            l.setUser(user);
            l.setDate(LocalDate.now());
            return l;
        });
        log.setGlasses(glasses);
        log.setGoalGlasses(goal);
        waterRepository.save(log);
    }
}