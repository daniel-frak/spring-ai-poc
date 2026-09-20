package dev.codesoapbox.springaipoc.shopping.application.config;

import dev.codesoapbox.springaipoc.shopping.application.AiShopService;
import dev.codesoapbox.springaipoc.shopping.application.ShopAiTools;
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
    ShopAiTools shopTools(ShopRepository shopRepository) {
        return new ShopAiTools(shopRepository);
    }

    @Bean
    AiShopService aiShopService(
            ChatClient.Builder chatClientBuilder,
            ShopAiTools shopTools) {

        return new AiShopService(
                chatClientBuilder,
                shopTools
        );
    }
}