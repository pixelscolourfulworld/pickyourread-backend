package de.htw_berlin.pickyourread.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Cacheable 
public class ApiWorkResponse {

    private int numFound;
    private int start;
    private boolean numFoundExact;
    private String q;
    private int offset;

    // 0 : Doc1<Keys,Values> : List<Available Keys> 
    private List<Map<String,Object>> docs;

    public int getStart() {
        return start;
    }

    public boolean isNumFoundExact() {
        return numFoundExact;
    }

    public String getQ() {
        return q;
    }

    public int getOffset() {
        return offset;
    }

    public List<Map<String, Object>> getDocs() {
        return docs;
    }

    private ObjectMapper om = new ObjectMapper();
    

    public ApiWorkResponse(ResponseEntity<Object> re) {
        Map<String,Object> response = om.convertValue(re.getBody(), new TypeReference<Map<String,Object>> () {});

        List<String> req = List.of("numFound","start","numFoundExact","q","offset","docs");

        if(!response.keySet().containsAll(req)) {
            throw new IllegalArgumentException("The given ResponseEntity is not valid for the Type ApiResponseWork");
        }

        numFound = om.convertValue(response.get("numFound"), int.class);
        start = om.convertValue(response.get("start"), int.class);
        numFoundExact = om.convertValue(response.get("numFoundExact"), boolean.class);
        q = om.convertValue(response.get("q"), String.class);
        offset = response.get("offset") == null ? 0 : om.convertValue(response.get("offset"), int.class);

        docs = om.convertValue(response.get("docs"), new TypeReference<List<Map<String,Object>>>(){});
    }

    /**
     * Returns List of Keys available for the Entry at index index
     * @param index
     * @return List<String> of the available keys
     */
    public Set<String> getKeys(int index) {
        return docs.get(index).keySet();
    }

    public <T> T get(int index, String key, TypeReference<T> tr) {

        if(!getKeys(index).contains(key)) {
            System.out.println("No Value for Key: "+key+" found for Entry at index="+index);           
            return null;
        }
        
        return om.convertValue(docs.get(index).get(key), tr);
        
    }

    public ApiWork makeWork(int index) {

        TypeReference<List<String>> listStringRef = new TypeReference<List<String>>(){};
        TypeReference<String> stringRef = new TypeReference<String>() {};
        TypeReference<Integer> intRef = new TypeReference<Integer>() {};

        String apiKey = get(index, "key", stringRef).replace("/works/","");
        String title = get(index ,"title", stringRef);
        String description = get(index, "description", stringRef);
        List<String> authorKey = get(index,"author_key", listStringRef);
        List<String> authorName = get(index ,"author_name",listStringRef);
        String coverEditionKey = get(index ,"cover_edition_key",stringRef);
        Integer releaseYear = get(index,"first_publish_year",intRef);
        releaseYear = releaseYear == null ? 0 : releaseYear;
        List<String> seriesKey = get(index,"series_key",listStringRef);
        List<String> seriesName = get(index,"series_name",listStringRef);
        List<String> seriesPos = get(index,"series_position",listStringRef);

        return new ApiWork(apiKey, title, description, authorKey, authorName, coverEditionKey, releaseYear, seriesKey, seriesName,
                seriesPos);
    }

    public List<ApiWork> makeApiWorks() {
        List<ApiWork> result = new ArrayList<ApiWork>();

        int i = 0;
        while( i < docs.size() ) {
            result.add(makeWork(i));
            i++;
        }
        return result;
    }

}
