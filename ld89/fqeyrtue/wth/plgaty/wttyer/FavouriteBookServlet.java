import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/favbook")
public class FavouriteBookServlet extends HttpServlet {

    // --- Deutsch: doGet liest Lieblingsbuch und startet die Kette (part1 -> part2) ---
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String favoriteBook = req.getParameter("favoriteBook");
        resp.setContentType("text/html");
        part1(resp, favoriteBook);
    }

    // --- Deutsch: doPost macht das Gleiche (part1 -> part2) ---
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String favoriteBook = req.getParameter("favoriteBook");
        resp.setContentType("text/html");
        part1(resp, favoriteBook);
    }

    // --- Deutsch: part1 könnte vorbereiten, reicht aber hier nur an part2 weiter ---
    private void part1(HttpServletResponse resp, String favoriteBook) throws IOException {
        part2(resp, favoriteBook);
    }

    // --- Deutsch: part2 schreibt die HTML-Seite und holt Daten aus MySQL ---
    private void part2(HttpServletResponse resp, String favoriteBook) throws IOException {
        String totalReaders = "";
        String topBook = "";

        String url  = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String pass = "password";

        try {
            // Deutsch: JDBC-Treiber laden
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement st = conn.createStatement();

            // Deutsch: Wie viele Leser gibt es insgesamt?
            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM readers");
            if (rs1.next()) {
                totalReaders = rs1.getString(1);
            }

            // Deutsch: Welches Buch ist am beliebtesten?
            ResultSet rs2 = st.executeQuery("SELECT title FROM books ORDER BY popularity DESC LIMIT 1");
            if (rs2.next()) {
                topBook = rs2.getString(1);
            }

            rs1.close();
            rs2.close();
            st.close();
            conn.close();

        } catch (Exception e) {
            totalReaders = "Fehler";
            topBook = "Fehler: " + e.getMessage();
        }

        // Deutsch: Jetzt alles als einfache Seite ausgeben
        resp.getWriter().write(
            "<!doctype html>" +
            "<html><head><meta charset='utf-8'/>" +
            "<title>Lieblingsbuch-Tracker</title>" +
            "<style>body{font-family:Arial;margin:2rem} .box{border:1px solid #ccc;padding:.5rem 1rem;border-radius:6px;margin:.25rem 0;display:inline-block}</style>" +
            "</head><body>" +
            "<h1>Willkommen im Lieblingsbuch-Tracker</h1>" +
            "<p>Hier kannst du dein Lieblingsbuch eingeben und ein paar Bibliotheksinfos sehen.</p>" +

            "<form method='post' action='favbook'>" +
            "Lieblingsbuch: <input type='text' name='favoriteBook'/> " +
            "<button type='submit'>Senden</button>" +
            "</form>" +

            "<hr/>" +
            "<div class='box'><strong>Dein Lieblingsbuch:</strong> " + favoriteBook + "</div>" +

            "<div class='box'>" +
            "<h3>Bibliotheks-Statistik (aus MySQL)</h3>" +
            "<p><strong>Gesamtzahl Leser:</strong> " + totalReaders + "</p>" +
            "<p><strong>Beliebtestes Buch:</strong> " + topBook + "</p>" +
            "</div>" +

            "<p style='margin-top:1rem'><a href='favbook'>Zurück (GET)</a></p>" +
            "</body></html>"
        );
    }
}
