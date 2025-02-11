package dev.codesoapbox.springaipoc.application.config;

import dev.codesoapbox.springaipoc.application.PdfSummarizer;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    PdfSummarizer pdfSummarizer(ChatModel chatModel) {
        return new PdfSummarizer(chatModel);
    }
}
