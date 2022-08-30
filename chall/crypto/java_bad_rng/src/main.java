import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.util.*;


class Session {
    static Random _rand = new Random();

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
    static String _admin_password_hash = "c4bbcb1fbec99d65bf59d85c8cb62ee2db963f0fe106f483d9afa73bd4e39a8a";

    Vector<Session> _sessions;
    Scanner _scan;

    public SessionManager() {
        _sessions = new Vector();
        _scan = new Scanner(System.in);
        // Add a default admin session
        _sessions.add(new Session(true));
    }

    // Returns the session associated with the given session_id
    public Session find_session(String session_id) {
        for (Session session : _sessions) {
            if (session._session_id.equals(session_id)) {
                return (session);
            }
        }
        return null;
    }

    // Ask the user for a session id and return the associated session
    public Session prompt_for_session() {
        System.out.print("$ session_id > ");
        String session_id = _scan.nextLine();
        return (find_session(session_id));
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
                    session = new Session(false);
                    System.out.println("Your session id: " + session._session_id);
                    _sessions.addElement(session);
                    break;
                // Check the password given, if the password is good create an admin session
                case "login_admin":
                    System.out.print("$ password > ");
                    String password = _scan.nextLine();
                    try {
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
                        BigInteger no = new BigInteger(1, md.digest(password.getBytes("ASCII")));
                        String hashtext = no.toString(16);
                        if (hashtext.equals(_admin_password_hash)) {
                            session = new Session(true);
                            System.out.println("Your session id: " + session._session_id);
                            _sessions.addElement(session);
                        } else {
                            System.out.println("bad password");
                        }
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                // Check if a session has admin powers
                case "is_admin":
                    session = prompt_for_session();
                    if (session == null) {
                        System.out.println("session not found");
                    } else if (session._is_admin) {
                        System.out.println("admin");
                    } else {
                        System.out.println("NOT admin");
                    }
                    break;
                // If admin, launch a shell
                case "admin_shell":
                    session = prompt_for_session();
                    if (session == null) {
                        System.out.println("session not found");
                    }
                    else if (session._is_admin) {
                        // TODO spawn shell
                    } else {
                        System.out.println("NOT admin, you do not have thie permission");
                    }
                    break;
                case "quit":
                    return;
                case "help":
                    System.out.println("    login_guest");
                    System.out.println("    login_admin");
                    System.out.println("    is_admin");
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
