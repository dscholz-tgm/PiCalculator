package belinic_scholz;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Dominik
 * @version 0.2
 */
public class Server implements Calculator {
    
    public static void main(String args[]) {
        
//        //Parsen des Ports
//        if(args.length < 1) {
//            System.err.println("Das Argument muss eine gueltige Portnummer zwischen 0 und 65535 sein");
//            return;
//        }
//        int port = 0;
//        try {
//            port = Integer.parseInt(args[0]);
//            if (port < 0 || port > 65535) throw new NumberFormatException();
//        } catch (NumberFormatException nfe) {
//            System.err.println("Das Argument muss eine gueltige Portnummer zwischen 0 und 65535 sein");
//            return;
//        }
    	
    	//Verarbeiten der Optionen und Argumente
    	MyCLI cli = new MyCLI(args);
        
        //Erstellen des Security Managers
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
