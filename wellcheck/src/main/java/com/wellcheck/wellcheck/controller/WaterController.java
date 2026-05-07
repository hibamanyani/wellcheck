package com.wellcheck.wellcheck.controller;

import com.wellcheck.wellcheck.model.WaterLog;
import com.wellcheck.wellcheck.service.WaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/water")
@RequiredArgsConstructor
public class WaterController {

    private final WaterService waterService;

    @GetMapping
    public String waterPage(Authentication auth, Model model) {
        WaterLog log = waterService.getTodayLog(auth.getName());
        int percent = (int) ((log.getGlasses() * 100.0) / log.getGoalGlasses());
        model.addAttribute("log", log);
        model.addAttribute("percent", Math.min(percent, 100));
        return "tracker/water";
    }

    @PostMapping
    public String saveWater(@RequestParam int glasses,
                            @RequestParam int goal,
                            Authentication auth) {
        waterService.saveLog(auth.getName(), glasses, goal);
        return "redirect:/water";
    }
}