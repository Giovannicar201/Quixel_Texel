package QuixelTexel.IS.Service.GST;

import QuixelTexel.IS.Exception.GEN.GEN.EntityNotFoundException;
import QuixelTexel.IS.Exception.GST.InvalidColumnException;
import QuixelTexel.IS.Exception.GST.InvalidRowException;
import QuixelTexel.IS.Service.CanvasService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

@Service
public class MatitaServiceImpl extends StrumentoService {

    public MatitaServiceImpl(@Qualifier("mappaServiceImpl") CanvasService canvasService) {
        super(canvasService);
    }

    @Override
    public String usa(String canvas, String elemento, String riga, String colonna,String email)
            throws SQLException,
            InvalidColumnException,
            EntityNotFoundException,
            ParseException,
            InvalidRowException {
        return canvasService.piazza(canvas,elemento,riga,colonna,email);
    }
}
