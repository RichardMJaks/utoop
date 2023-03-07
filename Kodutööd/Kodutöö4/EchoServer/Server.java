package Kodutööd.Kodutöö4.EchoServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import static Kodutööd.Kodutöö4.EchoServer.Codes.*;

public class Server {
    private static final String fileLocation = "files/";

    public static void main(String[] args) throws IOException {
        try (ServerSocket ss = new ServerSocket(1337)) {
            System.out.println("Listening to port 1337...");
            // Okei ma lege eeldasin, et viimane socket peab sulgema serveri ja ma üritasin rahulikult viimased 3 tundi seda teha
            // ja siis imestan et ei leia mingit lahendust sellele netist
            // Võite kohe arvestada et mul läks 3 tundi kauem aega reaalsuses kui ma ütlen sest see nii idiootne probleem
            // mul et ma lic ei loe seda umbkaudsesse aega sisse
            while (true) {
                Socket s = ss.accept();  // Creates a socket
                new Thread(new ThreadedSocket(s)).start(); // Starts a thread
            }
        } catch (IOException e) {
            System.out.println("Got IOException: " + e);
        } catch (Exception e) {
            System.out.println("Got unknown exception: " + e);
        }
        System.out.println("Connection terminated.");
    }

    public static class ThreadedSocket implements Runnable {
        private Socket socket;
        //private final String workingDirectory = "src/Kodutööd/Kodutöö4/EchoServer/"; This can be set in configuration menu also apparently

        public ThreadedSocket(Socket s) {
            this.socket = s;
        }

        /**
         * Completes echo request
         *
         * @param out  DataOutputStream to be used
         * @param echo message to echo back
         * @throws IOException When something goes wrong
         */
        private void echoRequest(DataOutputStream out, String echo) throws IOException {
            System.out.println("Performing Echo Request");
            out.writeInt(OK);
            out.writeUTF(echo);
            System.out.println("Finished performing echo request");
        }

        /**
         * Completes a file request
         *
         * @param out      DataOutputStream to be used
         * @param fileName name of the file to work with
         * @throws IOException When something goes wrong
         */
        private void fileRequest(DataOutputStream out, String fileName) throws IOException {
            System.out.println("Performing file request");
            Path filePath = Path.of(fileLocation + fileName);
            if (Files.isRegularFile(filePath) && !filePath.isAbsolute()) {
                System.out.println("Reading all bytes of file " + fileName);
                byte[] fileContents = Files.readAllBytes(filePath);
                System.out.println("Finished reading bytes of the file.");
                out.writeInt(OK);
                out.writeInt(fileContents.length);
                out.write(fileContents);
                System.out.println("Finished sending file contents.");
            } else {
                if (!Files.isRegularFile(filePath))
                    out.writeInt(ERROR_FILE_NOT_FOUND); // Just make sure if file exists
                else out.writeInt(ERROR_PATH_IS_ABSOLUTE);
            }
        }

        @Override
        public void run() {
            try (DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

                int expectedLength = in.readInt();

                for (int i = 0; i < expectedLength; i++) {
                    int type = in.readInt();
                    String content = in.readUTF();
                    if (type == REQUEST_TYPE_ECHO) echoRequest(out, content); // If type is echo, echo contents back
                    else if (type == REQUEST_TYPE_FILE)
                        fileRequest(out, content); // If type is file, read contents of file and return them
                    else {
                        out.writeInt(ERROR_ACTION_TYPE_NOT_RECOGNIZED);
                    } // If type doesn't have an action
                }
            } catch (IOException e) {
                System.out.println("Thread ran into an IOException: " + (e));
            } // Error handling
            System.out.println("Closing the socket.");
        }
    }
}
