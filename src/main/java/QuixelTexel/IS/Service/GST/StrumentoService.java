package QuixelTexel.IS.Service.GST;

import QuixelTexel.IS.Exception.GEN.GEN.EntityNotFoundException;
import QuixelTexel.IS.Exception.GST.InvalidColumnException;
import QuixelTexel.IS.Exception.GST.InvalidRowException;
import QuixelTexel.IS.Service.CanvasService;
import org.json.simple.parser.ParseException;
import java.sql.SQLException;

public abstract class StrumentoService {

    protected CanvasService canvasService;

    public StrumentoService(CanvasService canvasService) {
        this.canvasService = canvasService;
    }

    public abstract String usa(String canvas, String elemento, String riga, String colonna, String email)
            throws SQLException,
            InvalidColumnException,
            EntityNotFoundException,
            ParseException,
            InvalidRowException;
}
