package dev.codesoapbox.springaipoc.shopping.adapters.driving.http;

import dev.codesoapbox.springaipoc.shopping.application.AiShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shops")
@RequiredArgsConstructor
public class AskAboutShopsController {

    private final AiShopService aiShopService;

    @GetMapping(value = "answer")
    public String answer(@RequestParam String question) {
        return aiShopService.answer(question);
    }
}
