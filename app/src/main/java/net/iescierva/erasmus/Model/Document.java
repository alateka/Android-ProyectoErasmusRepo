package net.iescierva.erasmus.Model;

public class Document {

    private int id;
    private String documentName;

    public Document(int id, String documentName) {
        this.id = id;
        this.documentName = documentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
