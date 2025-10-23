public class BookPageProgressService {
    public static void updateProgress(String userId, int page) {
        DynamoDbHelper.saveBookProgress(userId, page);
    }
    public static int getProgress(String userId) {
        return DynamoDbHelper.getBookProgress(userId);
    }
}