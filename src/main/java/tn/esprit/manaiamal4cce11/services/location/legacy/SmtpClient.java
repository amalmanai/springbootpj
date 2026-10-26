package tn.esprit.manaiamal4cce11.services.location.legacy;


public class SmtpClient {

    private final String host;
    private final int port;

    public SmtpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void send(String destinataire, String message) {
        System.out.println("[SMTP " + host + ":" + port + "] -> " + destinataire + " : " + message);
    }
}
