package DNS;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DNS_Message {

    ArrayList<DNS_Header> dns_header = new ArrayList<>();
    ArrayList<DNS_Question> dns_question = new ArrayList<>();
    ArrayList<DNS_Record> dns_record = new ArrayList<>();

    static byte get_next_byte(InputStream inputStream, ArrayList<Byte> message_part) throws IOException {
        byte next_byte = (byte) inputStream.read();
        System.out.println(next_byte);
        message_part.add(next_byte);
        return next_byte;
    }

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
}
