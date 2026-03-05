package com.skillmirror.backend.service;
import com.skillmirror.backend.entity.TechnicalAttempt;
import com.skillmirror.backend.entity.User;
import com.skillmirror.backend.repository.TechnicalAttemptRepository;
import com.skillmirror.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TechnicalService {

    private final TechnicalAttemptRepository repository;
    private final UserRepository userRepository;

    public TechnicalService(TechnicalAttemptRepository repository,
                            UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Map<String, Object> evaluate(Long userId, String companyName,
                                        List<Integer> answers) {
        int total = answers.size();
        int correct = 0;

        for (int a : answers) {
            if (a == 1) correct++;
        }

        double percentage = ((double) correct / total) * 100;
        boolean passed = percentage >= 60;
        String status = passed ? "PASS" : "FAIL";

        // Save attempt
        TechnicalAttempt attempt = new TechnicalAttempt();
        attempt.setUserId(userId);
        attempt.setCompanyName(companyName);
        attempt.setCorrectAnswers(correct);
        attempt.setTotalQuestions(total);
        attempt.setPercentage(percentage);
        attempt.setStatus(status);
        attempt.setAttemptedAt(LocalDateTime.now());
        attempt.setPassed(passed);

        repository.save(attempt);

        // Update user progression
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passed) {
            user.setTechnicalPassed(true);
            user.setInterviewUnlocked(true);
        } else {
            user.setTechnicalPassed(false);
            user.setInterviewUnlocked(false);
        }

        userRepository.save(user);

        // Build response
        Map<String, Object> result = new HashMap<>();
        result.put("correct", correct);
        result.put("total", total);
        result.put("percentage", percentage);
        result.put("status", status);
        result.put("passed", passed);
        result.put("interviewUnlocked", user.isInterviewUnlocked());

        return result;
    }

    public List<TechnicalAttempt> getAttemptsByUser(Long userId) {
        return repository.findByUserId(userId);
    }
}
