package dev.codesoapbox.springaipoc.shopping.application;

import java.util.List;

public record Shop(
        String name,
        List<String> products
) {
}
