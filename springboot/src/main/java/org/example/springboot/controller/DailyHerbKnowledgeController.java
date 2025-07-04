package org.example.springboot.controller;

import org.example.springboot.entity.DailyHerbKnowledge;
import org.example.springboot.service.DailyHerbKnowledgeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dailyHerbKnowledge")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class DailyHerbKnowledgeController {

    private final DailyHerbKnowledgeService dailyHerbKnowledgeService;

    public DailyHerbKnowledgeController(DailyHerbKnowledgeService dailyHerbKnowledgeService) {
        this.dailyHerbKnowledgeService = dailyHerbKnowledgeService;
    }

    @GetMapping("/selectAll")
    public List<DailyHerbKnowledge> selectAll() {
        return dailyHerbKnowledgeService.selectAll();
    }

    @GetMapping("/selectByDate")
    public DailyHerbKnowledge selectByDate(@RequestParam String date) {
        return dailyHerbKnowledgeService.selectByDate(date);
    }
}
