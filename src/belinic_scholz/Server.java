package belinic_scholz;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Dominik
 * @version 0.3
 */
public class Server implements Calculator {
    
    private static int id = -1;
    
    public static void main(String args[]) {

    	//Verarbeiten der Optionen und Argumente
    	MyCLI cli = new MyCLI(args);
        
        //Erstellen der Security Managers
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        if(!cli.getNameBalancer().equals("")) {
            try {
             Registry registry = LocateRegistry.getRegistry(cli.getNameBalancer(),cli.getPortBalancer());
             Balancer bal = (Balancer) registry.lookup("ComputePI");
             id = bal.register(InetAddress.getLocalHost().getHostAddress(), cli.getPort()); //Nicht sicher Ob das mit der Host Address funktioniert
             System.out.println("Got Server ID: " + id);
         } catch (RemoteException | NotBoundException | UnknownHostException ex) {
             System.err.println("Naehere Informationen:" + ex.getMessage() + "\n" + ex.getStackTrace());
         }
        }
        
        //Binden des Calculators / Starten des Servers
        try {
            Calculator calc = (Calculator) UnicastRemoteObject.exportObject(new Server(), 0);
            Registry registry = LocateRegistry.createRegistry(cli.getPort()); //ev. auch nur getRegistry(cli.getPort()) noetig
            registry.rebind("ComputePI", calc);
            System.out.println("Server gestartet");
        } catch (RemoteException ex) {
            System.err.println("Server Exception: " + ex.getMessage());
        }
    }

    @Override
    public BigDecimal pi(int anzahl_nachkommastellen) {
        System.out.println("Server " + id + ": calulating PI");
        return new CalculatorImpl().pi(anzahl_nachkommastellen);
    }
}
