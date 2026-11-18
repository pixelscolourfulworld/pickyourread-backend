package de.htw_berlin.pickyourread.api.openlib;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import de.htw_berlin.pickyourread.entities.ApiBook;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.MismatchedInputException;

public class ApiBookResponse {

    private ObjectMapper om = new ObjectMapper();

    public ApiBookResponse(ApiWork work, ResponseEntity<Object> re) {
        this.work = work;

        Map<String,Object> response = om.convertValue(re.getBody(), new TypeReference<Map<String,Object>> () {});

        List<String> req = List.of("links", "size", "entries");

        if(!response.keySet().containsAll(req)) {
            throw new IllegalArgumentException("The given ResponseEntity is not valid for the Type ApiResponseWork");
        }

        Map<String,String> links = om.convertValue(response.get("links"), new TypeReference<Map<String,String>>(){});

        linkSelf = om.convertValue(links.get("self"), String.class);
        linkWork = om.convertValue(links.get("work"), String.class);
        linkNext = om.convertValue(links.get("next"), String.class);
        
        size = response.get("size") == null ? 0 : om.convertValue(response.get("size"), int.class);

        entries = om.convertValue(response.get("entries"), new TypeReference<List<Map<String,Object>>>(){});
    }

    private String linkSelf;
    private String linkWork;
    private String linkNext;

    private int size;

    private ApiWork work;
    private List<Map<String,Object>> entries;

    /**
     * Returns List of Keys available for the Entry at index index
     * @param index
     * @return List<String> of the available keys
     */
    public Set<String> getKeys(int index) {
        return entries.get(index).keySet();
    }

    public List<Map<String,Object>> getEntries() {
        return entries;
    }

    public <T> T get(int index, String key, TypeReference<T> tr) {

        if(!getKeys(index).contains(key)) {
            System.out.println("No Value for Key: "+key+" found for Entry at index="+index);           
            return null;
        }
        
        try {
            return om.convertValue(entries.get(index).get(key), tr);
        } catch (MismatchedInputException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ApiBook makeBook(int index) {

        TypeReference<List<String>> listStringRef = new TypeReference<List<String>>(){};
        TypeReference<String> stringRef = new TypeReference<String>() {};
        TypeReference<Integer> intRef = new TypeReference<Integer>() {};

        String key = get(index, "key", stringRef).replace("/books/","");
        String title = get(index, "title", stringRef);

        List<String> isbn10List = get(index, "isbn_10", listStringRef);
        List<String> isbn13List = get(index, "isbn_13", listStringRef);
        List<String> publisherList = get (index, "publishers", listStringRef);
        List<String> coverList = get(index, "covers", listStringRef);

        String isbn10 = isbn10List != null ? isbn10List.get(0) : null;
        String isbn13 = isbn13List != null ? isbn13List.get(0) : null;
        String publisher = publisherList != null ? publisherList.get(0) : null;
        String cover = coverList != null ? coverList.get(0) : null;

        String releaseDateString = get(index, "publish_date", stringRef);
        LocalDate releaseDate;
        try {
            releaseDateString = releaseDateString.length() == 4 ? releaseDateString+"-12-31" : releaseDateString;
            releaseDateString = releaseDateString.length() != 10 && releaseDateString.length() != 8 ? null : releaseDateString;
            releaseDate = releaseDateString == null ? null : LocalDate.parse(releaseDateString);
        } catch (Exception e) {
            releaseDate = null;
            e.printStackTrace();
        }
        Integer pages = get(index, "number_of_pages", intRef);
        pages = pages == null ? 0 : pages;

        Map<String,String> descMap = get(index, "description", new TypeReference<Map<String,String>>() {});
        String desc = descMap != null ? descMap.get("value") : null;

        return new ApiBook(work, key, title, isbn10, isbn13, publisher, cover, null, null, releaseDate, null, pages, desc);
    }

    public List<ApiBook> makeApiBooks() {
        List<ApiBook> result = new ArrayList<ApiBook>();

        int i = 0;
        while( i < entries.size() ) {
            result.add(makeBook(i));
            i++;
        }
        return result;
    }

}


