package QuixelTexel.IS.Service.GEN.GEN;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Entity.GEN.GEN.ProprietaEntity;
import QuixelTexel.IS.Entity.GEN.GIM.ImmagineEntity;
import QuixelTexel.IS.Entity.GMP.GCR.CartellaEntity;
import QuixelTexel.IS.Exception.GEN.GEN.*;
import QuixelTexel.IS.Repository.GEN.GEN.EntitaRepository;
import QuixelTexel.IS.Service.GAC.UtenteService;
import QuixelTexel.IS.Service.GEN.GIM.ImmagineService;
import QuixelTexel.IS.Service.GMP.GCR.CartellaService;
import QuixelTexel.IS.Utility.Validator;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

@Service
public class EntitaServiceImpl implements EntitaService {

    @Autowired
    private EntitaRepository entitaRepository;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ImmagineService immagineService;
    @Autowired
    private CartellaService cartellaService;
    @Autowired
    private ProprietaService proprietaService;

    @Override
    @Transactional
    public void creaEntita(String email, String nomeImmagine, String nome, String collisioni, String nomeCartella, List<String> nomiProprieta, List<String> valoriProprieta)
            throws InvalidCollisionException,
            FolderNotFoundException,
            InvalidNumberOfPropertyException,
            NotUniqueEntityException,
            InvalidEntityNameException,
            ImageNotFoundException,
            ImageAlreadyUsedException,
            InvalidPropertyNameException,
            InvalidPropertyValueException,
            NotUniquePropertyException {

        UtenteEntity utenteEntity = utenteService.get(email);

        EntitaEntity entitaEntity = new EntitaEntity();

        EntitaEntity entitaEntityQuery = entitaRepository.findByNomeAndUtenteEntity(nome,utenteEntity);

        ImmagineEntity immagineEntityQuery = immagineService.get(nomeImmagine,email);

        EntitaEntity secondaEntitaEntityQuery = entitaRepository.findByImmagineEntity(immagineEntityQuery);

        CartellaEntity cartellaEntityQuery = cartellaService.get(nomeCartella,email);

        if(secondaEntitaEntityQuery != null)
            throw new ImageAlreadyUsedException("ERRORE - IMMAGINE GIÀ USATA.");

        if(immagineEntityQuery == null)
            throw new ImageNotFoundException("ERRORE - IMMAGINE NON ESISTENTE.");

        if(!Validator.isEntityNameValid(nome))
            throw new InvalidEntityNameException("ERRORE - NOME NON VALIDO.");

        if(entitaEntityQuery != null)
            throw new NotUniqueEntityException("ERRORE - ENTITÀ GIÀ ESISTENTE.");

        if(!Validator.isCollisionValid(collisioni))
            throw new InvalidCollisionException("ERRORE - COLLISIONI NON VALIDE.");

        if(cartellaEntityQuery == null)
            throw new FolderNotFoundException("ERRORE - CARTELLA NON ESISTENTE.");

        if(!Validator.isNumberOfPropertyValid(nomiProprieta.size()))
            throw new InvalidNumberOfPropertyException("ERRORE - NUMERO DI PROPRIETÀ NON VALIDO.");

        Iterator<String> iteratoreNomi = nomiProprieta.iterator();
        Iterator<String> iteratoreValori = valoriProprieta.iterator();

        entitaEntity.setUtenteEntity(utenteEntity);
        entitaEntity.setImmagineEntity(immagineEntityQuery);
        entitaEntity.setNome(nome);
        entitaEntity.setCollisione(collisioni);
        entitaEntity.setCartellaEntity(cartellaEntityQuery);

        entitaRepository.save(entitaEntity);

        while (iteratoreNomi.hasNext() && iteratoreValori.hasNext()) {
            String nomeProprieta = iteratoreNomi.next();
            String valoreProprieta = iteratoreValori.next();

            try {

                proprietaService.creaProprieta(nomeProprieta, valoreProprieta, entitaEntity);

            } catch (InvalidPropertyNameException e) {

                entitaRepository.delete(entitaEntity);

                throw new InvalidPropertyNameException("ERRORE - NOME PROPRIETÀ NON VALIDO.");

            } catch (NotUniquePropertyException e) {

                entitaRepository.delete(entitaEntity);

                throw new NotUniquePropertyException("ERRORE - PROPRIETÀ GIÀ ESISTENTE.");

            } catch (InvalidPropertyValueException e) {

                entitaRepository.delete(entitaEntity);

                throw new InvalidPropertyValueException("ERRORE - VALORE PROPRIETÀ NON VALIDO.");

            }
        }
    }

