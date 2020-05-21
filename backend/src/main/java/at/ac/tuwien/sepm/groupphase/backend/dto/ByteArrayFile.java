package at.ac.tuwien.sepm.groupphase.backend.dto;

public class ByteArrayFile {
    private String name;
    private byte[] content;

    public ByteArrayFile(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
