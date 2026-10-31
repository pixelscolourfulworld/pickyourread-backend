package de.htw_berlin.pickyourread;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.htw_berlin.pickyourread.api.ApiWork;
import de.htw_berlin.pickyourread.api.ResponseHandler;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
class ResponseHandlerTests {

        @Test
        void handleEditionResponse() {
                List<ApiWork> expected = List.of(
                                new ApiWork("OL16607146W",
                                                "Throne of Glass",
                                                Map.of("OL7115219A", "Sarah J. Maas"),
                                                "OL35606216M",
                                                2012,
                                                Map.of("OL334582L",
                                                                Map.of("seriesName", "Throne of Glass", "seriesPos",
                                                                                "1"))),

                                new ApiWork("OL17546674W",
                                                "The Assassin’s Blade",
                                                Map.of("OL7115219A", "Sarah J. Maas"),
                                                null,
                                                2014,
                                                null),

                                new ApiWork("OL17718538W",
                                                "Queen of Shadows",
                                                Map.of("OL7115219A", "Sarah J. Maas"),
                                                "OL26319924M",
                                                2015,
                                                Map.of("OL334582L",
                                                                Map.of("seriesName", "Throne of Glass", "seriesPos",
                                                                                "4"))),

                                new ApiWork("OL19914740W",
                                                "The Assassin's Blade / Throne of Glass / Crown of Midnight / Heir of Fire / Queen of Shadows / Empire of Storms / Tower of Dawn / Kingdom of Ash",
                                                Map.of("OL7115219A", "Sarah J. Maas"),
                                                "OL27099336M",
                                                2018,
                                                null),

                                new ApiWork("OL17791167W",
                                                "Tower of Dawn",
                                                Map.of("OL7115219A", "Sarah J. Maas"),
                                                "OL26832224M",
                                                2017,
                                                Map.of("OL334582L",
                                                                Map.of("seriesName", "Throne of Glass", "seriesPos",
                                                                                "6"))),

                                new ApiWork("OL19352982W",
                                                "Kingdom of Ash",
                                                Map.of("OL7115219A", "Sarah J. Maas"),
                                                "OL26809543M",
                                                2018,
                                                Map.of("OL334582L",
                                                                Map.of("seriesName", "Throne of Glass", "seriesPos",
                                                                                "7"))),

                                new ApiWork("OL21126759W",
                                                "Throne of Glass Coloring Book",
                                                Map.of("OL7115219A", "Sarah J. Maas",
                                                                "OL4429603A", "Yvonne Gilbert",
                                                                "OL1755865A", "John Howe",
                                                                "OL5498075A", "Craig Phillips",
                                                                "OL2864593A", "Jon Proctor",
                                                                "OL9635965A", "Terauchi Joji",
                                                                "OL10584622A", "Ananias Creon"),
                                                "OL39385828M",
                                                2016,
                                                null),

                                new ApiWork("OL17625829W",
                                                "Empire of Storms",
                                                Map.of("OL7115219A", "Sarah J. Maas"),
                                                "OL43631860M",
                                                2016,
                                                Map.of("OL334582L",
                                                                Map.of("seriesName", "Throne of Glass", "seriesPos",
                                                                                "5"))),

                                new ApiWork("OL27308145W",
                                                "Throne of Glass / Crown of Midnight / Heir of Fire / Empire of Storms / Queen of Shadows",
                                                Map.of("OL7115219A", "Sarah J. Maas"),
                                                "OL37073549M",
                                                2019,
                                                null),

                                new ApiWork("OL21149879W",
                                                "World of Throne of Glass",
                                                Map.of("OL7115219A", "Sarah J. Maas", "OL7535140A", "Lynette Noni"),
                                                "OL28631809M",
                                                2018,
                                                null));

                ResponseHandler handler = new ResponseHandler();

                File testFile = new File("src/test/res/togworkssearch.json");

                Map<String, Object> testMap = new ObjectMapper().readValue(testFile, new TypeReference<Map<String,Object>>(){});

                
                List<ApiWork> actual = handler.toWorkList(testMap);

                Iterator<ApiWork> expIterator = expected.iterator();
                Iterator<ApiWork> actIterator = actual.iterator();

                while(expIterator.hasNext() && actIterator.hasNext()) {
                        ApiWork exp = expIterator.next();
                        ApiWork act = actIterator.next();

                        System.out.println(exp.equals(act));
                        assertEquals(exp, act);
                }
        }
}