package NetStuff.Net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ShutdownHandler extends Thread {

    private DatagramSocket socket;

    private InetAddress address;

    private int port;

    private User user;

    public ShutdownHandler(User user, DatagramSocket datagramSocket, InetAddress address, int port){
        socket = datagramSocket;
        this.address = address;
        this.port = port;
        this.user = user;
    }

    public void run() {


        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(user);
            TransferPackage transferPackage = new TransferPackage(111, "", null, baos.toByteArray());
            byte[] bytes = transferPackage.getBytes();
            socket.send(new DatagramPacket(bytes, bytes.length, address, port));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
