package DNS;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class DNS_Cache {

//    This class is the local cache. It should basically just have a HashMap<DNSQuestion,
//    DNSRecord> in it. You can just store the first answer for any question in the cache
//    (a response for google.com might return 10 IP addresses, just store the first one).
//    This class should have methods for querying and inserting records into the cache.
//    When you look up an entry, if it is too old (its TTL has expired), remove it and return "not found."


    static HashMap<DNS_Question, DNS_Record> cache = new HashMap<DNS_Question, DNS_Record>();



    static DNS_Record get_DNS_record(String domain_name) throws Exception {
        for (DNS_Question i: cache.keySet()) {
            if (i.domain_name.equals(domain_name)) {
                return cache.get(i);
            }
        }
        throw new Exception("IP Address Not Found");
    }

    static boolean has_domain_name(String domain_name) throws Exception {
        for (DNS_Question i: cache.keySet()) {
            if (i.domain_name.equals(domain_name)) {
                return true;
            }
        }
        return false;
    }


    static void add_entry(DNS_Question dns_question, DNS_Record dns_record) {
        cache.put(dns_question, dns_record);
    }

//        if (cache.containsKey(domain_name)) {
//            return cache.get(domain_name);  // Todo Update this to remove cache if it's too old, remove it, and return "not found"
//        } else {
//            return cache.get(domain_name);    // Todo Update this to call google, update the cache, and return response
//        }
//    }
}
