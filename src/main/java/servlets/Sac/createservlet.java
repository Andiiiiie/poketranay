package servlets.relation_look_matiere;

import com.example.poketranay.DaoConfig;
import database.core.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Look;
import model.Matiere;
import model.Relation_look_matiere;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Vector;

@WebServlet(urlPatterns = "relation_look_matiere/create")
public class createservlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection dbConnection = null;
        try {
            dbConnection= DaoConfig.DATABASE.createConnection();
            Vector<Matiere> matieres=Matiere.get_all(dbConnection);
            Vector<Look> looks=Look.get_all(dbConnection);

            dbConnection.commit();
            dbConnection.close();

            request.setAttribute("matieres",matieres);
            request.setAttribute("looks",looks);
            request.getRequestDispatcher("/relation_look_matiere/insert.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection dbConnection = null;
        try {
            dbConnection= DaoConfig.DATABASE.createConnection();
            String id_matiere=request.getParameter("matiere");
            String id_look=request.getParameter("look");
            Relation_look_matiere relationLookMatiere=new Relation_look_matiere();
            relationLookMatiere.setId_look(id_look);
            relationLookMatiere.setId_matiere(id_matiere);

            relationLookMatiere.enregistrer(dbConnection);
            dbConnection.commit();
            dbConnection.close();
            response.sendRedirect(request.getContextPath()+"/relation_look_matiere/create");
        } catch (Exception e) {
            try {
                request.setAttribute("matieres",Matiere.get_all(dbConnection));
                request.setAttribute("looks",Look.get_all(dbConnection));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/relation_look_matiere/insert.jsp").forward(request, response);
        }

    }
}
