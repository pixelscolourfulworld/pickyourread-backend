package de.htw_berlin.pickyourread.api;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import de.htw_berlin.pickyourread.entities.ApiBook;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

/**
 * ResponseHandler
 */
public class ResponseHandler {

    ObjectMapper om = new ObjectMapper();

    /**
     * Takes a {@code ResponseEntity} and converts it into a {@code Map<String, Object>}
     * @param re {@code ResponseEntity}
     * @return {@code Map<String, Object>}
     */
    @SuppressWarnings("unchecked") // TODO: Check Conversion
    protected Map<String, Object> responseToMap(ResponseEntity<Object> re) {
        return om.convertValue(re.getBody(), HashMap.class);
    }


    /**
     * Used to convert API Results into a list of {@code ApiBooks}
     * @param books
     * @return {@code List<ApiBooks>}
     */
    protected List<ApiBook> toBookArray(Map<String, Object>[] books) {
        List<ApiBook> result = new ArrayList<ApiBook>();

        Stream.of(books).forEach(m -> result.add(new ApiBook(m)));

        return result;
    }
    
    public List<ApiBook> toEditionList(Map<String,Object> map) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEditionList'");
    }


    public List<ApiWork> toWorkList(Map<String,Object> map) {
        List<ApiWork> result = new ArrayList<ApiWork>();

        List<Object> docs = om.convertValue(map.get("docs"), new TypeReference<List<Object>>(){});
        
        TypeReference<List<String>> listStringRef = new TypeReference<List<String>>(){};

        for (Object d:docs) {



            Map<String,Object> workMap = om.convertValue(d, new TypeReference<Map<String,Object>>(){});

            String apiKey = om.convertValue(workMap.get("key"), String.class).replace("/works/","");
            String title = om.convertValue(workMap.get("title"), String.class);
            List<String> authorKey = om.convertValue(workMap.get("author_key"), listStringRef);
            List<String> authorName = om.convertValue(workMap.get("author_name"), listStringRef);
            String coverEditionKey = om.convertValue(workMap.get("cover_edition_key"), String.class);
            int releaseYear = om.convertValue(workMap.get("first_publish_year"), int.class);
            List<String> seriesKey = om.convertValue(workMap.get("series_key"), listStringRef);
            List<String> seriesName = om.convertValue(workMap.get("series_name"), listStringRef);
            List<String> seriesPos = om.convertValue(workMap.get("series_position"), listStringRef);


            Map<String,String> author = zipToMap(authorKey, authorName);
            Map<String,Map<String,String>> series;
            
            if(seriesKey == null || seriesName == null || seriesPos == null) {
                series = null;
            } else {
                series = zipToPropMap(seriesKey, "seriesName", seriesName,"seriesPos", seriesPos);
            }
        
            result.add(new ApiWork(apiKey, title, author, coverEditionKey, releaseYear, series));
        
        }

        return result;
    }

    private static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, values::get));
    }

    private static <K, V> Map<K, Map<String, V>> zipToPropMap(List<K> keys, String prop1, List<V> values1, String prop2, List<V> values2) {
        List<Map<String, V>> props = new ArrayList<Map<String, V>>(0);
        
        Iterator<V> it1 = values1.iterator();
        Iterator<V> it2 = values2.iterator();

        while( it1.hasNext() && it2.hasNext()) {
            props.add(Map.of(prop1, it1.next(), prop2, it2.next()));
        }
        

        return zipToMap(keys, props);
    }

}
