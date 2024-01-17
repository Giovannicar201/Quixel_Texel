package QuixelTexel.IS.Service.GST;

import QuixelTexel.IS.Exception.GEN.GEN.EntityNotFoundException;
import QuixelTexel.IS.Exception.GST.*;
import QuixelTexel.IS.Service.GMP.GMP.MappaServiceImpl;
import QuixelTexel.IS.Utility.Validator;
import jakarta.transaction.Transactional;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@Service
public class ScatteringServiceImpl implements ScatteringService {

    @Autowired
    private MappaServiceImpl mappaService;

    @Override
    @Transactional
    public String scatter(String mappa, String rigaPrimoPunto, String colonnaPrimoPunto, String rigaSecondoPunto, String colonnaSecondoPunto, String percentualeRiempimento, List<String> nomiEntita, List<String> prioritaEntita, String email)
            throws InvalidFillPercentageException,
            InvalidNumberOfPriorityException,
            InvalidPriorityPercentageSumException,
            SQLException,
            InvalidColumnException,
            EntityNotFoundException,
            ParseException,
            InvalidRowException {

        Random random = new Random();

        int primaRiga = Integer.parseInt(rigaPrimoPunto);
        int primaColonna = Integer.parseInt(colonnaPrimoPunto);
        int secondaRiga = Integer.parseInt(rigaSecondoPunto);
        int secondaColonna = Integer.parseInt(colonnaSecondoPunto);

        int altezza = (secondaRiga - primaRiga) + 1;
        int larghezza = (secondaColonna - primaColonna) + 1;

        if(percentualeRiempimento == null || percentualeRiempimento.compareTo("") == 0)
            throw new InvalidFillPercentageException("ERRORE - PERCENTUALE DI RIEMPIMENTO NON VALIDA");

        float percentuale = Float.parseFloat(percentualeRiempimento);
        int totaleDaPiazzare = (int) ((altezza * larghezza * percentuale) / 100);

        float[] prioritaArray = new float[prioritaEntita.size()];
        int[] daPiazzareArray = new int[prioritaEntita.size()];

        float sommaPriorita = 0;

        for(int i = 0; i < prioritaEntita.size(); i++) {

            String priorita = prioritaEntita.get(i);

            if(priorita == null || priorita.compareTo("") == 0 || priorita.contains(".") || priorita.contains("-") || priorita.contains(","))
                throw new InvalidPriorityPercentageSumException("ERRORE - NUMERO DI PRIORITÀ NON VALIDO.");

            prioritaArray[i] = Float.parseFloat(priorita);

            daPiazzareArray[i] = (int) ((totaleDaPiazzare * prioritaArray[i]) / 100);

            sommaPriorita += prioritaArray[i];
        }

        if(!Validator.isFillPercentageValid(percentuale))
            throw new InvalidFillPercentageException("ERRORE - PERCENTUALE DI RIEMPIMENTO NON VALIDA.");

        if(!Validator.isNumberOfPriorityValid(prioritaArray.length,altezza * larghezza))
            throw new InvalidNumberOfPriorityException("ERRORE - NUMERO DI PRIORITÀ NON VALIDO.");

        if(!Validator.isPriorityPercentageSumValid(sommaPriorita))
            throw new InvalidPriorityPercentageSumException("ERRORE - SOMMA DELLE PRIORITÀ NON VALIDA.");

        for(int i = 0; i < nomiEntita.size(); i++) {

            String nome = nomiEntita.get(i);

            for(int j = 0; j < daPiazzareArray[i]; j++) {

                String riga = random.nextInt(altezza) + primaRiga + "";
                String colonna = random.nextInt(larghezza) + primaColonna + "";

                mappa = mappaService.piazza(mappa,nome,riga,colonna,email);
            }
        }

        return mappa;
    }
}
