package QuixelTexel.IS.Service.GMP.GST;

import QuixelTexel.IS.Exception.GEN.GEN.EntityNotFoundException;
import QuixelTexel.IS.Exception.GMP.GST.InvalidColumnException;
import QuixelTexel.IS.Exception.GMP.GST.InvalidRowException;
import org.json.simple.parser.ParseException;
import java.sql.SQLException;

public interface MatitaService {
    String piazza(String email, String string, String obj, String riga, String colonna) throws ParseException, SQLException, EntityNotFoundException, InvalidColumnException, InvalidRowException;

    String visualizzaLista(String email, String string) throws SQLException;
}
