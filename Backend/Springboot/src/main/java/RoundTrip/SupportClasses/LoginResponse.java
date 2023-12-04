package RoundTrip.SupportClasses;

public class LoginResponse {
    private String userType;
    private Long userId;
    public LoginResponse(String userType, Long userId) {
        this.userType = userType;
        this.userId = userId;
    }

    // Getters and Setters
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

