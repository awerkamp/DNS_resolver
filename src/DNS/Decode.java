package DNS;

import java.io.IOException;
import java.io.InputStream;
import java.security.spec.EncodedKeySpec;
import java.util.ArrayList;

// Program written using the following documentation: https://www.ietf.org/rfc/rfc1035.txt

abstract class Decode {

    ArrayList<Byte> decoded_input = new ArrayList<>();
    ArrayList<Byte> encoded_output = new ArrayList<>();
    abstract void decode(InputStream inputStream) throws IOException;
    abstract void encode();
}
