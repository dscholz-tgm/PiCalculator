package belinic_scholz;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Schnittstelle zum registrieren des Servers
 * @author Dominik
 * @version 0.1
 */
public interface Balancer extends Remote {
    public int register(String host,int port) throws RemoteException;
}