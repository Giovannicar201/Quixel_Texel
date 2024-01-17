package QuixelTexel.IS.Service.GAC;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Exception.GAC.*;
import org.json.simple.parser.ParseException;

import java.security.NoSuchAlgorithmException;

public interface UtenteService {

    /**
     * Registra un nuovo utente con le informazioni fornite.
     *
     * @param email L'indirizzo email dell'utente per la registrazione.
     * @param nome Il nome dell'utente per la registrazione.
     * @param password La password dell'utente per la registrazione.
     * @param passwordRipetuta La ripetizione della password per confermare la registrazione.
     * @throws NoSuchAlgorithmException Se l'algoritmo di hashing della password non è disponibile.
     * @throws InvalidNameException Se il nome fornito è invalido.
     * @throws InvalidEmailException Se l'indirizzo email fornito è invalido.
     * @throws InvalidPasswordException Se la password fornita è invalida.
     * @throws SignupPasswordsMismatchException Se le password fornite non corrispondono.
     * @throws NotUniqueUserException Se l'indirizzo email è già associato a un utente esistente.
     */
    void signup(String email, String nome, String password, String passwordRipetuta)
            throws NoSuchAlgorithmException,
            InvalidNameException,
            InvalidEmailException,
            InvalidPasswordException,
            SignupPasswordsMismatchException,
            NotUniqueUserException;

    /**
     * Esegue l'accesso di un utente con le credenziali fornite.
     *
     * @param email L'indirizzo email dell'utente per il login.
     * @param password La password dell'utente per il login.
     * @throws NoSuchAlgorithmException Se l'algoritmo di hashing della password non è disponibile.
     * @throws UserNotFoundException Se l'utente con l'indirizzo email fornito non è trovato.
     * @throws LoginPasswordsMismatchException Se la password fornita non corrisponde a quella dell'utente associato all'email.
     * @throws ParseException Se si verifica un errore durante la conversione della data di creazione dell'utente.
     */
    void login(String email, String password)
            throws NoSuchAlgorithmException,
            UserNotFoundException,
            LoginPasswordsMismatchException,
            ParseException;

    /**
     * Ottiene un'entità utente in base all'indirizzo email fornito.
     *
     * @param email L'indirizzo email dell'utente.
     * @return L'entità utente corrispondente all'indirizzo email fornito.
     */
    UtenteEntity get(String email);
}
