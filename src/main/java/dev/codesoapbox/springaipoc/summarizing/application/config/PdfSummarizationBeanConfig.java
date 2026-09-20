package dev.codesoapbox.springaipoc.summarizing.application.config;

import dev.codesoapbox.springaipoc.summarizing.application.PdfSummarizer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PdfSummarizationBeanConfig {

    @Bean
    PdfSummarizer pdfSummarizer(ChatClient.Builder chatClientBuilder) {
        return new PdfSummarizer(chatClientBuilder);
    }
}
