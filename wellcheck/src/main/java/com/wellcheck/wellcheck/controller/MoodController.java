package com.wellcheck.wellcheck.controller;

import com.wellcheck.wellcheck.model.MoodLog;
import com.wellcheck.wellcheck.service.MoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/mood")
@RequiredArgsConstructor
public class MoodController {

    private final MoodService moodService;

    @GetMapping
    public String moodPage(Authentication auth, Model model) {
        MoodLog log = moodService.getTodayLog(auth.getName());
        List<MoodLog> history = moodService.getLast7Days(auth.getName());
        model.addAttribute("log", log);
        model.addAttribute("history", history);
        return "tracker/mood";
    }

    @PostMapping
    public String saveMood(@RequestParam int mood,
                           @RequestParam int energy,
                           @RequestParam(required = false, defaultValue = "") String note,
                           Authentication auth) {
        moodService.saveLog(auth.getName(), mood, energy, note);
        return "redirect:/mood";
    }
}