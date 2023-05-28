package servlets.add;

import cl.DB;
import cl.Money;
import cl.TypeMoney;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

import java.io.IOException;

@WebServlet("/api/add_source")
public class AddSourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set CORS headers
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "86400");

        // Set response content type
        response.setContentType("application/json");

        // Get query parameters
        int amount = Integer.parseInt(request.getParameter("amount"));
        String name = request.getParameter("name");
        String img =  request.getParameter("img");

        TypeMoney source = new TypeMoney(0, name, amount, img);

        DB.AddSource(source);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
