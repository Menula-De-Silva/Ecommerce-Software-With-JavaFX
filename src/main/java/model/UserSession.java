package model;

public class UserSession {
    private static String username;
    private static String password;
    private static String profileImagePath; // New field for image path

    // Getter and Setter for username
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserSession.username = username;
    }

    // Getter and Setter for password
    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserSession.password = password;
    }

    // Getter and Setter for profile image path
    public static String getProfileImagePath() {
        return profileImagePath;
    }

    public static void setProfileImagePath(String profileImagePath) {
        UserSession.profileImagePath = profileImagePath;
    }
}