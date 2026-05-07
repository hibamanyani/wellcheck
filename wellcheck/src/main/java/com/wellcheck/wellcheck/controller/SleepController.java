package com.wellcheck.wellcheck.controller;

import com.wellcheck.wellcheck.model.SleepLog;
import com.wellcheck.wellcheck.service.SleepService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/sleep")
@RequiredArgsConstructor
public class SleepController {

    private final SleepService sleepService;

    @GetMapping
    public String sleepPage(Authentication auth, Model model) {
        SleepLog log = sleepService.getTodayLog(auth.getName());
        List<SleepLog> history = sleepService.getLast7Days(auth.getName());
        boolean belowRecommended = log.getHoursSlept() < 8 && log.getHoursSlept() > 0;
        model.addAttribute("log", log);
        model.addAttribute("history", history);
        model.addAttribute("belowRecommended", belowRecommended);
        return "tracker/sleep";
    }

    @PostMapping
    public String saveSleep(@RequestParam double hours,
                            Authentication auth) {
        sleepService.saveLog(auth.getName(), hours);
        return "redirect:/sleep";
    }
}