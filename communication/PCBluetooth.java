import java.io.DataOutputStream;
import java.io.IOException;

// Lejos imports
import lejos.pc.comm.*;

// Connects to robot and sends commands
public class PCBluetooth {

    private static DataOutputStream blueOutStream;
    private static NXTComm communicator;
    private static String name = "NXT";
    private static String address = "00:16:53:07:D6:2B";

    public static void main(String[] args) throws InterruptedException, IOException{
        openConnection();
        Thread.sleep(2000);
        for (int i=1; i<100; i++) {
            sendMessage(i);
            Thread.sleep(200);
        }
        closeConnection();
    }

    // Tries to connect to robot and set up an output stream
    public static void openConnection() {
        // Set up bluetooth
        try {
            communicator = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
        } catch (NXTCommException e) {
            e.printStackTrace();
        }

        // Try connect
        NXTInfo nxtInfo = new NXTInfo(0, name,address);
        System.out.format("Connecting to %s", nxtInfo.name);

        boolean connected = false;
        try {
            connected = communicator.open(nxtInfo);
        } catch (NXTCommException e) {
            e.printStackTrace();
        }

        // Set up output stream if connection worked
        if (!connected) {
            System.out.println("Failed");
        } else {
            System.out.format("Connected to %s", nxtInfo.name);
            try {
                blueOutStream = new DataOutputStream(communicator.getOutputStream());
            } catch (Exception e) {
                System.out.println("Failed");
            }
        }
    }

    // Sends a given message to the device
    public static void sendMessage(int message) throws IOException {
        System.out.println("PCBluetooth: " + message);
        blueOutStream.writeInt(message);
        blueOutStream.flush();
    }

    // Closes the connection and the output stream
    // Also sends a kill command to the robot
    public static void closeConnection() throws IOException {
        System.out.println("Closing connections");
        sendMessage(0);
        communicator.close();
        blueOutStream.close();
    }
}
