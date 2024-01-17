package QuixelTexel.IS.Utility;

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
    private static final String NOME_UTENTE_REGEX = "^[a-zA-Z\\s]{1,32}$";
    private static final Pattern NOME_UTENTE_PATTERN = Pattern.compile(NOME_UTENTE_REGEX);

    /*
     *
     * REGEX E PATTERN GCR.
     *
     * */

    private static final String NOME_CARTELLA_REGEX = "^(?=.*[a-zA-Z\\s])[a-zA-Z\\s]{1,32}$";
    private static final Pattern NOME_CARTELLA_PATTERN = Pattern.compile(NOME_CARTELLA_REGEX);

    /*
     *
     * REGEX E PATTERN GEN.
     *
     * */

    private static final String NOME_ENTITA_REGEX = "^[a-zA-Z\\s]{1,32}$";
    private static final Pattern NOME_ENTITA_PATTERN = Pattern.compile(NOME_ENTITA_REGEX);
    private static final String COLLISIONE_REGEX = "\\b(?:si|no)\\b";
    private static final Pattern COLLISIONE_PATTERN = Pattern.compile(COLLISIONE_REGEX);
    private static final String NOME_PROPRIETA_REGEX = "^[a-zA-Z\\s]{1,32}$";
    private static final Pattern NOME_PROPRIETA_PATTERN = Pattern.compile(NOME_PROPRIETA_REGEX);
    private static final String VALORE_PROPRIETA_REGEX = "^[a-zA-Z\\s]{1,64}$";
    private static final Pattern VALORE_PROPRIETA_PATTERN = Pattern.compile(VALORE_PROPRIETA_REGEX);

    /*
     *
     * REGEX E PATTERN GMP.
     *
     * */

    private static final String NOME_MAPPA_REGEX = "^[a-zA-Z\\s]{1,32}$";
    private static final Pattern NOME_MAPPA_PATTERN = Pattern.compile(NOME_MAPPA_REGEX);

    /*
     *
     * REGEX E PATTERN GPL.
     *
     * */

    private static final String NOME_PALETTE_REGEX = "^(?=.*[a-zA-Z\\s])[a-zA-Z\\s]{1,32}$";
    private static final Pattern NOME_PALETTE_PATTERN = Pattern.compile(NOME_PALETTE_REGEX);

    /*
     *
     * REGEX E PATTERN GPA.
     *
     * */

    private static final String NOME_PIXEL_ART_REGEX = "^[a-zA-Z\\s]{1,32}$";
    private static final Pattern NOME_PIXEL_ART_PATTERN = Pattern.compile(NOME_PIXEL_ART_REGEX);

    /*
     *
     * REGEX E PATTERN GEV.
     *
     * */

    private static final String NOME_EVENTO_REGEX = "^[a-zA-Z\\s]{1,32}$";
    private static final Pattern NOME_EVENTO_PATTERN = Pattern.compile(NOME_EVENTO_REGEX);
    private static final String VALORE_ISTRUZIONE_TESTO_REGEX = "^[a-zA-Z\\s]{1,32}$";
    private static final Pattern VALORE_ISTRUZIONE_TESTO_PATTERN = Pattern.compile(VALORE_ISTRUZIONE_TESTO_REGEX);
    private static final String VALORE_ISTRUZIONE_DIALOGO_REGEX = "^[a-zA-Z\\s]{1,32}$";
    private static final Pattern VALORE_ISTRUZIONE_DIALOGO_PATTERN = Pattern.compile(VALORE_ISTRUZIONE_DIALOGO_REGEX);

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

    public static boolean isPropertyNameValid(String nomeProprieta) {
        if(nomeProprieta == null)
            return false;

        return NOME_PROPRIETA_PATTERN.matcher(nomeProprieta).matches();
    }

    public static boolean isPropertyValueValid(String valoreProprieta) {
        if(valoreProprieta == null)
            return false;

        return VALORE_PROPRIETA_PATTERN.matcher(valoreProprieta).matches();
    }

    public static boolean isEntityNameValid(String nomeEntita) {
        if(nomeEntita == null)
            return false;

        return NOME_ENTITA_PATTERN.matcher(nomeEntita).matches();
    }

    public static boolean isCollisionValid(String collisione) {
        if(collisione == null)
            return false;

        return COLLISIONE_PATTERN.matcher(collisione).matches();
    }

    public static boolean isNumberOfPropertyValid(int numeroDiProprieta) {
        return numeroDiProprieta >= 0 && numeroDiProprieta <= 4;
    }

    public static boolean isMapNameValid(String nomeMappa) {
        if(nomeMappa == null)
            return false;

        return NOME_MAPPA_PATTERN.matcher(nomeMappa).matches();
    }

    public static boolean isMapWidthValid(long larghezza) {
        return larghezza >= 0 && larghezza <= 32;
    }

    public static boolean isMapHeightValid(long altezza) {
        return altezza >= 0 && altezza <= 32;
    }

    public static boolean isRowValid(String riga, long altezza) {
        if(riga == null || riga.compareTo("") == 0)
            return  false;

        return Integer.parseInt(riga) >= 0 && Integer.parseInt(riga) <= (altezza - 1);
    }

    public static boolean isColumnValid(String colonna, long larghezza) {
        if(colonna == null || colonna.compareTo("") == 0)
            return  false;

        return Integer.parseInt(colonna) >= 0 && Integer.parseInt(colonna) <= (larghezza - 1);
    }

    public static boolean isPaletteNameValid(String nomePalette) {
        if(nomePalette == null)
            return false;

        return NOME_PALETTE_PATTERN.matcher(nomePalette).matches();
    }

    public static boolean isNumberOfColorValid(int numeroDiColori) {
        return numeroDiColori >= 1 && numeroDiColori <= 8;
    }

    public static boolean isPixelArtNameValid(String nomePixelArt) {
        if(nomePixelArt == null)
            return false;

        return NOME_PIXEL_ART_PATTERN.matcher(nomePixelArt).matches();
    }

    public static boolean isFillPercentageValid(float percentualeRiempimento) {
        return percentualeRiempimento >= 0 && percentualeRiempimento <= 100 ;
    }

    public static boolean isNumberOfPriorityValid(int numeroDiPriorita, int area) {
        return numeroDiPriorita <= area;
    }

    public static boolean isPriorityPercentageSumValid(float sommaPriorita) {
        return sommaPriorita == 100 ;
    }

    public static boolean isEventNameValid(String nomeEvento) {
        if(nomeEvento == null)
            return false;

        return NOME_EVENTO_PATTERN.matcher(nomeEvento).matches();
    }

    public static boolean isPositionValid(String riga, String colonna, long altezza, long larghezza) {
        if(riga == null || riga.compareTo("") == 0)
            return  false;

        if(colonna == null || colonna.compareTo("") == 0)
            return false;

        return Integer.parseInt(colonna) >= 0 && Integer.parseInt(colonna) <= (larghezza - 1) && Integer.parseInt(riga) >= 0 && Integer.parseInt(riga) <= (altezza - 1);
    }

    public static boolean isNumberOfInstructionValid(int numeroDiIstruzioni) {
        return numeroDiIstruzioni > 0 && numeroDiIstruzioni <= 16;
    }

    public static boolean isCycleValueValid(int iterazioni) {
        return iterazioni >= 0 && iterazioni <= 4;
    }

    public static boolean isDialogValueValid(String valore) {
        if(valore == null)
            return false;

        return VALORE_ISTRUZIONE_DIALOGO_PATTERN.matcher(valore).matches();
    }

    public static boolean isTextValueValid(String valore) {
        if(valore == null)
            return false;

        return VALORE_ISTRUZIONE_TESTO_PATTERN.matcher(valore).matches();
    }
}
