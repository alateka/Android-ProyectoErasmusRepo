// Author ==> Alberto Pérez Fructuoso
// File   ==> Document.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus.Model;

public class Document {

    private String id;
    private String documentName;

    /**
     * Clase diseñada para representar los documentos en la APP.
     * @param id : ID del documento en la BD.
     * @param documentName : Nombre del documento en la BD.
     */
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
}
