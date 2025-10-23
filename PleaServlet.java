import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/lib", "/lib/health", "/lib/info", "/lib/echo"})
public class PleaServlet extends HttpServlet {

    // ====== kleine Hilfsmethode zum Schreiben ======
    private void write(HttpServletResponse resp, String text) throws IOException {
        resp.getWriter().write(text);
    }

    // ====== GET ======
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Hauptmethode, ruft die andere auf
        String path = req.getServletPath();
        doGet(req, resp, path);
    }

    // überladene doGet mit Pfad
    private void doGet(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        // einfache if-Abfragen statt Switch
        if ("/lib".equals(path)) {
            String name = req.getParameter("name");
            doGet(resp, name); // ruft wieder andere Methode auf
        } else if ("/lib/health".equals(path)) {
            doGetHealth(resp);
        } else if ("/lib/info".equals(path)) {
            doGetInfo(resp);
        } else {
            write(resp, "nicht gefunden");
        }
    }

    // überladene doGet mit (resp, name) → ursprüngliche "Name"-Funktion
    private void doGet(HttpServletResponse resp, String name) throws IOException {
        resp.setContentType("text/html");
        write(resp,
            "<html><body>" +
            "<h1>GET Anfrage erhalten</h1>" +
            "<form method='post' action='lib'>" +
            "Name: <input type='text' name='name'/>" +
            "<input type='submit' value='Senden'/>" +
            "</form>" +
            "<p>Hallo, " + name + "!</p>" +
            "<a href='lib'>Zurück</a>" +
            "<hr/>" +
            "<p>Probiere auch: <code>/lib/health</code>, <code>/lib/info</code>, <code>POST /lib/echo</code></p>" +
            "</body></html>"
        );
    }

    // extra GET #1 → Gesundheitscheck
    private void doGetHealth(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        write(resp, "{\"status\":\"UP\"}");
    }

    // extra GET #2 → kleine Infoausgabe
    private void doGetInfo(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        write(resp, "app=Library Demo\n");
        write(resp, "version=1.0.0\n");
        write(resp, "servlet=" + getServletName() + "\n");
        write(resp, "java=" + System.getProperty("java.version") + "\n");
    }

    // ====== POST ======
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Hauptmethode, ruft wieder überladene Variante auf
        String path = req.getServletPath();
        doPost(req, resp, path);
    }

    // überladene doPost mit Pfad
    private void doPost(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        if ("/lib".equals(path)) {
            String name = req.getParameter("name");
            doPost(resp, name); // wieder einfache Weiterleitung
        } else if ("/lib/echo".equals(path)) {
            String name = req.getParameter("name");
            doPostEcho(resp, name);
        } else {
            write(resp, "nicht gefunden");
        }
    }

    // überladene doPost mit (resp, name) → ursprünglicher POST-Flow
    @Override
    protected void doPost(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        resp.setContentType("text/html");
        String name = req.getParameter("name")
        resp.getWriter().write(
            "<html><body>" +
            "<h1>POST Anfrage erhalten</h1>" +
            "<form method='post' action='lib'>" +
            "Name: <input type='text' name='name'/>" +
            "<input type='submit' value='Senden'/>" +
            "</form>" +
            "<p>Hallo, " + name + "!</p>" +
            "<a href='lib'>Zurück</a>" +
            "</body></html>"
        );
    }

    // extra POST → gibt den Namen einfach als JSON zurück
    private void doPostEcho(HttpServletResponse resp, String name) throws IOException {
        resp.setContentType("application/json");
        write(resp, "{\"name\":\"" + name + "\"}");
    }
}
