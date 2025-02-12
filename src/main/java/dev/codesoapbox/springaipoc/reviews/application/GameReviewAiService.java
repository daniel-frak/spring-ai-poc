package dev.codesoapbox.springaipoc.reviews.application;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

public class GameReviewAiService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public GameReviewAiService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder
                .defaultSystem("You are an AI assistant answering questions about games based on their reviews." +
                               " The context is not provided by the user so you should not act like the user provided" +
                               " that information. The context is game reviews written by professional game reviewers." +
                               " Do not reference the context information in your answer, unless it is relevant to" +
                               " the user's prompt.")
                .build();

        List<Document> gameReviews = new TokenTextSplitter().apply(List.of(
                new Document("""
                        Game A is a roguelike platformer set in medieval England.
                        It is a very good game with a playtime of 8 hours.
                        """),
                new Document("""
                        Nothing could prepare me for how bad Game B would be.
                        It just drags on, and on, and on... For a sci-fi RPG, I expected more!
                        
                        Score: 2/10
                        """),
                new Document("""
                        Game C
                        
                        A true 10/10 experience which will be loved by many, this platformer will surely capture
                        the hearts of many.
                        """)
        ));
        vectorStore.add(gameReviews);
    }

    public String answer(String question) {
        QuestionAnswerAdvisor questionAnswerAdvisor = new QuestionAnswerAdvisor(vectorStore);

        return chatClient.prompt()
                .user(question)
                .advisors(questionAnswerAdvisor)
                .call()
                .content();
    }
}
