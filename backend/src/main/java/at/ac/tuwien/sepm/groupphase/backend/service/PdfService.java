package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dto.PlaceholderPdfData;
import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface PdfService {
    /**
     * @return returns a Placeholder Pdf used while the real pdf-template is not created
     */
    ByteArrayFile createPlaceholderPdf();
    /**
     * @param data The Data object used to fill the Freemarker-tempalte
     * @param filename The filename under which the user will download the file
     * @param templateName The name of the Freemarker Template (.ftl) located in the templates folder
     * @return returns a ByteArrayFile with a pdf as its content based on the provided Tempalte and data
     */
    ByteArrayFile createPdfFromTemplate(Object data, String filename, String templateName);
}
