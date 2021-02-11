package DNS;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DNS_Server {

//    This class should open up a UDP socket (DatagramSocket class in Java), and listen for requests.
//    When it gets one, it should look at all the questions in the request. If there is a valid answer in cache,
//    add that the response, otherwise create another UDP socket to forward the request Google (8.8.8.8) and
//    then await their response. Once you've dealt with all the questions, send the response back to the client.
//
//    Note: dig sends an additional record in the "additionalRecord" fields with a type of 41. You're supposed to
//    send this record back in the additional record part of your response as well.
//
    DatagramSocket socket;
    byte[] b1;
    DatagramPacket dp;
    String ip_address = "";


//    DatagramSocket google_socket;
//    byte[] b2;
//    DatagramPacket dp2;
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


                if (DNS_Cache.has_domain_name(domain_name)) {   // Todo where I am currently working // Need to add things to cache

                    DNS_Record dns_record = DNS_Cache.get_DNS_record(domain_name);

                    DatagramPacket dp_new = new DatagramPacket(b1, b1.length);
//                    dp_new.setPort(8054);
//                    dp_new.setAddress(local_host);

                    dp_new = dp;

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
                    // Todo send record to client


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



//                InputStream myInputStream2 = new ByteArrayInputStream(b1);
//
//                DNS_Header.decodeHeader(myInputStream2);
//
//                DNS_Question dns_question2 = new DNS_Question();
//
//                dns_question2.decodeQuestion(myInputStream2);
//
//                DNS_Record dns_record2 = new DNS_Record();
//
//                dns_record2.decodeRecord(myInputStream2);
//
//                System.out.println(dns_question2.domain_name);
//                System.out.println(dns_record2.ip_address);
            }
        }



//        dns_message.readDomainName(myInputStream);


//        DNS_Question dns_quesiton = DNS_Question.decodeQuestion(myInputStream, );


        ///////////////////////

//
//        dp.setPort(53);
//        dp.setAddress(ip);
//        System.out.println("here");
////
////        DatagramSocket google_socket = new DatagramSocket(8053);
//        socket.send(dp);
//        byte[] b2 =  new byte[512];
//        DatagramPacket dp2 = new DatagramPacket(b1, b1.length);
//        socket.receive(dp2);
//
//        b2 = dp2.getData();
//
//        InputStream myInputStream2 = new ByteArrayInputStream(b1);
//
//        DNS_Header dns_header2 = DNS_Header.decodeHeader(myInputStream2);
//
//        DNS_Message dns_message2 = new DNS_Message();
//
//        DNS_Question dns_question1 = new DNS_Question();
//
//        dns_question1.decodeQuestion(myInputStream2, dns_message2);
//
//        DNS_Record.decodeRecord(myInputStream2, dns_message2);


//
/////////////////////////////////////////////////////////
//
//        b2 = new byte[512];
//        dp2 = new DatagramPacket(b2, b2.length, ip, 8054);
//        google_socket.send(dp2);
//        google_socket.receive(dp);
//        b1 = dp.getData();
//
//        InputStream myInputStream2 = new ByteArrayInputStream(b1);
//
//        DNS_Header dns_header2 = DNS_Header.decodeHeader(myInputStream2);
//
//        System.out.println(dns_header2.toString());




    }


//    public DNS_Server() throws SocketException, UnknownHostException {
//
//
//
//        System.out.println(socket.getLocalPort());
//
//    }


}
