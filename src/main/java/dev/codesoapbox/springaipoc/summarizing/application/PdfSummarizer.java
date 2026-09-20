package dev.codesoapbox.springaipoc.summarizing.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PdfSummarizer {

    private static final String SYSTEM_PROMPT_TEXT = """
            You are a system for describing PDF documents.
            You receive the contents of a document as input and answer with a single-sentence description of it.
            """;

    private final ChatClient chatClient;

    public PdfSummarizer(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String summarize(MultipartFile pdfFile) {
        try (InputStream inputStream = pdfFile.getInputStream()) {
            String documentText = getDocumentTextContent(inputStream);
            return askAiToSummarize(documentText);
        } catch (IOException e) {
            log.error("Error processing file", e);
            return "Error processing file";
        }
    }

    private String askAiToSummarize(String documentText) {
        return chatClient.prompt()
                .messages(
                        // System message to tell LLM how to behave
                        SystemMessage.builder()
                                .text(SYSTEM_PROMPT_TEXT)
                                .build(),

                        // Few-shot prompting to show LLM what responses should look like

                        // Example 1
                        UserMessage.builder()
                                .text("Some PDF content")
                                .build(),

                        AssistantMessage.builder()
                                .content("A water bill for 200 USD addressed to John Doe.")
                                .build(),

                        // Example 2
                        UserMessage.builder()
                                .text("Some PDF content")
                                .build(),

                        // The actual content of the document
                        AssistantMessage.builder()
                                .content(
                                        "A software development book about " +
                                                "Test-Driven Development written by Peter Peterson."
                                )
                                .build(),

                        UserMessage.builder()
                                // Actual document
                                .text(documentText)
                                .build()
                )
                .call()
                .content();
    }

    private String getDocumentTextContent(InputStream inputStream) {
        var resource = new InputStreamResource(inputStream);
        var reader = new PagePdfDocumentReader(resource);

        List<Document> documents = reader.read();

        return documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));
    }
}
