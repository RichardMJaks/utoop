package Kodutööd.Kodutöö4.EchoServer;

public abstract class Codes {
    // Status codes
    public static final int ERROR_ACTION_TYPE_NOT_RECOGNIZED = 6;
    public static final int ERROR_PATH_IS_ABSOLUTE = 5;
    public static final int ERROR_FILE_NOT_FOUND = 7;
    public static final int ERROR_ECHO_EXCEPTION = 8;
    public static final int ERROR_IO_EXCEPTION = 9; // Thought I'd leave it in bc I felt real dumb after I realized how the hell is server supposed to send this error back to client
    public static final int OK = 0;

    // Types
    public static final int REQUEST_TYPE_ECHO = 0;
    public static final int REQUEST_TYPE_FILE = 1;
}
