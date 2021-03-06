package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import at.ac.tuwien.sepm.groupphase.backend.dto.PlaceholderPdfData;
import at.ac.tuwien.sepm.groupphase.backend.service.PdfService;
import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;

@Service
public class PdfServiceImpl implements PdfService {

    private final Configuration freemarkerConfiguration;
    private final String PLACEHOLDER_TEMPLATE_FILENAME = "placeholderTemplate.ftl";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public PdfServiceImpl(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @Override
    public ByteArrayFile createPlaceholderPdf() {
        PlaceholderPdfData placeholderPdfData = new PlaceholderPdfData("This a Placeholder PDF, it holds place");
        return createPdfFromTemplate(placeholderPdfData, "placeholder-file.pdf", PLACEHOLDER_TEMPLATE_FILENAME);
    }

    @Override
    public ByteArrayFile createPdfFromTemplate(Object data, String filename, String templateName) {
        HashMap dataMap = new HashMap();
        dataMap.put("data", data);
        String html = resolveHtmlTemplate(dataMap, templateName);
        return createPdfFromHtml(html, filename);
    }

    private String resolveHtmlTemplate(Object data, String templateFileName) {
        try {
            Template template = freemarkerConfiguration.getTemplate(templateFileName);
            StringWriter result = new StringWriter();
            template.process(data, result);
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ByteArrayFile createPdfFromHtml(String html, String filename) {
        ITextRenderer renderer = new ITextRenderer();
        try {
            renderer.getFontResolver().addFont("Roboto-Regular.ttf",false);
        } catch (DocumentException e) {
            LOGGER.warn("Could not inject font, using fallback");
        } catch (IOException e) {
            LOGGER.warn("Font file not found, using fallback");
        } ByteArrayOutputStream outputStream =  new ByteArrayOutputStream();
        renderer.setDocumentFromString(html);
        renderer.layout();
        try {
            renderer.createPDF(outputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        return new ByteArrayFile(filename, outputStream.toByteArray());
    }
}
