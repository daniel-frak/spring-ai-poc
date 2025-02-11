package dev.codesoapbox.springaipoc.shopping.application.config;

import dev.codesoapbox.springaipoc.shopping.application.AiShopService;
import dev.codesoapbox.springaipoc.shopping.application.ShopRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopBeanConfig {

    @Bean
    ShopRepository shopRepository() {
        return new ShopRepository();
    }

    @Bean
    AiShopService aiShopService(ChatClient.Builder chatClientBuilder) {
        return new AiShopService(chatClientBuilder);
    }
}
