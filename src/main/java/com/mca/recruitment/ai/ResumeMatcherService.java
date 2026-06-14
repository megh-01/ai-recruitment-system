package com.mca.recruitment.ai;

import org.springframework.stereotype.Service;

@Service
public class ResumeMatcherService {

    public double calculateMatch(String candidateSkills, String requiredSkills) {

        if (candidateSkills == null || candidateSkills.trim().isEmpty()) {
            return 0;
        }

        if (requiredSkills == null || requiredSkills.trim().isEmpty()) {
            return 0;
        }

        String candidateText = normalize(candidateSkills);
        String[] requiredSkillArray = requiredSkills.split(",");

        int totalSkills = 0;
        int matchedSkills = 0;

        for (String skill : requiredSkillArray) {

            String cleanSkill = normalize(skill);

            if (!cleanSkill.isEmpty()) {
                totalSkills++;

                if (candidateText.contains(cleanSkill)) {
                    matchedSkills++;
                }
            }
        }

        if (totalSkills == 0) {
            return 0;
        }

        return ((double) matchedSkills / totalSkills) * 100;
    }

    private String normalize(String text) {
        return text
                .toLowerCase()
                .replace(" ", "")
                .replace("-", "")
                .replace("_", "")
                .replace(".", "")
                .replace("/", "")
                .trim();
    }
}