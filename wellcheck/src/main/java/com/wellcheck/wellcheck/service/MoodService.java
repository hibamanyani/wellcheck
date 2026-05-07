package com.wellcheck.wellcheck.service;

import com.wellcheck.wellcheck.model.MoodLog;
import com.wellcheck.wellcheck.model.User;
import com.wellcheck.wellcheck.repository.MoodRepository;
import com.wellcheck.wellcheck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoodService {

    private final MoodRepository moodRepository;
    private final UserRepository userRepository;

    public MoodLog getTodayLog(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return moodRepository.findByUserAndDate(user, LocalDate.now())
            .orElseGet(() -> {
                MoodLog log = new MoodLog();
                log.setUser(user);
                log.setDate(LocalDate.now());
                log.setMoodScore(0);
                log.setEnergyScore(0);
                log.setNote("");
                return log;
            });
    }

    public void saveLog(String email, int mood, int energy, String note) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Optional<MoodLog> existing = moodRepository.findByUserAndDate(user, LocalDate.now());
        MoodLog log = existing.orElseGet(() -> {
            MoodLog l = new MoodLog();
            l.setUser(user);
            l.setDate(LocalDate.now());
            return l;
        });
        log.setMoodScore(mood);
        log.setEnergyScore(energy);
        log.setNote(note);
        moodRepository.save(log);
    }

    public List<MoodLog> getLast7Days(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return moodRepository.findByUserOrderByDateDesc(user)
            .stream()
            .limit(7)
            .toList();
    }
}