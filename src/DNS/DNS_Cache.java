package DNS;

import java.util.HashMap;

public class DNS_Cache {

//    This class is the local cache. It should basically just have a HashMap<DNSQuestion,
//    DNSRecord> in it. You can just store the first answer for any question in the cache
//    (a response for google.com might return 10 IP addresses, just store the first one).
//    This class should have methods for querying and inserting records into the cache.
//    When you look up an entry, if it is too old (its TTL has expired), remove it and return "not found."



//
    static HashMap<DNS_Question, DNS_Record> cache;

    static DNS_Record query_cache(DNS_Question domain_name) {
        if (cache.containsKey(domain_name)) {
            return cache.get(domain_name);  // Todo Update this to remove cache if it's too old, remove it, and return "not found"
        } else {
            return cache.get(domain_name);    // Todo Update this to call google, update the cache, and return response
        }
    }




}
