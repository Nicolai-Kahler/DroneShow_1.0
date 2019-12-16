import java.net.DatagramPacket;
import java.util.*;

public class udpPacketConverter extends Thread {

    // Accessing the arraylist of drones
    ArrayList<TelloDrone> drones;

    //byte[] receiveData = new byte[12]; // Virker den her? Fjernes?

        // udpPacketConverters constructor
    public udpPacketConverter(ArrayList<TelloDrone> drones)
    {
        this.drones = drones;
    }

    // This boolean is a "better" solution to our while loops,
    // since we have the opportunity to stop the while loop and not have the program run forever.
    public boolean stop = false;

    // Instantiation of the list of data packets from the drones
    public LinkedList<DatagramPacket> packets = new LinkedList<>();

    @Override
    public void run() {
        while (!stop)
        {
            try {
                // When the packets are handled the first in line always has priority
                handlePacket(packets.getFirst());
            }
            catch (Exception e)
            {
                //e.printStackTrace();
            }

        }
    }

    // translation of the data packet from the drones
    private void handlePacket(DatagramPacket packet) {

        //Converting the data packet to a String of chars.
        char[] c = new char[packet.getData().length];
        for (int i = 0; i < packet.getData().length; i++) {
            c[i] = (char)packet.getData()[i];
        }
        String reply = String.valueOf(c).trim();

        //the state of the drone is altered depending on the received message
        for (TelloDrone drone: drones) {
            if(reply.equals("ok") && drone.ip.equals(packet.getAddress()))
            {
                drone.setState(TelloDrone.State.OK);
            }

            if(reply.equals("error") && drone.ip.equals(packet.getAddress()))
            {
                drone.setState(TelloDrone.State.ERROR);
            }

            if(reply.equals("busy") && drone.ip.equals(packet.getAddress()))
            {
                drone.setState(TelloDrone.State.BUSY);
            }

            System.out.println(drone.ip.toString() + " state is " + drone.getState());


        }
        // Test printouts
        System.out.println(c);
        //System.out.print(Arrays.toString(receiveData));
        System.out.print(" from ");
        System.out.println(packet.getAddress().toString().trim());

        //Remove the packet from the queue
        packets.remove(packet);

    }


}
