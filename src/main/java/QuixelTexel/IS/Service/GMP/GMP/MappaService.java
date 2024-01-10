package QuixelTexel.IS.Service.GMP.GMP;


import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;
import QuixelTexel.IS.Exception.GMP.GMP.InvalidMapHeightException;
import QuixelTexel.IS.Exception.GMP.GMP.InvalidMapNameException;
import QuixelTexel.IS.Exception.GMP.GMP.InvalidMapWidthException;

public interface MappaService {
    String creaMappa(String email, String nome, String lunghezza, String larghezza) throws InvalidMapNameException, InvalidMapWidthException, InvalidMapHeightException, InvalidMapNameException, InvalidMapWidthException, InvalidMapHeightException;

    MappaEntity get(String email);
}
