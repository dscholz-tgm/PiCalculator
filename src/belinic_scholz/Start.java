package belinic_scholz;

/**
 * Startet je nachdem was angegeben wird den Server den Client oder den Balancer.
 * @author Vennesa Belinic
 * @version 2013-12-05
 */
public class Start {

	public static void main(String[] args) {
		MyCLI cli = new MyCLI(args);
		if(cli.getType() == 1) Server.main(args);
		if(cli.getType() == 0) Client.main(args);
		if(cli.getType() == 2 && cli.useBalancer()) LoadBalancer.main(args);

	}

}
