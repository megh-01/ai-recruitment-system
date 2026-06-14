package com.mca.recruitment.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendApplicationResult(
            String candidateEmail,
            String candidateName,
            String jobTitle,
            String companyName,
            double matchPercentage,
            double threshold,
            boolean selected) {

        String formattedPercentage =
                String.format(Locale.US, "%.2f", matchPercentage);

        String formattedThreshold =
                String.format(Locale.US, "%.2f", threshold);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderEmail);
        message.setTo(candidateEmail);

        if (selected) {
            message.setSubject(
                    "Congratulations! Selected for the Next Round – " + jobTitle
            );

            message.setText(
                    "Dear " + candidateName + ",\n\n" +

                    "Congratulations!\n\n" +

                    "Your resume has achieved a match score of "
                    + formattedPercentage + "% for the position of "
                    + jobTitle + " at " + companyName + ".\n\n" +

                    "The required selection score was "
                    + formattedThreshold + "%.\n\n" +

                    "You have been selected to proceed to the next stage " +
                    "of the recruitment process. Our recruitment team will " +
                    "contact you regarding the further procedure.\n\n" +

                    "We wish you all the best.\n\n" +

                    "Regards,\n" +
                    companyName + " Recruitment Team"
            );

        } else {
            message.setSubject(
                    "Application Update – " + jobTitle
            );

            message.setText(
                    "Dear " + candidateName + ",\n\n" +

                    "Thank you for applying for the position of "
                    + jobTitle + " at " + companyName + ".\n\n" +

                    "Your resume has achieved a match score of "
                    + formattedPercentage + "%.\n\n" +

                    "The required selection score was "
                    + formattedThreshold + "%.\n\n" +

                    "Unfortunately, your profile has not been selected " +
                    "for the next stage of the recruitment process.\n\n" +

                    "We appreciate your interest in our company and " +
                    "encourage you to apply for future opportunities that " +
                    "match your skills and experience.\n\n" +

                    "Better luck next time.\n\n" +

                    "Regards,\n" +
                    companyName + " Recruitment Team"
            );
        }

        mailSender.send(message);
    }
}