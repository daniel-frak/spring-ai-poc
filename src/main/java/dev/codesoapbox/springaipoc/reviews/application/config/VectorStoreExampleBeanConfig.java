package dev.codesoapbox.springaipoc.reviews.application.config;

import dev.codesoapbox.springaipoc.reviews.application.GameReviewAiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreExampleBeanConfig {

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel)
                .build();
    }

    @Bean
    GameReviewAiService gameReviewAiService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        return new GameReviewAiService(vectorStore, chatClientBuilder);
    }
}
