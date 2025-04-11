import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class HelloWorldServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        System.out.println("Server started on port 8080");
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "Hello!";
            try {
                // Connect to MySQL using environment variables
                String url = "jdbc:mysql://" + System.getenv("DB_HOST") + ":3306/" + System.getenv("DB_NAME") + "?useSSL=false&allowPublicKeyRetrieval=true";
                Connection conn = DriverManager.getConnection(url, System.getenv("DB_USER"), System.getenv("DB_PASS"));

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT greeting FROM messages LIMIT 1;");
                if (rs.next()) {
                    response = rs.getString("greeting");
                }
                conn.close();
            } catch (Exception e) {
                response = "Error: " + e.getMessage();
            }

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

