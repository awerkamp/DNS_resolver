package DNS;

import java.io.IOException;
import java.io.InputStream;

public class DNS_Question extends Decode {

    String domain_name = "";
    int domain_name_parts = 0;

    void octetsToString(String[] octets, DNS_Question dns_question) {

        for (int i = 0; i < dns_question.domain_name_parts; i++) {
             dns_question.domain_name += octets[i];
             if (i < dns_question.domain_name_parts - 1) {
                 dns_question.domain_name += ".";
             }
        }
    }
    public void decode(InputStream inputStream) throws IOException {

        String[] name = readDomainName(inputStream, this);
        octetsToString(name, this);

        for (int i = 0; i < 4; i++) {
            DNS_Message.get_next_byte(inputStream, decoded_input);
        }
    }

    // Copies the decoded question to the encoded output for the response
    public void encode() {

        encoded_output.addAll(decoded_input);
    }

    String[] readDomainName(InputStream inputStream, DNS_Question dns_question) throws IOException {  // Changed to static because just helping DNS_question calss

        final int DOMAIN_PARTS_SUPPORTED = 5;
        String[] domain_name = new String[DOMAIN_PARTS_SUPPORTED];

        String domain_part = "";
        byte next_byte = DNS_Message.get_next_byte(inputStream, decoded_input);
        byte length = next_byte; // This first byte gives length of the first part of the domain name
        int num_of_loops = 0;

        while (next_byte != 0) {

            for (int i = 0; i < length; i++) {
                next_byte = DNS_Message.get_next_byte(inputStream, decoded_input);
                domain_part += (char) next_byte;
            }
            domain_name[num_of_loops] = domain_part; // Add the next part of the domain to the domain name
            domain_part = "";
            next_byte = DNS_Message.get_next_byte(inputStream, decoded_input); // gives the length of the next part of the domain name or 0 if end
            length = next_byte;
            num_of_loops += 1;
        }

        for (int i = 0; i < num_of_loops; i++) {
            System.out.println(domain_name[i]);
        }
        dns_question.domain_name_parts = num_of_loops;

        return domain_name;
    }
}
