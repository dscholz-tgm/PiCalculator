package belinic_scholz;

import java.rmi.RemoteException;

/**
 * Schnittstelle zum registrieren des Servers
 * @author Dominik
 * @version 0.1
 */
public interface Balancer extends Calculator {
    public int register(String host,int port) throws RemoteException;
}