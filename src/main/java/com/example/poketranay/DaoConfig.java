import database.core.DBConnection;
import database.core.Database;
import database.exception.SQL.AttributeMissingException;
import database.exception.SQL.AttributeTypeNotExistingException;
import database.exception.object.NotIdentifiedInDatabaseException;
import database.provider.PostgreSQL;
import model.Look;
import model.Matiere;
import model.Relation_look_matiere;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class DaoConfig {
    public static final Database DATABASE = new PostgreSQL("localhost", "5432", "haha", "postgres", "postgres");

    public static void main(String[] args) throws SQLException, AttributeTypeNotExistingException, AttributeMissingException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NotIdentifiedInDatabaseException {
        DBConnection dbConnection = DATABASE.createConnection();

        createTables(dbConnection);

        dbConnection.commit();
        dbConnection.close();
    }

    public static void createTables(DBConnection dbConnection) throws SQLException, AttributeTypeNotExistingException, AttributeMissingException {
        dbConnection.getDatabase().createSequenceFunction(dbConnection.getConnection());
        Matiere matiere=new Matiere();
        matiere.createTable(dbConnection);
        Look look=new Look();
        look.createTable(dbConnection);
        Relation_look_matiere relationLookMatiere=new Relation_look_matiere();
        relationLookMatiere.createTable(dbConnection);
    }
}
