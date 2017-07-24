package workflow.utilities;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by sramalin on 12/06/17.
 */
@Component
public class CommonUtilities {



    public <T> List<T> loadObjectList(Class<T> type, byte[] csvFile) {
        try {

            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            MappingIterator<T> readValues = mapper.readerWithSchemaFor(type).with(bootstrapSchema).readValues(csvFile);
            return readValues.readAll();

        } catch (Exception e) {
            System.out.println("Error occurred while loading object list from file " +  e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<String[]> loadManyToManyRelationship(byte[] csvFile) {
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(true);
            mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
            MappingIterator<String[]> readValues =
                    mapper.readerFor(String[].class).with(bootstrapSchema).readValues(csvFile);
            return readValues.readAll();
        } catch (Exception e) {
            System.out.println("Error occurred while loading many to many relationship from file = " + csvFile);
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
    public boolean sendMail(String toMail, String userLastname, long ticketID, String ticketName) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("workflowpondy@gmail.com","Password@123");
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(toMail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toMail));
            message.setSubject("File assigned");
            message.setText("Dear "+ userLastname +"," +
                    "\n\n Ticket with id - "+ticketID + " and ticket name "+ ticketName +"  has been assigned to you \n\n Regards, Ticket admin");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}


