package belinic_scholz;

/**
 * @author Dominik
 * @version 0.1
 */
public class LoadBalancer extends Server implements Balancer{
    
    private int id = 0;
    
    @Override
    public int register(String host, int port) {
        return id++;
    }
    
}
