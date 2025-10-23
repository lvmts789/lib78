import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/simple")
public class LibServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().write(
            "<html><body>" +
            "<h1>GET Request Received</h1>" +
            "<form method='post'>" +
            "Name: <input type='text' name='name'/>" +
            "<input type='submit' value='Submit'/>" +
            "</form>" +
            "</body></html>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        response.setContentType("text/html");
        response.getWriter().write(
            "<html><body>" +
            "<h1>POST Request Received</h1>" +
            "<p>Hello, " + (name != null ? name : "guest") + "!</p>" +
            "<a href='simple'>Back</a>" +
            "</body></html>"
        );
    }
}
