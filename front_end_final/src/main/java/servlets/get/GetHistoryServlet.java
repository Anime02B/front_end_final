package servlets.get;

import cl.DB;
import cl.Money;
import cl.Money_for_front;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/api/get_history")
public class GetHistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Set response content type
        response.setContentType("application/json");

        // Generate some data
        ArrayList<Money_for_front> history = DB.getHistory();


        // Write the data as JSON to the response
        Gson gson = new Gson();
        String json = gson.toJson(history);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
