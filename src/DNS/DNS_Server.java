package DNS;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

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


//    DatagramSocket google_socket;
//    byte[] b2;
//    DatagramPacket dp2;
    InetAddress ip = InetAddress.getByName("8.8.8.8");

    public DNS_Server() throws IOException {
        socket = new DatagramSocket(8053);
        b1 =  new byte[512];
        dp = new DatagramPacket(b1, b1.length);

        socket.receive(dp);
        System.out.println("printing dp " + dp);
        b1 = dp.getData();
        System.out.println("printing b1 " + b1);

        InputStream myInputStream = new ByteArrayInputStream(b1);

        DNS_Header dns_header = DNS_Header.decodeHeader(myInputStream);

        DNS_Message dns_message = new DNS_Message();

        dns_message.readDomainName(myInputStream);

//        DNS_Question dns_quesiton = DNS_Question.decodeQuestion(myInputStream, );


        ///////////////////////

//
//        dp.setPort(53);
//        dp.setAddress(ip);
////        System.out.println("here");
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
//        System.out.println(dns_header2.toString());


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
