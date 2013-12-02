package belinic_scholz;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * Der Clienten der auf Methoden eines bestimmten Interfaceses zugreift.
 * @author Vennesa Belinic
 * @version 2013-11-28
 */
public class Client {

    public static void main(String args[]) {
        
        //Parsen des Ports
        if(args.length < 3) {
            System.err.println("Synopsis: [host] [port] [kommastellen]");
            return;
        }
        int port = 0;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 0 || port > 65535) throw new NumberFormatException();
        } catch (NumberFormatException nfe) {
            System.err.println("Das Argument muss eine gültige Portnummer zwischen 0 und 65535 sein");
            return;
        }
        
       //Erstellen des Security Managers
        //[2]
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            //System.setProperty("java.security.policy","file:./java.policy");	//[1]
            //nicht noetig es soll mit der lokalen Policy funktionieren
        }

        //Aufrufen des Servers / Berechnung von PI
        try {
             String name = "ComputePI";
             Registry registry = LocateRegistry.getRegistry(args[0],port);
             Calculator comp = (Calculator) registry.lookup(name);
             BigDecimal pi = comp.pi(Integer.parseInt(args[2]));
             System.out.println(pi);
         } catch (Exception e) {
             System.err.println("Naehere Informationen:" + e.getMessage());
         }
       
       //[2]
    }    
	
    /**
     * [1] Honza, "How do I set the security.policy file for a specific application from within NetBeans?", aktualisiert am: 13.10.2013, 
     *     online verfuegbar: http://stackoverflow.com/questions/6061722/how-do-i-set-the-security-policy-file-for-a-specific-application-from-within-net,
     *     zuletzt besucht am: 28.11.2013
     *     
     * [2] Oracle, "Creating a Client", aktualisiert am: 2013, online verfuegbar: http://docs.oracle.com/javase/tutorial/rmi/client.html,
     *     zuletzt besucht am: 28.11.2013
     */
	
}
