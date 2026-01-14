package com.skillmirror.backend.controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@CrossOrigin
@RequestMapping("/api/aptitude")
public class AptitudeController
{
    @PostMapping("/evaluate")
    public Map<String, Object> evaluate(@RequestBody Map<String, Object> data)
    {
        List<Integer> answers = (List<Integer>) data.get("answers");
        int total = answers.size();
        int correct = 0;
        for (int a : answers)
        {
            if (a == 1)
            {
                correct++;
            }
        }
        double percentage = ((double) correct / total) * 100;
        Map<String, Object> result = new HashMap<>();
        result.put("score", correct + "/" + total);
        result.put("percentage", percentage);
        result.put("status", percentage >= 80 ? "PASS" : "FAIL");
        return result;
    }
}
