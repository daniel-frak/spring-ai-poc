package dev.codesoapbox.springaipoc.shopping.application;

import org.springframework.ai.chat.client.ChatClient;

public class AiShopService {

    private final ChatClient chatClient;

    public AiShopService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("You are an AI assistant answering questions about different shops")
                .build();
    }

    public String answer(String question) {
        return chatClient.prompt()
                .user(question)
                .functions("getShopDetails")
                .call()
                .content();
    }
}
