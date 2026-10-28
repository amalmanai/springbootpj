package tn.esprit.manaiamal4cce11.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Client;
import tn.esprit.manaiamal4cce11.services.INotificationService;
import tn.esprit.manaiamal4cce11.services.location.legacy.SmsGateway;
import tn.esprit.manaiamal4cce11.services.location.legacy.SmtpClient;

@Slf4j
@Service
public class NotificationServiceImpl implements INotificationService {

    @Override
    public void notifier(Client client, String canal, String message) {
        if (client == null) {
            log.warn("Impossible d'envoyer la notification : client nul");
            return;
        }

        if ("EMAIL".equalsIgnoreCase(canal)) {
            SmtpClient smtp = new SmtpClient("smtp.autoloc.tn", 25);
            smtp.send(client.getEmail(), message);
            log.info("Email envoyé à {} : {}", client.getEmail(), message);
        } else if ("SMS".equalsIgnoreCase(canal)) {
            SmsGateway sms = new SmsGateway("https://sms.autoloc.tn");
            sms.send(client.getTelephone(), message);
            log.info("SMS envoyé à {} : {}", client.getTelephone(), message);
        } else {
            log.info("Notification générique [{}] vers {} : {}", canal, client.getEmail(), message);
        }
    }
}
