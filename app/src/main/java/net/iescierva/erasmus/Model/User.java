package net.iescierva.erasmus.Model;

public class User {

    private String apiToken;
    private String name;
    private String lastName;
    private String email;
    private String DNI;

    public User(String apiToken, String name, String lastName, String email, String dni) {
        this.apiToken = apiToken;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        DNI = dni;
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
}
