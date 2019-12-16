import java.net.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Swarm {

    public DatagramSocket socket; //socket is used for both sending and receiving messages

    udpReceiver receiver;
    udpPacketConverter converter;

    //The drones UDP port is instantiated and is a constant int
    private final int udpPort = 8889;
    private boolean isConnected = false;

    //The array of drones is created
    private ArrayList<TelloDrone> droneSwarm = new ArrayList<>();

    public Swarm() {
        try {
            //create socket for communication
            socket = new DatagramSocket();
        } catch (SocketException e) {
            //e.printStackTrace();
        }
        // Applying the converter and receiver to the swarm
        converter = new udpPacketConverter(droneSwarm);
        converter.start();

        receiver = new udpReceiver(socket, converter);
        receiver.start();
    }

    //function for adding drones to the arraylist
    public void addDrone(TelloDrone drone)
    {
        droneSwarm.add(drone);
    }

    //Establish the communication between program and drone
    public boolean sendCommand(String command, InetAddress ip) {
        try {
            byte[] sendData = command.getBytes();  // byte array from string
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, udpPort);
            socket.send(sendPacket);
            // Printing out the command currently sent to the drones
            System.out.println("send "+ command + " to " + ip.getHostAddress());
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    // Starting SDK mode for the drones in the drone swarm
    public void connectAll() {
        if (!isConnected);
        for (TelloDrone drone: droneSwarm) {
            sendCommand("command", drone.ip);
            drone.setState(TelloDrone.State.OK);
        }
    }

    // The possibility to send commands to a specific drone
    public void sendToSpecific(String command, InetAddress ip) {
        //Not implemented in this version
    }

    //Checks if all drones state are OK
    public boolean allOk() {
        for (TelloDrone drones : droneSwarm) {
            if (drones.getState() != TelloDrone.State.OK && drones.getState() != TelloDrone.State.DISCONNECTED) {
                return false;
            }
        }
        return true;
    }

    // Function that awaits all drones state to be OK
    public static void waitForAllDronesToBeOK(Swarm swarm) {
        while (!swarm.allOk())
        {
            // printing out a dot every second the program waits for the drones to be OK
            System.out.print(".");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
        System.out.println();
    }

    //Following commands are drone actions and movements used to move the drones

    public void takeOff() {
        for (TelloDrone drone: droneSwarm) {
            sendCommand("takeoff", drone.ip);
        }
    }

    public void land() {
        for (TelloDrone drone: droneSwarm) {
            sendCommand("land", drone.ip);
        }
    }

    // Function that sends 'move' commands to all drones
    public boolean moveAll(Direction direction, int cm)
    {
        for (TelloDrone drone: droneSwarm) {
            sendCommand(direction + " " + cm, drone.ip);
            drone.setState(TelloDrone.State.BUSY);
        }
        return false;
    }
}
