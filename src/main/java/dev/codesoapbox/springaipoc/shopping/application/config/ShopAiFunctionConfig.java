package dev.codesoapbox.springaipoc.shopping.application.config;

import dev.codesoapbox.springaipoc.shopping.application.Shop;
import dev.codesoapbox.springaipoc.shopping.application.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
@Configuration
public class ShopAiFunctionConfig {

    private final ShopRepository shopRepository;

    public record ShopName(String name) {
    }

    @Bean
    @Description("Get shop details by name")
    public Function<ShopName, Shop> getShopDetails() {
        return shopName -> shopRepository.findByName(shopName.name())
                .orElseGet(() -> new Shop("Not found", emptyList()));
    }
}
