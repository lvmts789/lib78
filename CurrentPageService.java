public class CurrentPageService {
    public static int getCurrentPage(String userId) {
        return BookPageProgressService.getProgress(userId);
    }
}