    @Override
    @Transactional
    public String eliminaEntita(String mappa, String nome, String email)
            throws EntityNotFoundException,
            ParseException {

        JSONParser parser = new JSONParser();

        UtenteEntity utenteEntity = utenteService.get(email);

        EntitaEntity entitaEntityQuery = entitaRepository.findByNomeAndUtenteEntity(nome,utenteEntity);

        if(entitaEntityQuery == null)
            throw new EntityNotFoundException("ERRORE - ENTITÀ NON ESISTENTE.");

        JSONObject mappaJSON = (JSONObject) parser.parse(mappa);

        JSONArray entita = (JSONArray) mappaJSON.get("mappa");

        int id = entitaEntityQuery.getId();

        for (Object entitaOBJ : entita) {
            JSONObject entitaJSON = (JSONObject) entitaOBJ;

            int idEntita = Math.toIntExact((Long) entitaJSON.get("id"));

            if(id == idEntita) {
                entitaJSON.put("id",0);
                entitaJSON.put("riga",entitaJSON.get("riga"));
                entitaJSON.put("colonna",entitaJSON.get("colonna"));
                entitaJSON.put("immagine","");
            }

        }

        entitaRepository.delete(entitaEntityQuery);

        mappaJSON.put("mappa",entita);

        return mappaJSON.toString();
    }

    @Override
    @Transactional
    public String visualizzaEntita(String nome, String email) throws EntityNotFoundException {

        UtenteEntity utenteEntity = utenteService.get(email);

        JSONObject entitaJSON = new JSONObject();
        JSONArray proprieta = new JSONArray();
        EntitaEntity entitaEntityQuery = entitaRepository.findByNomeAndUtenteEntity(nome,utenteEntity);

        if(entitaEntityQuery == null)
            throw new EntityNotFoundException("ERRORE - ENTITÀ NON ESISTENTE.");

        for(ProprietaEntity proprietaEntity : proprietaService.getLista(entitaEntityQuery)) {
            JSONObject proprietaJSON = new JSONObject();

            proprietaJSON.put(proprietaEntity.getNome(),proprietaEntity.getValore());

            proprieta.add(proprietaJSON);
        }

        entitaJSON.put("nome",entitaEntityQuery.getNome());
        entitaJSON.put("collisioni",entitaEntityQuery.getCollisione());
        entitaJSON.put("proprieta",proprieta);

        return entitaJSON.toString();
    }

    @Override
    @Transactional
    public String visualizzaListaEntitaInCartella(String email, String nomeCartella) throws SQLException, FolderNotFoundException {

        UtenteEntity utenteEntity = utenteService.get(email);

        CartellaEntity cartellaEntityQuery = cartellaService.get(nomeCartella,email);

        JSONObject immaginiJSON = new JSONObject();
        JSONArray blobImmagini = new JSONArray();

        if(cartellaEntityQuery == null)
            throw new FolderNotFoundException("ERROE - CARTELLA NON ESISTENTE.");

        List<EntitaEntity> entita = entitaRepository.findAllByCartellaEntityAndUtenteEntity(cartellaEntityQuery,utenteEntity);

        for(EntitaEntity entitaEntity : entita) {
            JSONObject immagineJSON = new JSONObject();

            Blob immagine = entitaEntity.getImmagineEntity().getImmagine();
            byte[] bytes = immagine.getBytes(1, (int) immagine.length());

            immagineJSON.put(entitaEntity.getNome(), Base64.getEncoder().encodeToString(bytes));

            blobImmagini.add(immagineJSON);
        }

        immaginiJSON.put("blobImmagini", blobImmagini);

        return immaginiJSON.toString();
    }

    @Override
    @Transactional
    public String visualizzaListaEntita(String email) throws SQLException {

        UtenteEntity utenteEntity = utenteService.get(email);

        JSONObject immaginiJSON = new JSONObject();
        JSONArray blobImmagini = new JSONArray();

        List<EntitaEntity> entita = entitaRepository.findAllByUtenteEntity(utenteEntity);

        for(EntitaEntity entitaEntity : entita) {
            JSONObject immagineJSON = new JSONObject();

            Blob immagine = entitaEntity.getImmagineEntity().getImmagine();
            byte[] bytes = immagine.getBytes(1, (int) immagine.length());

            immagineJSON.put(entitaEntity.getNome(), Base64.getEncoder().encodeToString(bytes));

            blobImmagini.add(immagineJSON);
        }

        immaginiJSON.put("blobImmagini", blobImmagini);

        return immaginiJSON.toString();
    }

    @Override
    @Transactional
    public EntitaEntity get(String nome, String email) {

        UtenteEntity utenteEntity = utenteService.get(email);

        return entitaRepository.findByNomeAndUtenteEntity(nome,utenteEntity);
    }

    @Override
    @Transactional
    public EntitaEntity get(int id) {
        return entitaRepository.findById(id);
    }
}
