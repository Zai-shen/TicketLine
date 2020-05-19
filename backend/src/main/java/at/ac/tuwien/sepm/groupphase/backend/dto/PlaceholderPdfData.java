package at.ac.tuwien.sepm.groupphase.backend.dto;

public class PlaceholderPdfData {
    private String text;

    public PlaceholderPdfData(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
