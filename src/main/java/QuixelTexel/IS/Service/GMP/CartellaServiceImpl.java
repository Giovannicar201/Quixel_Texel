package QuixelTexel.IS.Service.GMP;

import QuixelTexel.IS.Utility.Validator;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GMP.GCR.CartellaEntity;
import QuixelTexel.IS.Exception.GMP.InvalidFolderNameException;
import QuixelTexel.IS.Exception.GMP.NotUniqueFolderException;
import QuixelTexel.IS.Repository.GMP.GCR.CartellaRepository;
import QuixelTexel.IS.Service.GAC.UtenteService;

import java.util.List;

@Service
public class CartellaServiceImpl implements CartellaService {

    @Autowired
    private CartellaRepository cartellaRepository;
    @Autowired
    private UtenteService utenteService;


    @Override
    @Transactional
    public void creaCartella(String nome,String email) throws InvalidFolderNameException, NotUniqueFolderException {

        UtenteEntity utenteEntity = utenteService.get(email);
        
        CartellaEntity cartellaEntity = new CartellaEntity();
        CartellaEntity cartellaEntityQuery = cartellaRepository.findByNomeAndUtenteEntity(nome,utenteEntity);

        if(!Validator.isFolderNameValid(nome))
            throw new InvalidFolderNameException("ERRORE - NOME CARTELLA NON VALIDO.");

        if(cartellaEntityQuery != null)
            throw new NotUniqueFolderException("ERRORE - CARTELLA GIÀ ESISTENTE.");

        cartellaEntity.setNome(nome);
        cartellaEntity.setUtenteEntity(utenteEntity);

        cartellaRepository.save(cartellaEntity);
    }

    @Override
    @Transactional
    public String visualizzaListaCartelle(String email) {

        UtenteEntity utenteEntity = utenteService.get(email);

        JSONObject cartelleJSON = new JSONObject();
        JSONArray nomiCartelle = new JSONArray();

        List<CartellaEntity> cartelle = cartellaRepository.findAllByUtenteEntity(utenteEntity);

        for(CartellaEntity cartellaEntity : cartelle)
            nomiCartelle.add(cartellaEntity.getNome());

        cartelleJSON.put("nomiCartelle", nomiCartelle);

        return cartelleJSON.toString();
    }

    @Override
    @Transactional
    public CartellaEntity get(String nome, String email) {

        UtenteEntity utenteEntity = utenteService.get(email);

        return cartellaRepository.findByNomeAndUtenteEntity(nome,utenteEntity);
    }

    @Override
    @Transactional
    public CartellaEntity get(int id) {
        return cartellaRepository.findById(id);
    }
}
