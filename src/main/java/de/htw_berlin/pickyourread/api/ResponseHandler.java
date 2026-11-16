package de.htw_berlin.pickyourread.api;

// import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import de.htw_berlin.pickyourread.entities.ApiBook;
// import de.htw_berlin.pickyourread.enums.Format;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

/**@deprecated
 * ResponseHandler
 */
@Deprecated 
public class ResponseHandler {

    private ObjectMapper om = new ObjectMapper();

    /**@deprecated
     * Takes a {@code ResponseEntity} and converts it into a {@code Map<String, Object>}
     * @param re {@code ResponseEntity}
     * @return {@code Map<String, Object>}
     */
    @SuppressWarnings("unchecked") // TODO: Check Conversion
    @Deprecated 
    protected Map<String, Object> responseToMap(ResponseEntity<Object> re) {
        return om.convertValue(re.getBody(), HashMap.class);
    }


    /**@deprecated
     * Used to convert API Results into a list of {@code ApiBooks}
     * @param books
     * @return {@code List<ApiBooks>}
     */
    @Deprecated 
    protected List<ApiBook> toBookArray(Map<String, Object>[] books) {
        List<ApiBook> result = new ArrayList<ApiBook>();

        Stream.of(books).forEach(m -> result.add(new ApiBook(m)));

        return result;
    }
    
    @Deprecated 
    public List<ApiBook> toEditionList(Map<String,Object> map) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEditionList'");
    }

    @Deprecated 
    public List<ApiWork> toWorkList(ApiWorkResponse response) {
        List<ApiWork> result = new ArrayList<ApiWork>();

        List<Map<String,Object>> docs = response.getDocs();

        for (Map<String,Object> d:docs) {
            result.add(makeWork(d));
        }

        return result;
    }

    @Deprecated 
    public ApiWork makeWork(Map<String,Object> workMap) {

        TypeReference<List<String>> listStringRef = new TypeReference<List<String>>(){};

        String apiKey = getValue(workMap,"key", String.class).replace("/works/","");
        String title = getValue(workMap,"title", String.class);
        String description = getValue(workMap, "description", String.class);
        List<String> authorKey = getValue(workMap,"author_key", listStringRef);
        List<String> authorName = getValue(workMap,"author_name",listStringRef);
        String coverEditionKey = getValue(workMap,"cover_edition_key",String.class);
        int releaseYear = getValue(workMap,"first_publish_year",int.class);
        List<String> seriesKey = getValue(workMap,"series_key",listStringRef);
        List<String> seriesName = getValue(workMap,"series_name",listStringRef);
        List<String> seriesPos = getValue(workMap,"series_position",listStringRef);

        return new ApiWork(apiKey, title, description, authorKey, authorName, coverEditionKey, releaseYear, seriesKey, seriesName,
                seriesPos);
    }

    @Deprecated 
    private static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, values::get));
    }

    @Deprecated 
    private static <K, V> Map<K, Map<String, V>> zipToPropMap(List<K> keys, String prop1, List<V> values1, String prop2, List<V> values2) {
        List<Map<String, V>> props = new ArrayList<Map<String, V>>(0);
        
        Iterator<V> it1 = values1.iterator();
        Iterator<V> it2 = values2.iterator();

        while( it1.hasNext() && it2.hasNext()) {
            props.add(Map.of(prop1, it1.next(), prop2, it2.next()));
        }
        

        return zipToMap(keys, props);
    }

    private <T> T getValue(Map<String,Object> map, String key, Class<T> typeRef) {
        return om.convertValue(map.get(key), typeRef);
    }

    private <T> T getValue(Map<String,Object> map, String key, TypeReference<T> typeRef) {
        return om.convertValue(map.get(key), typeRef);
    }

}
