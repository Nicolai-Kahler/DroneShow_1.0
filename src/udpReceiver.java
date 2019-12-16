import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

//Threaded programming
public class udpReceiver extends Thread
{
    DatagramSocket socket;
    udpPacketConverter converter;

    //udpReceiver constructor
    public udpReceiver(DatagramSocket socket, udpPacketConverter converter)
    {
        this.socket = socket;
        this.converter = converter;
    }

    // byte array of received data
    byte[] receiveData = new byte[12];

    @Override
    public void run() {
        while (true) // forever
        {
            try {
                // Establish the retrieval of the drones messages
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                //Add new messages to the udpConverter
                converter.packets.add(receivePacket);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }
}
