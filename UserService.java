public class UserService {
    public static void createUser(String userId, String userName) {
        PostgresHelper.saveUser(userId, userName);
    }
    public static String fetchUser(String userId) {
        return PostgresHelper.getUser(userId);
    }
}