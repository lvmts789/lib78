public class ValidationUtil {
    public static boolean isValidUserId(String userId) {
        return userId != null && !userId.trim().isEmpty();
    }
}