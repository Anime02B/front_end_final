package servlets.get;

import cl.DB;
import cl.TypeMoney;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/api/get_source")
public class GetSourceListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set CORS headers
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Set response content type
        response.setContentType("application/json");

        // Generate some data
        ArrayList<TypeMoney> source = DB.GetSourceList();

        // Write the data as JSON to the response
        Gson gson = new Gson();
        String json = gson.toJson(source);
        response.getWriter().write(json);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
