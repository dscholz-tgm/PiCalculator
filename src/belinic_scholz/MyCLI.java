package belinic_scholz;

import java.util.List;

import org.apache.commons.cli2.*;
import org.apache.commons.cli2.builder.*;
import org.apache.commons.cli2.commandline.Parser;
import org.apache.commons.cli2.util.HelpFormatter;

/**
 * Verwaltet die Konsolen-Eingabe des Users
 *
 * @author Vennesa Belinic
 * @version 2013-12-03
 */
public class MyCLI {

	//Attribut(e)
    /**
     * Der Name des Servers. Langform: --host | Kurzform: -h
     */
    private String host = "";

    /**
     * Der Port. Langform: --port | Kurzform: -p
     */
    private int port = 52741;

    /**
     * Die Nachkommastellen. Langform: --stellen | Kurzform: -s
     */
    private int stellen = 3;
    
    /**
     * Gehoert zur Option type (erstes Argument).
     * Server = 1 | Client = 0 | Balancer = 2
     * Standartmaessig: Client
     */
    private int type = 0;
    
    /**
     * Gehoert zur Option type (zweites Argument).
     * Der Port des Balancers.
     */
    private int portbalancer = 0;
    
    /**
     * Gehoert zur Option type (drittes Argument).
     * Der Name des Balancers.
     */
    private String namebalancer = "";

    
    
