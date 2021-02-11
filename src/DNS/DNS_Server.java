package DNS;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DNS_Server {

    DatagramSocket socket;
    byte[] b1;
    DatagramPacket dp;

    InetAddress google_ip = InetAddress.getByName("8.8.8.8");
    InetAddress local_host = InetAddress.getByName("127.0.0.1");

    DatagramPacket set_datagram_data(DatagramPacket dp, ArrayList<Byte> final_message) {
        byte[] final_message_array = new byte[final_message.size()];

        for (int i = 0; i < final_message.size(); i++) {
            final_message_array[i] = final_message.get(i);
        }
        dp.setData(final_message_array,0,final_message.size());

        return dp;
    }

    public DNS_Server() throws Exception {

        byte prev_id = 100;
        byte current_id;

        socket = new DatagramSocket(8054);
        b1 =  new byte[512];

        while (true) {

            dp = new DatagramPacket(b1, b1.length);
            socket.receive(dp);
            b1 = dp.getData();

            current_id = b1[1];
            if (current_id == prev_id) {
                System.out.println("Waiting for a new packet");

            } else {
                prev_id = current_id;
                System.out.println("ID " + current_id);

                DNS_Message dns_message_question = new DNS_Message();
                dns_message_question.decode_message(b1);
                String domain_name = dns_message_question.dns_question.get(0).domain_name;


                if (DNS_Cache.has_domain_name(domain_name)) {

                    DNS_Record dns_record = DNS_Cache.get_DNS_record(domain_name);

                    DatagramPacket dp_new = new DatagramPacket(b1, b1.length);
                    dp_new.setPort(8054);
                    dp_new.setAddress(local_host);

                    ArrayList<Byte> header = dns_message_question.dns_header.get(0).encoded_output;
                    ArrayList<Byte> question = dns_message_question.dns_question.get(0).encoded_output;
                    ArrayList<Byte> record = dns_record.encoded_output;

                    ArrayList<Byte> final_message = new ArrayList<Byte>(header);
                    final_message.addAll(question);
                    final_message.addAll(record);

                    dp_new = set_datagram_data(dp_new, final_message);

                    System.out.println(Arrays.toString(dp_new.getData()));

                    System.out.println("CACHE HAS DOMAIN - SENDING TO CLIENT");

                    socket.send(dp_new);


                } else {

                    dp.setPort(53);
                    dp.setAddress(google_ip);

                    socket.send(dp);
                    System.out.println("CACHE DOESN'T HAVE DOMAIN - REQUESTING FROM GOOGLE");

                    DatagramPacket dp2 = new DatagramPacket(b1, b1.length);
                    socket.receive(dp2);

                    DNS_Message dns_message_google_response = new DNS_Message();

                    dns_message_google_response.decode_message(b1);

                    DNS_Cache.add_entry(dns_message_google_response.dns_question.get(0), dns_message_google_response.dns_record.get(0));

                    System.out.println("FORWARDING GOOGLE RESPONSE TO CLIENT");

                    dp.setPort(8054);
                    dp.setAddress(local_host);

                    socket.send(dp);
                }
            }
        }
    }
}
