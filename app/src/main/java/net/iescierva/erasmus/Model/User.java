// Author ==> Alberto Pérez Fructuoso
// File   ==> User.java
// Date   ==> 2022/05/29

package net.iescierva.erasmus.Model;

import org.json.JSONArray;

public class User {

    private final String apiToken;
    private String name;
    private String lastName;
    private String email;
    private String DNI;
    private JSONArray documentList;
    private String cycleName;
    private String birthDate;
    private String nationality;
    private String locality;
    private String phone;
    private String address;
    private String zip;

    /**
     * Clase diseñada para representar los datos del usuario en la APP.
     * @param apiToken El tocken de acceso a la API.
     * @param name El nombre del alumno.
     * @param lastName El apellido del alumno.
     * @param email El correo electrónico del alumno.
     * @param dni El DNI del alumno.
     * @param documentList El listado de documentos devuelto por la API.
     * @param cycleName El nombre del ciclo del alumno.
     * @param birthDate La fecha de nacimiento del alumno (YYYY-MM-DD).
     * @param nationality La nacionalidad del alumno.
     * @param locality La localidad del alumno.
     * @param phone El numero de teléfono del alumno.
     * @param address La dirección del alumno.
     * @param zip El código postal del alumno.
     */
    public User(String apiToken, String name, String lastName, String email, String dni, JSONArray documentList, String cycleName, String birthDate, String nationality, String locality, String phone, String address, String zip) {
        this.apiToken = apiToken;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.DNI = dni;
        this.documentList = documentList;
        this.cycleName = cycleName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.locality = locality;
        this.phone = phone;
        this.address = address;
        this.zip = zip;
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public JSONArray getDocumentList() {
        return documentList;
    }

    public void setDocumentList(JSONArray documentList) {
        this.documentList = documentList;
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
