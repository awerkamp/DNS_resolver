package DNS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DNS_Message {

    static ArrayList<Byte> header = new ArrayList<>();
    static ArrayList<Byte> questions = new ArrayList<>();
    static ArrayList<Byte> answers = new ArrayList<>();
    static ArrayList<Byte> authority_records = new ArrayList<>();
    static ArrayList<Byte> additional_records = new ArrayList<>();

    static ArrayList<Byte> complete_message = new ArrayList<>();


    static byte get_next_byte(InputStream inputStream, ArrayList<Byte> message_part) throws IOException {
        byte next_byte = (byte) inputStream.read();
        complete_message.add(next_byte);
        message_part.add(next_byte);
        return next_byte;
    }


//    This corresponds to an entire DNS Message. It should contain:
//
//    the DNS Header
//    an array of questions
//    an array of answers
//    an array of "authority records" which we'll ignore
//    an array of "additional records" which we'll almost ignore
//    You should also store the the byte array containing the complete message in this class.
//    You'll need it to handle the compression technique described above
//
//    It should have the following methods:
//
//    static DNSMessage decodeMessage(byte[] bytes)
//    String[] readDomainName(InputStream) --read the pieces of a domain name starting from
//    the current position of the input stream
//    String[] readDomainName(int firstByte) --same, but used when there's compression and we
//    need to find the domain from earlier in the message. This method should make a
//    ByteArrayInputStream that starts at the specified byte and call the other version of this method
//    static DNSMessage buildResponse(DNSMessage request, DNSRecord[] answers) --build a response
//    based on the request and the answers you intend to send back.
//    byte[] toBytes() -- get the bytes to put in a packet and send back
//    static void writeDomainName(ByteArrayOutputStream, HashMap<String,Integer> domainLocations,
//    String[] domainPieces) -- If this is the first time we've seen this domain name in the packet,
//    write it using the DNS encoding (each segment of the domain prefixed with its length, 0 at the end),
//    and add it to the hash map. Otherwise, write a back pointer to where the domain has been seen previously.
//    String octetsToString(String[] octets) -- join the pieces of a domain
//    name with dots ([ "utah", "edu"] -> "utah.edu" )
//    String toString()



//    the DNS Header
//    an array of questions
//    an array of answers
//    an array of "authority records" which we'll ignore
//    an array of "additional records" which we'll almost ignore

//    static DNS_Message decodeMessage(byte[] bytes) {
//        System.out.println("test");
//    }


    static String[] readDomainName(InputStream inputStream) throws IOException {  // Changed to static because just helping DNS_question calss

        final int DOMAIN_PARTS_SUPPORTED = 5;
        String[] domain_name = new String[DOMAIN_PARTS_SUPPORTED];

        String domain_part = "";
        byte next_byte = get_next_byte(inputStream, questions);
        byte length = next_byte; // This first byte gives length of the first part of the domain name
        int num_of_loops = 0;

        while (next_byte != 0) {

            for (int i = 0; i < length; i++) {
                next_byte = get_next_byte(inputStream, questions);
                domain_part += (char) next_byte;
            }
            domain_name[num_of_loops] = domain_part; // Add the next part of the domain to the domain name
            domain_part = "";
            next_byte = get_next_byte(inputStream, questions); // gives the length of the next part of the domain name or 0 if end
            length = next_byte;
            num_of_loops += 1;
        }

        for (int i = 0; i < num_of_loops; i++) {
            System.out.println(domain_name[i]);
        }

        return domain_name;
    }
//
//    String[] readDomainName(int firstByte) {
//
//    }
//
    // NDS_RECORD[] is always coming from cache or sometimes adding from google
//    static DNS_Message buildResponse(DNS_Message request, DNS_Record[] answers) {
//
//    }
//
//    byte[] toBytes() {
//
//    }
//
//    static void writeDomainName(ByteArrayOutputStream, HashMap<String,Integer> domainLocations, String[] domainPieces) {
//
//    }
//
    String octetsToString(String[] octets) {

        return octets[0] + "." + octets[1];
    }
//
//    @Override
//    public String toString() {
//        return super.toString();
//    }
}
