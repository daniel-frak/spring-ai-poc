package dev.codesoapbox.springaipoc.reviews.adapters.driving.http;

import dev.codesoapbox.springaipoc.reviews.application.GameReviewAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games")
@RequiredArgsConstructor
public class AskAboutGamesController {

    private final GameReviewAiService gameReviewAiService;

    @GetMapping(value = "answer")
    public String answer(@RequestParam String question) {
        return gameReviewAiService.answer(question);
    }
}
