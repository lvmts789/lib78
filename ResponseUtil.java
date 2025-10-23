import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {
    public static void write(HttpServletResponse resp, String message) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write(message);
    }
}