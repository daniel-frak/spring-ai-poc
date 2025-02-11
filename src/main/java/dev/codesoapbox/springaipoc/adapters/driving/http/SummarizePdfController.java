package dev.codesoapbox.springaipoc.adapters.driving.http;

import dev.codesoapbox.springaipoc.application.PdfSummarizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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
