package DNS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DNS_Record extends Decode {

    String ip_address = "";
    ArrayList<Byte> decoded_domain_name = new ArrayList<>();


    // todo add a Date object when program is created
//    public byte[] ip_address = new byte[4];

//    Everything after the header and question parts of the DNS message are stored as records.
//    This should have all the fields listed in the spec as well as a Date object storing when
//    this record was created by your program. It should also have the following public methods:
//
//    static DNSRecord decodeRecord(InputStream, DNSMessage)
//    writeBytes(ByteArrayOutputStream, HashMap<String, Integer>)
//    String toString()
// todo    boolean timestampValid() -- return whether the creation date + the time to live is after
//    the current time. The Date and Calendar classes will be useful for this.


    // MAke void instead of returning DNS_Record
    void decode(InputStream inputStream) throws IOException {



        for (int i = 0; i < 11; i++) {
            byte num = DNS_Message.get_next_byte(inputStream, decoded_input);
            System.out.println("Info for Compression " + i + " " + num);
        }
        byte length = DNS_Message.get_next_byte(inputStream, decoded_input);
        for (int i = 0; i < length; i++ ) {
            byte nextByte = DNS_Message.get_next_byte(inputStream, decoded_input);
            if (nextByte < 0) { // Bitmanip to create unsigned integer
                byte mask = 127; // 01111111
                int newInt = nextByte & mask; // Flips the first bit to 0 and casts to integer
                newInt = newInt | 128; // Flips the 8th bit to 1 -> 1_______
                this.ip_address += newInt;
            } else {
                this.ip_address += nextByte;
            }
            if (i < length - 1) {
                this.ip_address += ".";
            }
        }
    }

    public void encode() {
        byte compression_code = -64;
        byte domain_pointer = 12;
        encoded_output.add(compression_code);
        encoded_output.add(domain_pointer);

        byte type_1 = 0;
        byte type_2 = 0;
        encoded_output.add(type_1);
        encoded_output.add(type_2);

        byte class_1 = 0;
        byte class_2 = 1;
        encoded_output.add(class_1);
        encoded_output.add(class_2);

        byte ttl_1 = 0;
        byte ttl_2 = 0;
        byte ttl_3 = 61; // todo check if this, and next, should be changed (time interval)
        byte ttl_4 = -27;
        encoded_output.add(ttl_1);
        encoded_output.add(ttl_2);
        encoded_output.add(ttl_3);
        encoded_output.add(ttl_4);

        byte rd_length_1 = 0;
        byte rd_length_2 = 4;
        encoded_output.add(rd_length_1);
        encoded_output.add(rd_length_2);

        byte r_data_1 = 93; // IP Address from answer
        byte r_data_2 = -72; // todo change this to be the correct ip address from google or cache
        byte r_data_3 = -40;
        byte r_data_4 = 34;
        encoded_output.add(r_data_1);
        encoded_output.add(r_data_2);
        encoded_output.add(r_data_3);
        encoded_output.add(r_data_4);
    }

    //todo add the ability to add additional records. Copy from Google's response on query?

//
//
//    void writeBytes(ByteArrayOutputStream, HashMap<String, Integer>) {
//
//    }
//
//    @Override
//    public String toString() {
//        return super.toString();
//    }
//
//    boolean timestampValid() {
//        return false;
//    }
}
