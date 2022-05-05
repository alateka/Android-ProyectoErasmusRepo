package net.iescierva.erasmus.Data;

public class User {

    private String apiToken;
    private String name;

    public User(String apiToken, String name, String email) {
        this.apiToken = apiToken;
        this.name = name;
        this.email = email;
    }

    private String email;

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
}
