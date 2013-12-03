package belinic_scholz;

import org.apache.commons.cli2.*;
import org.apache.commons.cli2.builder.*;
import org.apache.commons.cli2.commandline.Parser;
import org.apache.commons.cli2.util.HelpFormatter;

/**
 * Verwaltet die Konsolen-Eingabe des Users
 * @author Vennesa Belinic
 * @version 2013-12-03
 */
public class MyCLI {
	
	//Attribut(e)
	
	/**
	 * Der Name des Servers.
	 * Langform: --host | Kurzform: -h
	 */
	private String host = "";
	
	/**
	 * Der Port.
	 * Langform: --port | Kurzform: -p
	 */
	private int port = 52741;
	
	/**
	 * Die Nachkommastellen.
	 * Langform: --stellen | Kurzform: -s
	 */
	private int stellen = 3;
	
	

	
	//Konstruktor(en)
	
	/**
	 * Ueberprueft die uebergebenen Optionen und Argumente.
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
				.withArgument(abuilder.withName("\n\t\tHost, hier gibt man den Namen/IP-Adr des Hosts an. \n\t\tVERPFLICHTEND\n")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option port = obuilder.withLongName("port").withShortName("p").withRequired(false)
				.withArgument(abuilder.withName("\n\t\tPort, dies ist der Port ueber den kommuniziert wird.\n\t\tEine Zahl zwischen 0 und 65535\n\t\tStandartmaessig: 52741\n\t\tNICHT VERPFLICHTEND\n")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option stellen = obuilder.withLongName("stellen").withShortName("s").withRequired(false)
				.withArgument(abuilder.withName("\n\t\tStellen, die Nachkommastellen von Pi.\n\t\tEine Zahl zwischen 0 und (2^31)-1\n\t\tStandartmaessig: 3\n\t\tNICHT VERPFLICHTEND\n")
						.withMinimum(1).withMaximum(1).create()).create();
		
		
		Group options = gbuilder.withName("options").withOption(host).withOption(port).withOption(stellen).create();
		
		Parser parser = new Parser();
		parser.setGroup(options);
	
		
	//[3]
		HelpFormatter hf = new HelpFormatter();
		hf.setShellCommand("RMI");
		hf.setGroup(options);
		hf.getFullUsageSettings().remove(DisplaySetting.DISPLAY_GROUP_EXPANDED);
		hf.getDisplaySettings().remove(DisplaySetting.DISPLAY_GROUP_ARGUMENT);
	//[3]
	//Apache2, "Helping", aktualisiert: 2013, online verfügbar: http://commons.apache.org/sandbox/commons-cli2/examples/ant.html, zuletzt besucht am: 22.10.2013
		
		
		/*
		 * Hier werden die Optionen und Argumente aus der args-Variable ausgelesen, und mit entsprechenden
		 * Meldungen und Exceptions verwaltet.
		 */
		try {
			CommandLine cl = parser.parse(args);
			
			if(cl.hasOption(host)) {
				try {
					this.host = (String) cl.getValue(host);
				} catch(Exception e) {
				//wenn etwas beim Catsen schief geht wird die Hilfe/Beschreibung ausgegeben und das Programm beendet
					hf.print();
					System.exit(1);
				}
			}
			
			if(cl.hasOption(port)) {
				try {
					this.port = Integer.parseInt((String) cl.getValue(port));
					if (this.port < 0 || this.port > 65535) throw new NumberFormatException();
				} catch(Exception e) {
				//wenn etwas beim Catsen schief geht wird die Hilfe/Beschreibung ausgegeben und das Programm beendet
					hf.print();
					System.exit(1);
				}
			}
			
			if(cl.hasOption(stellen)) {
				try {
					this.stellen = Integer.parseInt((String) cl.getValue(stellen));
					if (this.stellen  < 0 || this.stellen > Integer.MAX_VALUE) throw new NumberFormatException();
				} catch(Exception e) {
				//wenn etwas beim Catsen schief geht wird die Hilfe/Beschreibung ausgegeben und das Programm beendet
					hf.print();
					System.exit(1);
				}
			}
			
		} catch(OptionException e) {
		//wenn etwas beim Verarbeiten der Optionen und argmente schief geht wird die Hilfe/Beschreibung augegeben und das Programm beendet
			hf.print();
			System.exit(1);
		}
	}

	
	
	//Methode(n)
	
	/**
	 * Gibt den Host zurueck.
	 * @return den Host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Gibt den Port zurueck.
	 * @return den Port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Gibt die Stellen zurueck.
	 * @return ddie Stellen
	 */
	public int getStellen() {
		return stellen;
	}

}
