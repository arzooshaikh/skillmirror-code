package com.skillmirror.backend.controller;
import com.skillmirror.backend.service.TechnicalService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/technical")
public class TechnicalController {

    private final TechnicalService technicalService;

    public TechnicalController(TechnicalService technicalService) {
        this.technicalService = technicalService;
    }

    @PostMapping("/evaluate")
    public Map<String, Object> evaluateTechnical(@RequestBody Map<String, Object> data) {
        Long userId = Long.valueOf(data.get("userId").toString());
        String companyName = data.get("companyName").toString();
        List<Integer> answers = (List<Integer>) data.get("answers");

        return technicalService.evaluate(userId, companyName, answers);
    }
}