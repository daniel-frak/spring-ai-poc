package dev.codesoapbox.springaipoc.shopping.application;

import java.util.List;
import java.util.Optional;

public class ShopRepository {

    private final List<Shop> shops = List.of(
            new Shop(
                    "Consoles R Us",
                    List.of(
                            "Devtendo Swerve",
                            "PixelPanic Z",
                            "HyperHype Elite"
                    )
            ),
            new Shop(
                    "The Frootbasket",
                    List.of(
                            "Apple",
                            "Orange",
                            "Grapes",
                            "Banana"
                    )
            )
    );

    public Optional<Shop> findByName(String name) {
        return shops.stream()
                .filter(shop -> shop.name().equalsIgnoreCase(name))
                .findFirst();
    }
}
