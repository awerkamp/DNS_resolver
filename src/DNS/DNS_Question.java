package DNS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class DNS_Question {

    static public byte[] input = new byte[12];
    static public byte[] output = new byte[12];

//    This class represents a client request. It should have the following public methods:
//
//    static DNSQuestion decodeQuestion(InputStream, DNSMessage) -- read a question from the input stream. Due to compression, you may have to ask the DNSMessage containing this question to read some of the fields.
//    void writeBytes(ByteArrayOutputStream, HashMap<String,Integer> domainNameLocations). Write the question bytes which will be sent to the client. The hash map is used for us to compress the message, see the DNSMessage class below.
//    toString(), equals(), and hashCode() -- Let your IDE generate these. They're needed to use a question as a HashMap key, and to get a human readable string.

    static DNS_Question decodeQuestion(InputStream inputStream, DNS_Message dns_message) throws IOException {

        DNS_Message.readDomainName(inputStream);


        byte question = (byte) inputStream.read();

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
