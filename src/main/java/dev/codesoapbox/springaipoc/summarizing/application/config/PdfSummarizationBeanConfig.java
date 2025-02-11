package dev.codesoapbox.springaipoc.summarizing.application.config;

import dev.codesoapbox.springaipoc.summarizing.application.PdfSummarizer;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PdfSummarizationBeanConfig {

    @Bean
    PdfSummarizer pdfSummarizer(ChatModel chatModel) {
        return new PdfSummarizer(chatModel);
    }
}
