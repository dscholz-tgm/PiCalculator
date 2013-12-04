package belinic_scholz;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Dominik
 * @version 0.3
 */
public class Server implements Calculator {
    
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
    public BigDecimal pi(int anzahl_nachkommastellen) {
        return new CalculatorImpl().pi(anzahl_nachkommastellen);
    }
}
