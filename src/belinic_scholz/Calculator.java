package belinic_scholz;

import java.math.BigDecimal;
import java.rmi.Remote;

/**
 * Schnittstelle auf die der User zugreift
 * @author Angabe
 * @version 2013-11-28
 */
public interface Calculator extends Remote {
    public BigDecimal pi(int anzahl_nachkommastellen);
}