import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/read")
public class ReadingWelcomeServlet extends HttpServlet {

    // --- Deutsch: doGet liest Parameter und startet die einfache Kette (part1 -> part2) ---
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Deutsch: Werte direkt aus der Anfrage holen (ohne Prüfungen)
        String reader = req.getParameter("reader");
        String book   = req.getParameter("book");
        String pages  = req.getParameter("pages");

        resp.setContentType("text/html");

        // Deutsch: Erst vorbereiten (part1), dann final schreiben (part2)
        part1(resp, reader, book, pages);
    }

    // --- Deutsch: doPost macht genau dasselbe (part1 -> part2) ---
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String reader = req.getParameter("reader");
        String book   = req.getParameter("book");
        String pages  = req.getParameter("pages");

        resp.setContentType("text/html");
        part1(resp, reader, book, pages);
    }

    // --- Deutsch: part1 baut einfache Text-Blöcke zusammen, schreibt aber NICHT ---
    private void part1(HttpServletResponse resp, String reader, String book, String pages) throws IOException {
        // Deutsch: Ein paar Teile vorbereiten (ganz simpel)
        String title   = "Willkommen zur Lese-Tracker-Seite";
        String intro   = "Diese Seite hilft dir, deine Lese-Fortschritte kurz festzuhalten.";
        String form    =
            "<form method='post' action='read'>" +
            "<p>Leser: <input type='text' name='reader'/></p>" +
            "<p>Buch: <input type='text' name='book'/></p>" +
            "<p>Seiten: <input type='number' name='pages'/></p>" +
            "<p><button type='submit'>Speichern</button></p>" +
            "</form>";

        String summary =
            "<div style='border:1px solid #ddd;padding:.5rem 1rem;border-radius:6px;display:inline-block'>" +
            "<h3>Zusammenfassung</h3>" +
            "<p><strong>Leser:</strong> " + reader + "</p>" +
            "<p><strong>Buch:</strong> " + book + "</p>" +
            "<p><strong>Seiten gelesen:</strong> " + pages + "</p>" +
            "</div>";

        // Deutsch: Jetzt an part2 übergeben, dort wird endgültig geschrieben
        part2(resp, title, intro, form, summary);
    }

    // --- Deutsch: part2 setzt eine komplette HTML-Seite zusammen und SCHREIBT ---
    private void part2(HttpServletResponse resp, String title, String intro, String form, String summary) throws IOException {
        resp.getWriter().write(
            "<!doctype html>" +
            "<html><head><meta charset='utf-8'/>" +
            "<title>" + title + "</title>" +
            "<style>body{font-family:Arial;margin:2rem}</style>" +
            "</head><body>" +
            "<h1>" + title + "</h1>" +
            "<p>" + intro + "</p>" +
            form +
            "<hr/>" +
            summary +
            "<p style='margin-top:1rem'><a href='read'>Zurück (GET)</a></p>" +
            "</body></html>"
        );
    }
}
