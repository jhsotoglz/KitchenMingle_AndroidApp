package RoundTrip.SupportClasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationRequest {
    private String userType;
    private String email;
    private String password;
    private String username;

//    public RegistrationRequest(String userType, String email, String password, String username) {
//        this.username = username;
//        this.userType = userType;
//        this.email = email;
//        this.password = password;
//    }

    @JsonCreator
    public RegistrationRequest(@JsonProperty("userType") String userType,
                               @JsonProperty("email") String email,
                               @JsonProperty("password") String password,
                               @JsonProperty("username") String username) {
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

