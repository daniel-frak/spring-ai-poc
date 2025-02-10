package dev.codesoapbox.springaipoc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("pdf")
@RequiredArgsConstructor
public class SummarizePdfController {

    private final OllamaChatModel chatModel;

    @PostMapping(value = "summarize", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String summarize(@RequestPart("file") MultipartFile cvFile) {
        InputStreamResource resource = new InputStreamResource(cvFile);
        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
        List<Document> documents = reader.read();
        String documentText = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));
        String promptText =
                "Summarize the content of the following document in a concise and accurate manner." +
                " Write a standalone summary that directly conveys the document's content," +
                " without introductory or concluding remarks like 'here are the key points'" +
                " or 'this document describes'." +
                " Ensure all critical information, including specific details or explicitly mentioned facts," +
                " is represented accurately in the summary." +
                " Focus solely on the content and avoid adding any meta-commentary or interpretations" +
                " beyond the document itself." +
                " The summary should be clear, well-structured, and written in complete sentences." +
                " Limit the summary to 200 words." +
                " The document is below: \n"
                + documentText;
        return chatModel.call(new Prompt(promptText))
                .getResult().getOutput().getContent();
    }
}
