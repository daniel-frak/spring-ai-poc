package dev.codesoapbox.springaipoc.shopping.application.config;

import dev.codesoapbox.springaipoc.shopping.application.Shop;
import dev.codesoapbox.springaipoc.shopping.application.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
@Configuration
public class ShopAiFunctionConfig {

    private final ShopRepository shopRepository;

    public record GetShopNamesAIResponse(
            List<String> shopNames
    ) {
    }

    public record GetShopDetailsAIRequest(String shopName) {
    }

    public record GetShopDetailsAIResponse(
            String name,
            List<String> products
    ) {
    }

    @Bean
    @Description("Get shop names")
    public Function<Void, GetShopNamesAIResponse> getShopNames() {
        return shopName -> new GetShopNamesAIResponse(shopRepository.getAllShopNames());
    }

    @Bean
    @Description("Get shop details by name")
    public Function<GetShopDetailsAIRequest, GetShopDetailsAIResponse> getShopDetails() {
        return request -> shopRepository.findByName(request.shopName())
                .map(this::toResponse)
                .orElseGet(this::shopNotFoundResponse);
    }

    private GetShopDetailsAIResponse toResponse(Shop shop) {
        return new GetShopDetailsAIResponse(shop.name(), shop.products());
    }

    private GetShopDetailsAIResponse shopNotFoundResponse() {
        return new GetShopDetailsAIResponse("Not found", emptyList());
    }
}
