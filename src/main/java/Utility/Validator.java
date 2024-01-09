package Utility;

import java.util.regex.Pattern;

public class Validator {

    /*
    *
    * REGEX E PATTERN GAC.
    *
    * */
    private static final String GMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@gmail.com$";
    private static final Pattern GMAIL_PATTERN = Pattern.compile(GMAIL_REGEX);
    private static final String YAHOO_REGEX = "^[a-zA-Z0-9._%+-]+@yahoo.com$";
    private static final Pattern YAHOO_PATTERN = Pattern.compile(YAHOO_REGEX);
    private static final String HOTMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@hotmail.com$";
    private static final Pattern HOTMAIL_PATTERN = Pattern.compile(HOTMAIL_REGEX);
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final String NOME_UTENTE_REGEX = "^[a-zA-Z]{1,32}$";
    private static final Pattern NOME_UTENTE_PATTERN = Pattern.compile(NOME_UTENTE_REGEX);

    /*
    *
    * REGEX E PATTERN GCR.
    *
    * */

    private static final String NOME_CARTELLA_REGEX = "^(?=.*[a-zA-Z])[a-zA-Z]{1,32}$";
    private static final Pattern NOME_CARTELLA_PATTERN = Pattern.compile(NOME_CARTELLA_REGEX);

    public static boolean isEmailValid(String email) {
        if(email == null)
            return false;

        boolean gmailMatch = GMAIL_PATTERN.matcher(email).matches();
        boolean yahooMatch = YAHOO_PATTERN.matcher(email).matches();
        boolean hotmailMatch = HOTMAIL_PATTERN.matcher(email).matches();

        return gmailMatch || yahooMatch || hotmailMatch;
    }

    public static boolean isUserNameValid(String nome) {
        if(nome == null)
            return false;

        return NOME_UTENTE_PATTERN.matcher(nome).matches();
    }

    public static boolean isPasswordValid(String password) {
        if (password == null)
            return false;

        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isFolderNameValid(String nomeCartella) {
        if(nomeCartella == null)
            return false;

        return NOME_CARTELLA_PATTERN.matcher(nomeCartella).matches();
    }

}
