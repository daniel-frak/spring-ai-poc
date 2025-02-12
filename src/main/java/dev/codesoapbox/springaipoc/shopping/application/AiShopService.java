package dev.codesoapbox.springaipoc.shopping.application;

import org.springframework.ai.chat.client.ChatClient;

public class AiShopService {

    private final ChatClient chatClient;

    public AiShopService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("You are an AI assistant answering questions about different shops." +
                               "When asked what you can help with, make sure to only reply with functionalities" +
                               " related to the function calls you can make. Never say you can be asked for" +
                               " information which isn't explicitly available through the function calls" +
                               " that are available to you." +
                               " Provide detailed information about what queries can be made which you can" +
                               " actually reliably answer.")
                .build();
    }

    public String answer(String question) {
        return chatClient.prompt()
                .user(question)
                .functions("getShopNames", "getShopDetails")
                .call()
                .content();
    }
}
