import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/lib")
public class LibServlet extends HttpServlet {

    // --- kleine Schreibhilfe, damit der Code kurz bleibt ---
    private void write(HttpServletResponse resp, String s) throws IOException {
        resp.getWriter().write(s);
    }

    // --- GET: holt "name" und ruft mehrere Mini-Bausteine auf ---
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Deutsch: Wir lesen den Namen direkt aus der Anfrage (ohne Prüfungen)
        String name = req.getParameter("name");

        resp.setContentType("text/html");

        // Deutsch: Seite zusammenklicken aus kleinen Methoden
        write(resp, header("GET Anfrage erhalten"));
        write(resp, form());                 // Formular oben
        write(resp, greet(name));            // einfache Begrüßung
        write(resp, greetLoud(name));        // gleiche Begrüßung, nur lauter
        write(resp, badge(name));            // „Student“-Badge mit Name
        write(resp, footer());
    }

    // --- POST: holt "name" und nutzt andere Bausteine ---
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Deutsch: Wieder direkt aus der Anfrage
        String name = req.getParameter("name");

        resp.setContentType("text/html");

        // Deutsch: Für POST zeigen wir eine kleine „Bestätigungsseite“
        write(resp, header("POST Anfrage erhalten"));
        write(resp, form());                 // Formular bleibt gleich
        write(resp, thanks(name));           // „Danke“-Text
        write(resp, miniSummary(name));      // Mini-Zusammenfassung
        write(resp, footer());
    }

    // -------------------- Hilfsmethoden (nur HTML-Fragmente) --------------------

    // Deutsch: Überschrift + kleines CSS (simpel)
    private String header(String title) {
        return "<!doctype html><html><head><meta charset='utf-8'/>"
             + "<title>Lib</title>"
             + "<style>body{font-family:Arial;margin:2rem;} .box{padding:.5rem 1rem;border:1px solid #ddd;border-radius:6px;display:inline-block;margin:.25rem 0;}</style>"
             + "</head><body><h1>" + title + "</h1>";
    }

    // Deutsch: Seitenende
    private String footer() {
        return "<p><a href='lib'>Zurück</a></p></body></html>";
    }

    // Deutsch: Einfache Begrüßung
    private String greet(String name) {
        return "<p class='box'>Hallo, " + name + "!</p>";
    }

    // Deutsch: „laute“ Begrüßung (nur demonstrativ)
    private String greetLoud(String name) {
        return "<p class='box'><strong>HALLO, " + ((name == null) ? null : name.toUpperCase()) + "!</strong></p>";
    }

    // Deutsch: Kleines „Badge“ mit dem Namen
    private String badge(String name) {
        return "<p class='box'>🎓 Student: <em>" + name + "</em></p>";
    }

    // Deutsch: Formular (GET/POST egal; wir posten zurück auf /lib)
    private String form() {
        return "<form method='post' action='lib'>"
             + "Name: <input type='text' name='name'/> "
             + "<button type='submit'>Senden</button>"
             + "</form>";
    }

    // Deutsch: Danke-Text nach POST
    private String thanks(String name) {
        return "<p class='box'>Danke für die Eingabe, " + name + ".</p>";
    }

    // Deutsch: Mini-Zusammenfassung
    private String miniSummary(String name) {
        return "<p class='box'>Zusammenfassung: name = <code>" + name + "</code></p>";
    }
}
