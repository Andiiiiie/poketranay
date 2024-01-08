package servlets.relation_look_matiere;

import com.example.poketranay.DaoConfig;
import database.core.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Matiere;

import java.io.IOException;

@WebServlet(name = "relation_look_matiere/list",urlPatterns = "relation_look_matiere/list")
public class listservlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DBConnection dbConnection = DaoConfig.DATABASE.createConnection();
            request.setAttribute("matieres",Matiere.get_all(dbConnection));
            request.getRequestDispatcher("/matiere/list.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
