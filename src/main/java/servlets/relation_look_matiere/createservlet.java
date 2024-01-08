package servlets.matiere;

import com.example.poketranay.DaoConfig;
import database.core.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Matiere;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "matiere/create")
public class createservlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/matiere/insert.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection dbConnection = null;
        try {
            dbConnection= DaoConfig.DATABASE.createConnection();
            String designation=request.getParameter("designation");
            Matiere.creer(designation,dbConnection);

            dbConnection.commit();
            dbConnection.close();
            response.sendRedirect(request.getContextPath()+"/matiere/create");
        } catch (Exception e) {
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/matiere/insert.jsp").forward(request, response);
        }

    }
}
