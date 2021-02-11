package DNS;

import java.util.HashMap;

public class DNS_Cache {

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
}
