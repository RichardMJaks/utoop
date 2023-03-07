package Kodutööd.Kodutöö4.EchoServer;

import java.io.*;
import java.net.Socket;
import java.security.InvalidParameterException;

import static Kodutööd.Kodutöö4.EchoServer.Codes.*;

public class Client {

    /**
     * Gets the request code from String. Overloaded method should be used beforehand to check for errors, as this assumes if it is not other type request it is file type request.
     *
     * @param type request type as String
     * @return request code
     */
    private static int getRequestType(String type) {
        if (type.equals("echo")) return REQUEST_TYPE_ECHO;
        else return REQUEST_TYPE_FILE;
    }

    /**
     * Gets the request code from String.
     *
     * @param type request type as String
     * @param i    index of current request in args array
     * @return request code
     * @throws InvalidParameterException Throws InvalidParameterException when argument is not in valid types
     */
    private static int getRequestType(String type, int i) throws InvalidParameterException {
        if (type.equals("echo")) return REQUEST_TYPE_ECHO;
        else if (type.equals("file")) return REQUEST_TYPE_FILE;
        else throw new InvalidParameterException("Action type not in valid types. Argument index: " + i);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Connecting to server...");
        try (Socket s = new Socket("localhost", 1337);
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             DataInputStream in = new DataInputStream(s.getInputStream())) {
            System.out.println("Connected, sending requests...");

            if (args.length % 2 == 1)
                throw new InvalidParameterException("Not an even number of args, something is missing?");

            // Should be disabled for efficiency, just an extra layer of protection to check if everything is in order
            for (int i = 1; i < args.length; i += 2) {
                getRequestType(args[i - 1], i); // Throws premature exception if request types are not correct
            }

            // Send number of messages server should expect
            out.writeInt(args.length / 2);

            for (int i = 1; i < args.length; i += 2) {
                int requestType = getRequestType(args[i - 1]);
                out.writeInt(requestType); // Send action type
                out.writeUTF(args[i]); // Send content

                int responseCode = in.readInt();
                switch (responseCode) {
                    case OK -> {
                        if (requestType == REQUEST_TYPE_FILE) {
                            int byteArrayLength = in.readInt();
                            byte[] answer = in.readNBytes(byteArrayLength); // Reading in bytes so response would be compatible with all file types (don't trust the multiple conversions)
                            File file = new File("recieved/" + args[i]);
                            if (file.createNewFile()) {
                                try (FileOutputStream fos = new FileOutputStream(file)) {
                                    fos.write(answer);
                                    System.out.printf("Created file named \"%s\"", args[i]);
                                }
                            } else {
                                System.out.println("Had an error while creating a new file: file already exists. For safety reasons not going to overwrite it.");
                            }
                        } else if (requestType == REQUEST_TYPE_ECHO) {
                            String answer = in.readUTF();
                            System.out.println("Recieved echo: " + answer);
                        }
                    }
                    case ERROR_ACTION_TYPE_NOT_RECOGNIZED ->
                            System.out.println("Server recieved an invalid action type. Response code: " + responseCode);
                    case ERROR_ECHO_EXCEPTION ->
                            System.out.println("Server returned an echo exception. Response code: " + responseCode);
                    case ERROR_FILE_NOT_FOUND ->
                            System.out.println("Server couldn't find the requested file. Response code: " + responseCode);
                    case ERROR_PATH_IS_ABSOLUTE ->
                            System.out.println("Server detected an absolute path instead of file name. Response code: " + responseCode);
                    default ->
                            System.out.println("Response code not recognized. Recieved response code: " + responseCode);
                }
            }
            System.out.println("Finished, terminating connection...");
        }
        System.out.println("Connection terminated");
    }
}
