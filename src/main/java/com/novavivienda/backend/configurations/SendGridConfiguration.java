package com.novavivienda.backend.configurations;

import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@EnableConfigurationProperties(SendGridConfigurationProperties.class)
@Configuration
public class SendGridConfiguration {
    private final SendGridConfigurationProperties sendGridConfigurationProperties;

    @Bean
    public SendGrid sendGrid() {
        String apiKey = sendGridConfigurationProperties.apiKey();
        return new SendGrid(apiKey);
    }

    @Bean
    public Email fromEmail() {
        String fromEmail = sendGridConfigurationProperties.fromEmail();
        String fromName = sendGridConfigurationProperties.fromName();
        return new Email(fromEmail, fromName);
    }
}