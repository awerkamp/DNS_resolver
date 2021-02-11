package DNS;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DNS_Message {

//    ArrayList<Byte> complete_message = new ArrayList<>();

    ArrayList<DNS_Header> dns_header = new ArrayList<>();
    ArrayList<DNS_Question> dns_question = new ArrayList<>();
    ArrayList<DNS_Record> dns_record = new ArrayList<>();

    static byte get_next_byte(InputStream inputStream, ArrayList<Byte> message_part) throws IOException {
        byte next_byte = (byte) inputStream.read();
        System.out.println(next_byte);
//        complete_message.add(next_byte);
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

    // Changed to static


    void decode_message(byte[] b1) throws IOException {

        InputStream myInputStream = new ByteArrayInputStream(b1);

        DNS_Header dns_header = new DNS_Header();
        dns_header.decode(myInputStream);
        dns_header.encode();
        this.dns_header.add(dns_header);

        DNS_Question dns_question = new DNS_Question();
        dns_question.decode(myInputStream);
        dns_question.encode();
        this.dns_question.add(dns_question);

        DNS_Record dns_record = new DNS_Record();
        dns_record.decode(myInputStream);
        this.dns_record.add(dns_record);

        System.out.println(dns_question.domain_name);
        System.out.println(dns_record.ip_address);
    }


//    static DNSMessage buildResponse(DNSMessage request, DNSRecord[] answers) {
//
//    }


//
//    String[] readDomainName(int firstByte) {
//
//        // Todo create an input stream starting at the first byte passed in and pass it to readDomainName
//        InputStream newStream = new ByteArrayInputStream()
//
//        String[] bob ={"hello"};
//        return bob;
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

//
//    @Override
//    public String toString() {
//        return super.toString();
//    }
}
