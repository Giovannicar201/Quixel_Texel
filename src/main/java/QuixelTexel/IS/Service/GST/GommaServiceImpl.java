package QuixelTexel.IS.Service.GST;

import QuixelTexel.IS.Exception.GST.InvalidColumnException;
import QuixelTexel.IS.Exception.GST.InvalidRowException;
import QuixelTexel.IS.Service.CanvasService;
import jakarta.transaction.Transactional;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Qualifier;

public class GommaServiceImpl extends StrumentoService {

    public GommaServiceImpl(@Qualifier("mappaServiceImpl") CanvasService canvasService) {
        super(canvasService);
    }

    @Override
    @Transactional
    public String usa(String canvas, String elemento, String riga, String colonna, String email)
            throws InvalidColumnException,
            ParseException,
            InvalidRowException {
        return canvasService.rimuovi(canvas,riga,colonna,email);
    }
}