	//Konstruktor(en)
    /**
     * Ueberprueft die uebergebenen Optionen und Argumente.
     *
     * @param args Die Optionen und Argumente die uebergeben werden
     */
    public MyCLI(String[] args) {
        DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
        ArgumentBuilder abuilder = new ArgumentBuilder();
        GroupBuilder gbuilder = new GroupBuilder();

        /*
         * Hier werden die Optionen gebildet, immer mit einem longName(wenn --name) und einem shortName(wenn -n),
         * einer passenden Beschreibung der Option und einer passenden Beschreibung zum Argument.
         */
        Option host = obuilder.withLongName("host").withShortName("h").withRequired(true)
                .withArgument(abuilder.withName("\tHost, hier gibt man den Namen/IP-Adr des Hosts an. \n\t\tVERPFLICHTEND")
                        .withMinimum(1).withMaximum(1).create()).create();

        Option port = obuilder.withLongName("port").withShortName("p").withRequired(false)
                .withArgument(abuilder.withName("\tPort, dies ist der Port ueber den kommuniziert wird.\n\t\t"
                		+ "Eine Zahl zwischen 0 und 65535\n\t\tStandartmaessig: 52741\n\t\tNICHT VERPFLICHTEND")
                        .withMinimum(1).withMaximum(1).create()).create();

        Option stellen = obuilder.withLongName("stellen").withShortName("s").withRequired(false)
                .withArgument(abuilder.withName("\tStellen, die Nachkommastellen von Pi.\n\t\t\t"
                		+ "Eine Zahl zwischen 0 und (2^31)-1\n\t\t\tStandartmaessig: 3\n\t\t\tNICHT VERPFLICHTEND")
                        .withMinimum(1).withMaximum(1).create()).create();
        
        Option type = obuilder.withLongName("type").withShortName("t").withRequired(true)
                .withArgument(abuilder.withName("\n\t\tType, ob es ein Server, Balancer oder ein Client ist.\n\t\t"
                		+ "Client ... 0\n\t\tServer ... 1\n\t\tBalancer ... 2\n\t\tStandartmaessig: Client\n\t\tVERPFLICHTEND\n"
                		+ "\n\t\tPortBalancer, der Port des Balancers.\n\t\t"
                		+ "Eine Zahl zwischen 0 und 65535\n\t\tNICHT VERPFLICHTEND, ausser man benutzt einen Balancer\n"
                		+ "\n\t\tNameBalancer, der Name des Balancers.\n\t\t"
                		+ "NICHT VERPFLICHTEND, ausser man benutzt einen Balancer")
                        .withMinimum(1).withMaximum(3).withInitialSeparator(('=')).withSubsequentSeparator(('-')).create())
//                .withArgument(abuilder.withName("\n\t\tPortBalancer, der Port des Balancers.\n\t\t"
//                		+ "Eine Zahl zwischen 0 und 65535\n\t\tNICHT VERPFLICHTEND, ausser man benutzt einen Balancer\n")
//                        .withMinimum(1).withMaximum(1).create())
//                .withArgument(abuilder.withName("\n\t\tNameBalancer, der Name des Balancers.\n\t\t"
//                		+ "NICHT VERPFLICHTEND, ausser man benutzt einen Balancer\n")
//                        .withMinimum(1).withMaximum(1).create())
                .create();

        Group options = gbuilder.withName("options").withOption(host).withOption(port).withOption(stellen).withOption(type).create();

        Parser parser = new Parser();
        parser.setGroup(options);

        //[3]
        HelpFormatter hf = new HelpFormatter();
		hf.setShellCommand("RMI");
		hf.setGroup(options);
		hf.getFullUsageSettings().remove(DisplaySetting.DISPLAY_GROUP_EXPANDED);
        //[3]
        //Apache2, "Helping", aktualisiert: 2013, online verfuegbar: http://commons.apache.org/sandbox/commons-cli2/examples/ant.html, zuletzt besucht am: 22.10.2013

        /*
         * Hier werden die Optionen und Argumente aus der args-Variable ausgelesen, und mit entsprechenden
         * Meldungen und Exceptions verwaltet.
         */
        try {
            CommandLine cl = parser.parse(args);

            if (cl.hasOption(host)) {
                try {
                    this.host = (String) cl.getValue(host);
                } catch (Exception e) {
                    //wenn etwas beim Catsen schief geht wird die Hilfe/Beschreibung ausgegeben und das Programm beendet
                    //System.out.print(e.getMessage() + "\n" + e.getStackTrace());
                	hf.print();
                    System.exit(1);
                }
            }

            if (cl.hasOption(port)) {
                try {
                    this.port = Integer.parseInt((String) cl.getValue(port));
                    if (this.port < 0 || this.port > 65535) {
                        throw new NumberFormatException();
                    }
                } catch (Exception e) {
                    //wenn etwas beim Catsen schief geht wird die Hilfe/Beschreibung ausgegeben und das Programm beendet
                	//System.out.print(e.getMessage() + "\n" + e.getStackTrace());
                	hf.print();
                    System.exit(1);
                }
            }

            if (cl.hasOption(stellen)) {
                try {
                    this.stellen = Integer.parseInt((String) cl.getValue(stellen));
                    if (this.stellen < 0 || this.stellen > Integer.MAX_VALUE) {
                        throw new NumberFormatException();
                    }
                } catch (Exception e) {
                    //wenn etwas beim Catsen schief geht wird die Hilfe/Beschreibung ausgegeben und das Programm beendet
                	//System.out.print(e.getMessage() + "\n" + e.getStackTrace());
                	hf.print();
                    System.exit(1);
                }
            }
            
            if (cl.hasOption(type)) {
                try {
                    List temp = cl.getValues(type);
                    if(temp.size() == 1 || temp.size() == 3) this.type = (Integer.parseInt((String) temp.get(0)));
                    if(temp.size() == 2) throw new IllegalArgumentException();
                    if(temp.size() == 3) {
                    	this.portbalancer = Integer.parseInt((String) temp.get(1));
                    	if (this.portbalancer < 0 || this.portbalancer > 65535) {
                    		throw new NumberFormatException();
                        }
                    	this.namebalancer = (String) temp.get(2);
                    }
                } catch (Exception e) {
                    //wenn etwas beim Catsen schief geht wird die Hilfe/Beschreibung ausgegeben und das Programm beendet
                	//System.out.print(e.getMessage() + "\n" + e.getStackTrace());
                	hf.print();
                    System.exit(1);
                }
            }

        } catch (OptionException e) {
            //wenn etwas beim Verarbeiten der Optionen und argmente schief geht wird die Hilfe/Beschreibung augegeben und das Programm beendet
        	//System.out.print(e.getMessage() + "\n" + e.getStackTrace());
        	hf.print();
            System.exit(1);
        }
    }

	//Methode(n)
    /**
     * Gibt den Host zurueck.
     *
     * @return den Host
     */
    public String getHost() {
        return host;
    }

    /**
     * Gibt den Port zurueck.
     *
     * @return den Port
     */
    public int getPort() {
        return port;
    }

    /**
     * Gibt die Stellen zurueck.
     *
     * @return die Stellen
     */
    public int getStellen() {
        return stellen;
    }
    
    /**
     * Gibt zurueck ob der User einen Server, Balancer oder einen Client starten will.
     *
     * @return was gestartet werden soll
     */
    public int getType() {
        return type;
    }
    
    /**
     * Gibt den Port des Balnacers zurueck.
     *
     * @return den Port des Balancers
     */
    public int getPortBalancer() {
        return portbalancer;
    }
    
    /**
     * Gibt den Namen des Balancers zurueck.
     *
     * @return den Namen des Balancers
     */
    public String getNameBalancer() {
        return namebalancer;
    }
    
    /**
     * Gibt zurueck ob der Server einen Balancer verwendet
     *
     * @return ob ein Balancer verwendet wird
     */
    public boolean useBalancer() {
    	return (this.namebalancer.equals("") && this.portbalancer == 0)?false:true;
    }

}
