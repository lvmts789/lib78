import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/lib")
public class PleaServlet extends HttpServlet {

    // --- Deutsch: Diese Methode schreibt einfach die komplette Seite raus ---
    private void write(HttpServletResponse resp, String name) throws IOException {
        resp.getWriter().write(
            "<!doctype html>" +
            "<html><head><meta charset='utf-8'/>" +
            "<title>Lib</title>" +
            "<style>body{font-family:Arial;margin:2rem} .box{border:1px solid #ddd;padding:.5rem 1rem;border-radius:6px;display:inline-block}</style>" +
            "</head><body>" +
            "<h1>Einfaches Formular</h1>" +
            // Deutsch: ganz normales Formular, postet wieder auf /lib
            "<form method='post' action='lib'>" +
            "Name: <input type='text' name='name'/> " +
            "<button type='submit'>Senden</button>" +
            "</form>" +
            "<hr/>" +
            // Deutsch: wir benutzen den Namen direkt (ohne Prüfungen)
            "<p class='box'>Hallo, " + name + "!</p>" +
            "<p>Oben kannst du den Namen ändern und mit POST schicken.</p>" +
            "<p><a href='lib'>Zurück (GET)</a></p>" +
            "</body></html>"
        );
    }

    // --- Deutsch: Nur eine doGet. Liest name aus req und gibt ihn an write weiter. ---
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Deutsch: name direkt aus der Anfrage holen (ohne Checks)
        String name = req.getParameter("name");

        // Deutsch: HTML ausgeben
        resp.setContentType("text/html");
        write(resp, name);  // Deutsch: hier wird alles geschrieben
    }

    // --- Deutsch: Nur eine doPost. Gleiches Prinzip: name holen -> write aufrufen. ---
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Deutsch: wieder direkt lesen, ohne Validierung
        String name = req.getParameter("name");

        // Deutsch: HTML ausgeben
        resp.setContentType("text/html");
        write(resp, name);  // Deutsch: gleiche Seite, zeigt nur an, was kam
    }
}
