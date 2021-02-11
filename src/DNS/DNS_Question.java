package DNS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DNS_Question extends Decode {

    String domain_name = "";
    int domain_name_parts = 0;

//    This class represents a client request. It should have the following public methods:
//
//    static DNSQuestion decodeQuestion(InputStream, DNSMessage) -- read a question from the input stream. Due to compression, you may have to ask the DNSMessage containing this question to read some of the fields.
//    void writeBytes(ByteArrayOutputStream, HashMap<String,Integer> domainNameLocations). Write the question bytes which will be sent to the client. The hash map is used for us to compress the message, see the DNSMessage class below.
//    toString(), equals(), and hashCode() -- Let your IDE generate these. They're needed to use a question as a HashMap key, and to get a human readable string.


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

        while (next_byte != 0) {  // todo this needs to be in a for loop?

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
//
//    void writeBytes(ByteArrayOutputStream, HashMap<String,Integer> domainNameLocations) {
//
//    }
//
//
//    @Override
//    public String toString() {
//        return "DNS_Question{}";
//    }



}
