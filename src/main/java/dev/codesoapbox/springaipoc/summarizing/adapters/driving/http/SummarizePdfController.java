package dev.codesoapbox.springaipoc.summarizing.adapters.driving.http;

import dev.codesoapbox.springaipoc.summarizing.application.PdfSummarizer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("pdf")
@RequiredArgsConstructor
public class SummarizePdfController {

    private final PdfSummarizer pdfSummarizer;

    @PostMapping(value = "summarize", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String summarize(@RequestPart("file") MultipartFile cvFile) {
        return pdfSummarizer.summarize(cvFile);
    }
}
