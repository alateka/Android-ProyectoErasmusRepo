package net.iescierva.erasmus.Model;

import org.json.JSONArray;

public class User {

    private String apiToken;
    private String name;
    private String lastName;
    private String email;
    private String DNI;
    private JSONArray documentList;
    private int cycleID;
    private String cycleName;
    private String birthDate;
    private String nationality;
    private String locality;
    private String phone;
    private String address;
    private String zip;

    public User(String apiToken, String name, String lastName, String email, String dni, JSONArray documentList, int cycleID, String cycleName, String birthDate, String nationality, String locality, String phone, String address, String zip) {
        this.apiToken = apiToken;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.DNI = dni;
        this.documentList = documentList;
        this.cycleID = cycleID;
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

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
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

    public int getCycleID() {
        return cycleID;
    }

    public void setCycleID(int cycleID) {
        this.cycleID = cycleID;
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
        birthDate = birthDate;
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
