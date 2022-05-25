package net.iescierva.erasmus.Model;

public class Document {

    private String id;
    private String documentName;

    public Document(String id, String documentName) {
        this.id = id;
        this.documentName = documentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
