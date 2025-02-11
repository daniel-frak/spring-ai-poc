package dev.codesoapbox.springaipoc.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class PdfSummarizer {

    private static final String SYSTEM_PROMPT_TEXT = """
            You are a system for describing PDF documents.
            You receive the contents of a document as input and answer with a single-sentence description of it.
            """;

    private final ChatModel chatModel;

    public String summarize(MultipartFile cvFile) {
        try (InputStream inputStream = cvFile.getInputStream()) {
            String documentText = getDocumentTextContent(inputStream);
            return askAiToSummarize(documentText);
        } catch (IOException e) {
            log.error("Error processing file", e);
            return "Error processing file";
        }
    }

    private String askAiToSummarize(String documentText) {
        var prompt = new Prompt(List.of(
                // System message to tell LLM how to behave
                new SystemMessage(SYSTEM_PROMPT_TEXT),

                // Few-shot prompting to show LLM what responses should look like
                new UserMessage("Some PDF content"),
                new AssistantMessage("A water bill for 200 USD addressed to John Doe."),
                new UserMessage("Some PDF content"),
                new AssistantMessage(
                        "A software development book about Test-Driven Development written by Peter Peterson."),

                // The actual content of the document
                new UserMessage(documentText)
        ));
        String answer = chatModel.call(prompt)
                .getResult().getOutput().getContent();
        log.info(prompt.toString());
        return answer;
    }

    private String getDocumentTextContent(InputStream inputStream) {
        var resource = new InputStreamResource(inputStream);
        var reader = new PagePdfDocumentReader(resource);
        List<Document> documents = reader.read();

        return documents.stream()
                .filter(Document::isText)
                .map(Document::getText)
                .collect(Collectors.joining("\n"));
    }
}
