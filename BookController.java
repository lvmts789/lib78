import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/book")
public class BookController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        int page = CurrentPageService.getCurrentPage(userId);
        ResponseUtil.write(resp, "Current page: " + page);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        int page = Integer.parseInt(req.getParameter("page"));
        BookPageProgressService.updateProgress(userId, page);
        ResponseUtil.write(resp, "Progress updated!");
    }
}