package QuixelTexel.IS.Service.GPA.GPL;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GPA.PaletteEntity;
import QuixelTexel.IS.Exception.GPA.GPL.InvalidNumberColorException;
import QuixelTexel.IS.Exception.GPA.GPL.InvalidPaletteNameException;
import QuixelTexel.IS.Exception.GPA.GPL.PaletteCreationException;
import QuixelTexel.IS.Repository.GPA.PaletteRepository;
import QuixelTexel.IS.Service.GAC.UtenteService;
import QuixelTexel.IS.Utility.Validator;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaletteServiceImpl implements PaletteService {

    @Autowired
    private PaletteRepository paletteRepository;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ColoreService coloreService;

    @Override
    @Transactional
    public void creaPalette(String nomePalette, String email, List<String> esadecimaleColori)
            throws PaletteCreationException,
            InvalidPaletteNameException,
            InvalidNumberColorException {

        UtenteEntity utenteEntity = utenteService.get(email);

        PaletteEntity paletteEntity = new PaletteEntity();
        PaletteEntity paletteEntityQuery = paletteRepository.findByNomePalette(nomePalette);

        if(paletteEntityQuery != null)
            throw new PaletteCreationException("ERRORE - PALETTE GIÀ CREATA");

        if(!Validator.isPaletteNameValid(nomePalette))
            throw new InvalidPaletteNameException("ERRORE - NOME NON VALIDO");

        if(!Validator.isNumberOfColorValid(esadecimaleColori.size()))
            throw new InvalidNumberColorException("ERRORE - NUMERO DI COLORI NON VALIDO");

        paletteEntity.setNomePalette(nomePalette);

        utenteEntity.setEmail(email);

        paletteEntity.setUtenteEntity(utenteEntity);

        for (String esadecimale : esadecimaleColori) {
            coloreService.creaColore(esadecimale, paletteEntity);
        }

        paletteRepository.save(paletteEntity);
    }

    @Override
    @Transactional
    public PaletteEntity get(String nomePalette,String email) {

        UtenteEntity utenteEntity = utenteService.get(email);

        return paletteRepository.findByNomePaletteAndUtenteEntity(nomePalette,utenteEntity);
    }

    @Override
    @Transactional
    public String visualizzaListaPalette(String email) {

        UtenteEntity utenteEntity = utenteService.get(email);

        JSONObject paletteJSON = new JSONObject();
        JSONArray nomiPalette = new JSONArray();

        List<PaletteEntity> palette = paletteRepository.getAllByUtenteEntity(utenteEntity);

        for(PaletteEntity paletteEntity : palette)
            nomiPalette.add(paletteEntity.getNomePalette());

        paletteJSON.put("nomiPalette",nomiPalette);

        return paletteJSON.toString();
    }
}
