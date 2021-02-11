package DNS;

import java.io.IOException;
import java.io.InputStream;

public class DNS_Header extends Decode {

    void encode() {
        for (int i = 0; i < 2; i ++) {
            encoded_output.add(decoded_input.get(i)); // First 2 bytes copy over identifier label
        }

        // Various Codes combined into two bytes
        byte codes_1 = -127;
        byte codes_2 = 32; // todo this is copied from question. change to -96 (google's response) if needed
        encoded_output.add(codes_1); // Codes
        encoded_output.add(codes_2); // Codes

        // Entries in the question section
        byte qd_count_1 = 0;
        byte qd_count_2 = 1;
        encoded_output.add(qd_count_1);
        encoded_output.add(qd_count_2);

        // Records in the answer section
        byte an_count_1 = 0;
        byte an_count_2 = 1;
        encoded_output.add(an_count_1);
        encoded_output.add(an_count_2);

        // Records in the name server section
        byte ns_count_1 = 0;
        byte ns_count_2 = 0;
        encoded_output.add(ns_count_1);
        encoded_output.add(ns_count_2);

        // Records in the additional section
        byte ar_count_1 = 0;
        byte ar_count_2 = 1;
        encoded_output.add(ar_count_1);
        encoded_output.add(ar_count_2);
    }


    public void decode(InputStream inputStream) throws IOException {

        System.out.println("inputstream " + inputStream.toString());

        for (int i = 0; i < 12; i++) {
            DNS_Message.get_next_byte(inputStream, decoded_input);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "DNS_Header{}";
    }
}
