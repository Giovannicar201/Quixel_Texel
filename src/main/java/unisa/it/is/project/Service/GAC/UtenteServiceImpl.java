package unisa.it.is.project.Service.GAC;

import Utility.Utility;
import Utility.Validator;
import jakarta.transaction.Transactional;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unisa.it.is.project.Entity.GAC.UtenteEntity;
import unisa.it.is.project.Exception.GAC.*;
import unisa.it.is.project.Repository.GAC.UtenteRepository;
import java.security.NoSuchAlgorithmException;

@Service
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    @Transactional
    public void signup(String email, String nome, String password, String passwordRipetuta) throws NoSuchAlgorithmException, InvalidEmailException, InvalidNameException, InvalidPasswordException, SignupPasswordsMismatchException, NotUniqueUserException {

        UtenteEntity utenteEntity = new UtenteEntity();
        UtenteEntity utenteEntityQuery = utenteRepository.findByEmail(email);

        if(utenteEntityQuery != null)
            throw new NotUniqueUserException("ERRORE - UTENTE GIÀ REGISTRATO.");

        if(!Validator.isEmailValid(email))
            throw new InvalidEmailException("ERRORE - EMAIL NON VALIDA.");

        if(!Validator.isUserNameValid(nome))
            throw new InvalidNameException("ERRORE - NOME NON VALIDO.");

        if(!Validator.isPasswordValid(password))
            throw new InvalidPasswordException("ERRORE - PASSWORD NON VALIDA.");

        if(!Validator.isPasswordValid(passwordRipetuta))
            throw new InvalidPasswordException("ERRORE - PASSWORD RIPETUTA NON VALIDA.");

        if(!password.equals(passwordRipetuta))
            throw new SignupPasswordsMismatchException("ERRORE - LE PASSWORD NON COINCIDONO.");

        utenteEntity.setEmail(email);
        utenteEntity.setNome(nome);
        utenteEntity.setPassword(Utility.encrypt(password));

        utenteRepository.save(utenteEntity);
    }

    @Override
    @Transactional
    public void login(String email, String password) throws NoSuchAlgorithmException, UserNotFoundException, LoginPasswordsMismatchException, ParseException {

        UtenteEntity utenteEntityQuery = utenteRepository.findByEmail(email);

        if(utenteEntityQuery == null)
            throw new UserNotFoundException("ERRORE - UTENTE NON REGISTRATO.");

        if(!Utility.encrypt(password).equals(utenteEntityQuery.getPassword()))
            throw new LoginPasswordsMismatchException("ERRORE - LE PASSWORD NON COINCIDONO.");
    }

    @Override
    @Transactional
    public UtenteEntity get(String email) {
        return utenteRepository.findByEmail(email);
    }
}
