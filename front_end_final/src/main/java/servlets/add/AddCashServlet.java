package servlets.add;

import cl.DB;
import cl.Money;
import cl.TypeMoney;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import com.google.gson.JsonObject;
import java.io.PrintWriter;

@WebServlet("/api/add_cash")
public class AddCashServlet extends HttpServlet {
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

        TypeMoney cash = new TypeMoney(0, name, amount, img);

        DB.AddCash(cash);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {





        // Create JSON object for the response
//        JsonObject jsonResponse = new JsonObject();
//        jsonResponse.addProperty("amount", amount);
//        jsonResponse.addProperty("name", category);

        // Send the response
//        PrintWriter out = response.getWriter();
//        out.print(jsonResponse);
//        out.flush();


    }
}
