package dev.codesoapbox.springaipoc.shopping.application;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
@Configuration
public class ShopAiTools {

    private final ShopRepository shopRepository;

    public record GetShopNamesAIResponse(
            List<String> shopNames
    ) {
    }

    public record GetShopDetailsAIRequest(
            String shopName
    ) {
    }

    public record GetShopDetailsAIResponse(
            String name,
            List<String> products
    ) {
    }

    @Tool(
            name = "getShopNames",
            description = "Get the names of all available shops"
    )
    public GetShopNamesAIResponse getShopNames() {
        return new GetShopNamesAIResponse(
                shopRepository.getAllShopNames()
        );
    }

    @Tool(
            name = "getShopDetails",
            description = "Get shop details (such products sold) by name"
    )
    public GetShopDetailsAIResponse getShopDetails(
            GetShopDetailsAIRequest request) {

        return shopRepository.findByName(request.shopName())
                .map(this::toResponse)
                .orElseGet(this::shopNotFoundResponse);
    }

    private GetShopDetailsAIResponse toResponse(Shop shop) {
        return new GetShopDetailsAIResponse(
                shop.name(),
                shop.products()
        );
    }

    private GetShopDetailsAIResponse shopNotFoundResponse() {
        return new GetShopDetailsAIResponse(
                "Not found",
                emptyList()
        );
    }
}