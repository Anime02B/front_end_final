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
import java.sql.Date;
import java.time.LocalDate;

import java.io.PrintWriter;

import com.google.gson.JsonObject;








@WebServlet("/api/add_income")
public class AddIncomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // Set CORS headers
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "86400");

        // Set CORS headers
        response.setContentType("application/json");

        // Get query parameters


        TypeMoney from_type = DB.GetSource(Integer.parseInt(request.getParameter("from_id")));

        TypeMoney to_type = DB.GetCash(Integer.parseInt(request.getParameter("to_id")));

        Money money = new Money(
                Date.valueOf(LocalDate.now()),
                Integer.parseInt(request.getParameter("amount")),
                0,
                request.getParameter("name"),
                from_type,
                to_type
        );



        DB.AddIncome(money);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
