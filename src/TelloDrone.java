import java.lang.String;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class TelloDrone {

    //Tello drone variables
    public InetAddress ip;
    private State state;

    public TelloDrone(String ip) {
        try {
            //Providing a specific ip and checking the ip format
            this.ip = InetAddress.getByName(ip);
        }
        catch (UnknownHostException e) {
            //e.printStackTrace();
            System.out.println("IP address not valid!");
        }
        this.state = State.DISCONNECTED;
}
    // Used for getting the current state
    public State getState() {
        return state;
    }

    // Used for setting a current state for the drones
    public void setState(State state) {
        this.state = state;
    }

    // Creating an enum with the different states
    enum State {
        OK, ERROR, BUSY, DISCONNECTED;
    }
}