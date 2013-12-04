package belinic_scholz;

import java.math.BigDecimal;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dominik
 * @version 0.1
 */
public class LoadBalancer implements Balancer,Calculator{
    
    private int count = 0;
    private List<String> hosts = new LinkedList<>();
    private List<Integer> ports = new LinkedList<>();
    
    @Override
    public int register(String host, int port) {
        hosts.add(host);
        ports.add(port);
        return hosts.size();
    }
    
    public static void main(String args[]) {

    	//Verarbeiten der Optionen und Argumente
    	MyCLI cli = new MyCLI(args);
        
        //Erstellen der Security Managers
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        //Binden des Calculators / Starten des Servers
        try {
            String name = "ComputePI";
            Calculator engine = new Server();
            Calculator stub = (Calculator) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.createRegistry(cli.getPort()); //ev. auch nur getRegistry(cli.getPort()) noetig
            registry.rebind(name, stub);
            System.out.println("Server gestartet");
        } catch (Exception e) {
            System.err.println("Server Exception:");
            e.printStackTrace();
        }
    }

    @Override
    public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {
        try {
            count = count++%hosts.size();
            
            String name = "ComputePI";
            Registry registry = LocateRegistry.getRegistry(hosts.get(count),ports.get(count));
            Calculator comp = (Calculator) registry.lookup(name);
            return comp.pi(anzahl_nachkommastellen);
        } catch (NotBoundException ex) {
            throw new RemoteException("NotBoundException: " + ex.getMessage());
        }
    }
    
}
