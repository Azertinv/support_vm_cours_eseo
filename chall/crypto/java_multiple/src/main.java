import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.util.*;
import java.time.Instant;
import java.nio.file.Path;
import java.nio.file.Paths;

class Session {
    static Random _rand = new Random(Instant.now().getEpochSecond());

    public String _session_id;
    public boolean _is_admin;

    // Generate a random hex string to use as a session id
    String generateSecretToken() {
        return Long.toHexString(_rand.nextLong());
    }

    public Session(boolean is_admin) {
        _session_id = generateSecretToken();
        _is_admin = is_admin;
    }
}

class SessionManager {
    static String   _admin_password_hash = "c4bbcb1fbec99d65bf59d85c8cb62ee2db963f0fe106f483d9afa73bd4e39a8a";
    Vector<Session> _sessions;
    Scanner         _scan;

    public SessionManager() {
        _sessions = new Vector<Session>();
        // Add a default admin session
        _sessions.add(new Session(true));
        _scan = new Scanner(System.in);
    }

    // Returns the session associated with the given session_id
    public Session find_session(String session_id) {
        for (Session session : _sessions)
            if (session._session_id.startsWith(session_id))
                return (session);
        return null;
    }

    // Ask the user for a session id and return the associated session
    public Session prompt_for_session() {
        System.out.print("$ session_id > ");
        String session_id = _scan.nextLine();
        // If no session id is given always return null
        if (session_id.length() == 0)
            return null;
        else
            return find_session(session_id);
    }

    // Create a session and give the session id to the user
    public void create_session(boolean is_admin) {
        Session session = new Session(is_admin);
        System.out.println("Your session id: " + session._session_id);
        _sessions.addElement(session);
    }

    // Main prompt loop
    public void loop() {
        Session session = null;
        while (true) {
            System.out.print("$ > ");
            String cmd = _scan.nextLine();
            switch (cmd) {
                // Don't ask for a password as guest, just generate a session
                case "login_guest":
                    create_session(false);
                    break;

                // Check the password given, if the password is good create an admin session
                case "login_admin":
                    System.out.print("$ password > ");
                    String password = _scan.nextLine();
                    try {
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
                        BigInteger no = new BigInteger(1, md.digest(password.getBytes("ASCII")));
                        String hashtext = no.toString(16);
                        if (hashtext.equals(_admin_password_hash))
                            create_session(true);
                        else
                            System.out.println("bad password");
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                // Check if a session has admin powers
                case "is_admin":
                    session = prompt_for_session();
                    if (session == null)
                        System.out.println("session not found");
                    else if (session._is_admin)
                        System.out.println("admin");
                    else
                        System.out.println("NOT admin");
                    break;

                // List files in a directory
                case "list":
                    session = prompt_for_session();
                    if (session == null)
                        System.out.println("session not found");
                    else {
                        System.out.print("$ path > ");
                        String path = _scan.nextLine();
                        ProcessBuilder pb = new ProcessBuilder("ls", "-l", path);
                        pb.inheritIO();
                        try {
                            pb.start().waitFor();
                        } catch (IOException | InterruptedException e) {}
                    }
                    break;

                // Display a file
                case "display":
                    session = prompt_for_session();
                    if (session == null)
                        System.out.println("session not found");
                    else {
                        System.out.print("$ filename > ");
                        String filename = _scan.nextLine();
                        Path cwd = Paths.get("").toAbsolutePath().normalize();
                        Path file = Paths.get(filename).toAbsolutePath().normalize();
                        // check if the file is in the current directory or deeper
                        if (file.startsWith(cwd)) {
                            ProcessBuilder pb = new ProcessBuilder("cat", filename);
                            pb.inheritIO();
                            try {
                                pb.start().waitFor();
                            } catch (IOException | InterruptedException e) {}
                        } else
                            System.out.println("filename is invalid");
                    }
                    break;

                // Clone a git repository
                case "clone":
                    session = prompt_for_session();
                    if (session == null)
                        System.out.println("session not found");
                    else {
                        System.out.print("$ url > ");
                        String url = _scan.nextLine();
                        System.out.print("$ path > ");
                        String path = _scan.nextLine();
                        Path cwd = Paths.get("").toAbsolutePath().normalize();
                        Path file = Paths.get(path).toAbsolutePath().normalize();
                        // check if the path given is in the current directory or deeper
                        if (file.startsWith(cwd)) {
                            ProcessBuilder pb = new ProcessBuilder("git", "clone", url, path);
                            pb.inheritIO();
                            try {
                                pb.start().waitFor();
                            } catch (IOException | InterruptedException e) {}
                        } else
                            System.out.println("path is invalid");
                    }
                    break;

                // If admin, launch a shell
                case "admin_shell":
                    session = prompt_for_session();
                    if (session == null)
                        System.out.println("session not found");
                    else if (session._is_admin) {
                        ProcessBuilder pb = new ProcessBuilder("/bin/bash");
                        pb.inheritIO();
                        try {
                            pb.start().waitFor();
                        } catch (IOException | InterruptedException e) {}
                    } else
                        System.out.println("NOT admin, you do not have this permission");
                    break;

                case "quit":
                    return;

                case "help":
                    System.out.println("    login_guest");
                    System.out.println("    login_admin");
                    System.out.println("    is_admin");
                    System.out.println("    list");
                    System.out.println("    display");
                    System.out.println("    clone");
                    System.out.println("    admin_shell");
                    System.out.println("    quit");
                    System.out.println("    help");
                    break;

                default:
                    System.out.println("bad command");
                    break;
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        SessionManager sm = new SessionManager();
        sm.loop();
    }
}
