package com.mca.recruitment.ai;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillExtractorService {

    public String extractSkills(String resumeText) {

        if (resumeText == null || resumeText.isBlank()) {
            return "";
        }

        String lowerText = resumeText.toLowerCase();

        String[] skillList = {
                "java",
                "spring boot",
                "spring",
                "hibernate",
                "jpa",
                "postgresql",
                "mysql",
                "sql",
                "html",
                "css",
                "javascript",
                "react",
                "angular",
                "python",
                "machine learning",
                "artificial intelligence",
                "rest api",
                "microservices",
                "git",
                "github",
                "maven",
                "eclipse",
                "sts"
        };

        List<String> foundSkills = new ArrayList<>();

        for (String skill : skillList) {
            if (lowerText.contains(skill)) {
                foundSkills.add(formatSkill(skill));
            }
        }

        return String.join(", ", foundSkills);
    }

    private String formatSkill(String skill) {

        if ("java".equals(skill)) {
            return "Java";
        } else if ("spring boot".equals(skill)) {
            return "Spring Boot";
        } else if ("postgresql".equals(skill)) {
            return "PostgreSQL";
        } else if ("mysql".equals(skill)) {
            return "MySQL";
        } else if ("sql".equals(skill)) {
            return "SQL";
        } else if ("html".equals(skill)) {
            return "HTML";
        } else if ("css".equals(skill)) {
            return "CSS";
        } else if ("javascript".equals(skill)) {
            return "JavaScript";
        } else if ("react".equals(skill)) {
            return "React";
        } else if ("angular".equals(skill)) {
            return "Angular";
        } else if ("python".equals(skill)) {
            return "Python";
        } else if ("machine learning".equals(skill)) {
            return "Machine Learning";
        } else if ("artificial intelligence".equals(skill)) {
            return "Artificial Intelligence";
        } else if ("rest api".equals(skill)) {
            return "REST API";
        } else if ("microservices".equals(skill)) {
            return "Microservices";
        } else if ("git".equals(skill)) {
            return "Git";
        } else if ("github".equals(skill)) {
            return "GitHub";
        } else if ("maven".equals(skill)) {
            return "Maven";
        } else if ("eclipse".equals(skill)) {
            return "Eclipse";
        } else if ("sts".equals(skill)) {
            return "STS";
        }

        return skill;
    }
}