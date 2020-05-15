package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dto.PlaceholderPdfData;
import at.ac.tuwien.sepm.groupphase.backend.dto.ByteArrayFile;

public interface PdfService {
    ByteArrayFile createPlaceholderPdf();
    ByteArrayFile createPdfFromTemplate(PlaceholderPdfData data, String filename, String templateName);
}
