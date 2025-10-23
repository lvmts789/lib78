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

    // --- Deutsch: GET ruft direkt part1 auf (Willkommens-/Formular-Seite) ---
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Deutsch: Lieblingsbuch direkt holen (ohne Prüfungen)
        String favoriteBook = req.getParameter("favoriteBook");
        resp.setContentType("text/html");
        part1(resp, favoriteBook);
    }

    // --- Deutsch: POST ruft direkt part2 auf (Ergebnis + MySQL-Infos) ---
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String favoriteBook = req.getParameter("favoriteBook");
        resp.setContentType("text/html");
        part2(resp, favoriteBook);
    }

    // --- Deutsch: part1 schreibt die einfache Willkommens-/Eingabe-Seite ---
    private void part1(HttpServletResponse resp, String favoriteBook) throws IOException {
        resp.getWriter().write(
            "<!doctype html>" +
            "<html><head><meta charset='utf-8'/>" +
            "<title>Lieblingsbuch-Tracker</title>" +
            "<style>body{font-family:Arial;margin:2rem} .box{border:1px solid #ccc;padding:.5rem 1rem;border-radius:6px;margin:.25rem 0;display:inline-block}</style>" +
            "</head><body>" +
            "<h1>Willkommen im Lieblingsbuch-Tracker</h1>" +
            "<p>Gib dein Lieblingsbuch ein und sende das Formular.</p>" +

            // Deutsch: Einfaches Formular (postet auf /favbook)
            "<form method='post' action='favbook'>" +
            "Lieblingsbuch: <input type='text' name='favoriteBook'/> " +
            "<button type='submit'>Senden</button>" +
            "</form>" +

            "<hr/>" +
            "<div class='box'><strong>Aktuell eingetragen (GET):</strong> " + favoriteBook + "</div>" +

            "<p style='margin-top:1rem'><a href='favbook'>Zurück (GET)</a></p>" +
            "</body></html>"
        );
    }

    // --- Deutsch: part2 schreibt die Ergebnis-Seite und liest Zusatzinfos aus MySQL (JDBC) ---
    private void part2(HttpServletResponse resp, String favoriteBook) throws IOException {
        // Deutsch: Mini-Infos aus der DB (studentisch, ohne Prüfungen)
        String totalReaders = "";
        String topBook = "";

        // Deutsch: Demo-Zugangsdaten (bei euch anpassen)
        String url  = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String pass = "password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement st = conn.createStatement();

            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM readers");
            if (rs1.next()) totalReaders = rs1.getString(1);

            ResultSet rs2 = st.executeQuery("SELECT title FROM books ORDER BY popularity DESC LIMIT 1");
            if (rs2.next()) topBook = rs2.getString(1);

            rs1.close();
            rs2.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            totalReaders = "Fehler";
            topBook = "Fehler: " + e.getMessage();
        }

        resp.getWriter().write(
            "<!doctype html>" +
            "<html><head><meta charset='utf-8'/>" +
            "<title>Lieblingsbuch-Tracker</title>" +
            "<style>body{font-family:Arial;margin:2rem} .box{border:1px solid #ccc;padding:.5rem 1rem;border-radius:6px;margin:.25rem 0;display:inline-block}</style>" +
            "</head><body>" +
            "<h1>Dein Lieblingsbuch (POST)</h1>" +
            "<div class='box'><strong>Lieblingsbuch:</strong> " + favoriteBook + "</div>" +

            "<hr/>" +
            "<h2>Bibliotheks-Infos (aus MySQL)</h2>" +
            "<div class='box'><strong>Gesamtzahl Leser:</strong> " + totalReaders + "</div>" +
            "<div class='box'><strong>Beliebtestes Buch:</strong> " + topBook + "</div>" +

            "<p style='margin-top:1rem'><a href='favbook'>Zurück (GET)</a></p>" +
            "</body></html>"
        );
    }
}
