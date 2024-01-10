package QuixelTexel.IS.Control.GMP.GST;

import QuixelTexel.IS.Exception.GMP.GST.GSTException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class MatitaControl {

    public abstract void piazza(String obj, HttpServletRequest request, HttpServletResponse response) throws GSTException;

}
