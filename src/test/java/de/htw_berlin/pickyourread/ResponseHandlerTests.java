package de.htw_berlin.pickyourread;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import de.htw_berlin.pickyourread.api.ApiWorkResponse;
import de.htw_berlin.pickyourread.api.ApiBookResponse;
import de.htw_berlin.pickyourread.api.ApiWork;
import de.htw_berlin.pickyourread.entities.ApiBook;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
class ResponseHandlerTests {

        List<ApiWork> testWorks;
        List<ApiBook> testBooks;

        @BeforeEach
        void setup() {
                testWorks = List.of(
                        new ApiWork("OL16607146W",
                                        "Throne of Glass",
                                        "Lethal. Loyal. Legendary.\r\n\r\nEnter the world of Throne of Glass with the first book in the #1 bestselling series by Sarah J. Maas.\r\n\r\nIn a land without magic, an assassin is summoned to the castle. She has no love for the vicious king who rules from his throne of glass, but she has not come to kill him. She has come to win her freedom. If she defeats twenty-three murderers, thieves, and warriors in a competition, she will be released from prison to serve as the King’s Champion.\r\n\r\nHer name is Celaena Sardothien.\r\n\r\nThe Crown Prince will provoke her. The Captain of the Guard will protect her. And a princess from a faraway country will befriend her. But something rotten dwells in the castle, and it’s there to kill. When her competitors start dying mysteriously, one by one, Celaena’s fight for freedom becomes a fight for survival—and a desperate quest to root out the evil before it destroys her world.",
                                        Map.of("OL7115219A", "Sarah J. Maas"),
                                        "OL35606216M",
                                        2012,
                                        Map.of("OL334582L",
                                                Map.of("seriesName", "Throne of Glass", "seriesPos", "1"))),

                        new ApiWork("OL17546674W",
                                        "The Assassin’s Blade",
                                        "Celaena Sardothien owes her reputation to Arobynn Hamel. He gave her a home at the Assassins' Guild and taught her the skills she needed to survive.\r\n\r\nArobynn's enemies stretch far and wide - from Adarlan's rooftops and its filthy dens, to remote islands and hostile deserts. Celaena is duty-bound to hunt them down. But behind her assignments lies a dark truth that will seal her fate - and cut her heart in two forever...\r\n\r\nExplore the dark underworld of this kick-ass heroine and find out how the legend begins in the five page-turning prequel novellas to the Throne of Glass series.",
                                        Map.of("OL7115219A", "Sarah J. Maas"),
                                        null,
                                        2014,
                                        null),

                        new ApiWork("OL17718538W",
                                        "Queen of Shadows",
                                        "Everyone Celaena Sardothien loves has been taken from her. But she's at last returned to the empire-for vengeance, to rescue her once-glorious kingdom, and to confront the shadows of her past...\r\n\r\nShe has embraced her identity as Aelin Galathynius, Queen of Terrasen. But before she can reclaim her throne, she must fight.\r\n\r\nShe will fight for her cousin, a warrior prepared to die just to see her again. She will fight for her friend, a young man trapped in an unspeakable prison. And she will fight for her people, enslaved to a brutal king and awaiting their lost queen's triumphant return.\r\n\r\nCelaena's epic journey has captured the hearts and imaginations of millions across the globe. This fourth volume will hold readers rapt as Celaena's story builds to a passionate, agonizing crescendo that might just shatter her world.",
                                        Map.of("OL7115219A", "Sarah J. Maas"),
                                        "OL26319924M",
                                        2015,
                                        Map.of("OL334582L",
                                                        Map.of("seriesName", "Throne of Glass", "seriesPos", "4"))),

                        new ApiWork("OL19914740W",
                                        "The Assassin's Blade / Throne of Glass / Crown of Midnight / Heir of Fire / Queen of Shadows / Empire of Storms / Tower of Dawn / Kingdom of Ash",
                                        "When magic has gone from the world and a vicious king rules from his throne of glass, an assassin is summoned to the castle. She is a prisoner, but if she can defeat twenty-three killers, thieves, and warriors in a competition, she will become the King's Champion and earn her freedom. But the evil she encounters in the castle goes deep, and as dark forces gather on the horizon-forces that threaten to destroy her entire world-the assassin must take her place in a fight greater than she could ever have imagined.",
                                        Map.of("OL7115219A", "Sarah J. Maas"),
                                        "OL27099336M",
                                        2018,
                                        null),

                        new ApiWork("OL17791167W",
                                        "Tower of Dawn",
                                        "Chaol Westfall has always defined himself by his unwavering loyalty, his strength, and his position as the captain of the Guard. But all of that has changed since the glass castle shattered, since his men were slaughtered, since the king of Adarlan spared him from a killing blow, but left his body broken.\r\n\r\nHis only shot at recovery lies with the legendary healers of the Torre Cesme in Antica - the stronghold of the southern continent's mighty empire. And with war looming over Dorian and Aelin back home, their survival might lie with Chaol and Nesryn convincing its rulers to ally with them.\r\n\r\nBut what they discover in Antica will change them both - and be more vital to saving Erilea than they could have imagined.",
                                        Map.of("OL7115219A", "Sarah J. Maas"),
                                        "OL26832224M",
                                        2017,
                                        Map.of("OL334582L",
                                                        Map.of("seriesName", "Throne of Glass", "seriesPos", "6"))),

                        new ApiWork("OL19352982W",
                                        "Kingdom of Ash",
                                        "Years in the making, Sarah J. Maas’s #1 New York Times bestselling Throne of Glass series draws to an epic, unforgettable conclusion. Aelin Galathynius’s journey from slave to king’s assassin to the queen of a once-great kingdom reaches its heart-rending finale as war erupts across her world. . .\r\n\r\nAelin has risked everything to save her people―but at a tremendous cost. Locked within an iron coffin by the Queen of the Fae, Aelin must draw upon her fiery will as she endures months of torture. Aware that yielding to Maeve will doom those she loves keeps her from breaking, though her resolve begins to unravel with each passing day…\r\n\r\nWith Aelin captured, Aedion and Lysandra remain the last line of defense to protect Terrasen from utter destruction. Yet they soon realize that the many allies they’ve gathered to battle Erawan’s hordes might not be enough to save them. Scattered across the continent and racing against time, Chaol, Manon, and Dorian are forced to forge their own paths to meet their fates. Hanging in the balance is any hope of salvation―and a better world.\r\n\r\nAnd across the sea, his companions unwavering beside him, Rowan hunts to find his captured wife and queen―before she is lost to him forever.\r\n\r\nAs the threads of fate weave together at last, all must fight, if they are to have a chance at a future. Some bonds will grow even deeper, while others will be severed forever in the explosive final chapter of the Throne of Glass series.",
                                        Map.of("OL7115219A", "Sarah J. Maas"),
                                        "OL26809543M",
                                        2018,
                                        Map.of("OL334582L",
                                                        Map.of("seriesName", "Throne of Glass", "seriesPos", "7"))),

                        new ApiWork("OL21126759W",
                                        "Throne of Glass Coloring Book",
                                        "For the first time, fans of Sarah J. Maas's sweeping New York Times bestselling Throne of Glass saga can experience the vivid imagery of her expansive world. Celaena battling the ridderak in the catacombs, Chaol in the gardens of the glass castle, Manon riding her wyvern through the Crossing, and many other favorite moments, characters, and objects come to life as readers explore the vibrantly detailed realm of Throne of Glass. Stunning original black-and-white drawings will bring fans deeper into the series than ever before-making this a must-have companion to Sarah J. Maas's beloved books.",
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
                                        "The long path to the throne has only just begun for Aelin Galathynius. Loyalties have been broken and bought, friends have been lost and gained, and those who possess magic find themselves at odds with those who don't.\r\n\r\nWith her heart sworn to the warrior-prince by her side, and her fealty pledged to the people she is determined to save, Aelin will delve into the depths of her power to protect those she loves. But as monsters emerge from the horrors of the past, and dark forces become poised to claim her world, the only chance for salvation will lie in a desperate quest that may mark the end of everything Aelin holds dear.\r\n\r\nIn this breathtaking fifth installment of the New York Times bestselling Throne of Glass series, Aelin will have to choose what -- and who -- to sacrifice if she's to keep the world of Erilea from breaking apart.",
                                        Map.of("OL7115219A", "Sarah J. Maas"),
                                        "OL43631860M",
                                        2016,
                                        Map.of("OL334582L",
                                                        Map.of("seriesName", "Throne of Glass", "seriesPos", "5"))),

                        new ApiWork("OL27308145W",
                                        "Throne of Glass / Crown of Midnight / Heir of Fire / Empire of Storms / Queen of Shadows",
                                        "Throne Of Glass Series Collection 5 Books Set By Sarah J. Maas Titles in the Set Throne of Glass, Crown of Midnight, Heir of Fire, Empire of Storms, Queen of Shadows",
                                        Map.of("OL7115219A", "Sarah J. Maas"),
                                        "OL37073549M",
                                        2019,
                                        null),

                        new ApiWork("OL21149879W",
                                        "World of Throne of Glass",
                                        null,
                                        Map.of("OL7115219A", "Sarah J. Maas", "OL7535140A", "Lynette Noni"),
                                        "OL28631809M",
                                        2018,
                                        null));
                testBooks = List.of(
                        new ApiBook(
                                testWorks.get(0),
                                "OL61827368M",
                                "Throne of glass",
                                null,
                                null,
                                "Bloomsbury",
                                null,
                                null,
                                null,
                                LocalDate.parse("2012-12-31"),
                                null,
                                406,
                                null),
                        new ApiBook(
                                testWorks.get(0),
                                "OL59254166M",
                                "Stakleni presto",
                                "8652124159",
                                "9788652124152",
                                "Laguna",
                                "15091963",
                                null,
                                null,
                                LocalDate.parse("2016-12-23"),
                                null,
                                448,
                                "Odsluživši zbog svojih zločina godinu dana teške robije u rudnicima soli u Endoviru, ubicu, osamnaestogodišnju Selenu Sardotijen, odvukli su pred princa prestolonaslednika. On joj je ponudio slobodu – ali pod jednim uslovom: mora da bude njegova predstavnica u nadmetanju za novog kraljevskog ubicu.\r\n\r\nNjeni takmaci su lopovi, ubice i ratnici iz svih krajeva carstva, a svako je predstavnik jednog člana kraljevskog saveta. Seleni je vežbanje s kapetanom Garde, Vestfalom, izazovno i uzbudljivo – ali život na dvoru joj je dosadan. Stvari postaju malo zanimljivije kada princ počne da se zanima za nju... ali čini se da je najbolje razume grubi kapetan Vestfal.\r\n\r\nA onda jednog od njenih takmaca nalaze mrtvog... a ubrzo potom i drugog. Može li Selena da otkrije ko je počinitelj pre nego što i ona postane žrtva? Potraga Selenu dovodi do otkrića da je čeka drugačija sudbina, onakva kakvu nikada nije mogla ni da zamisli."),
                        new ApiBook(
                                testWorks.get(0),
                                "OL57796276M",
                                "Trono de Vidro",
                                "9897541772",
                                "9789897541773",
                                "Marcador",
                                "14845027",
                                null,
                                null,
                                LocalDate.parse("2015-09-16"),
                                null,
                                400,
                                "Numa terra em que a magia foi banida e em que o rei governa com mão de ferro, uma assassina é chamada ao castelo. Ela vai, não para matar o rei, mas para conquistar a sua própria liberdade. Se derrotar os vinte e três oponentes em competição, será libertada da prisão para servir a Coroa com o estatuto de campeão do rei - o assassino do rei. O seu nome é Celaena Sardothien. O príncipe herdeiro vai provocá-la. O capitão da Guarda vai protegê-la. Mas um halo maléfico vagueia no castelo de vidro - e está lá para matar. Quando os seus concorrentes começam a morrer um a um, a luta de Celaena pela liberdade torna-se numa luta pela sobrevivência e numa jornada inesperada para expor um mal antes de que este destrua o seu mundo."),
                        new ApiBook(
                                testWorks.get(0),
                                "OL52200994M",
                                "Trono de Cristal",
                                null,
                                "9798890981547",
                                "Alfaguara",
                                "15102343",
                                null,
                                null,
                                LocalDate.parse("2024-12-31"),
                                null,
                                536,
                                "Un corazón de hielo. Una voluntad de acero. Te presentamos a una asesina. Te presentamos a Celaena Sardothien. Bella. Letal. Destinada a la grandeza.\r\n\r\nEn las tenebrosas minas de sal de Endovier, una muchacha de dieciocho años cumple cadena perpetua. Es una asesina profesional, la mejor en lo suyo, pero ha cometido un error fatal. La han capturado. El joven capitán Westfall le ofrece un trato: la libertad a cambio de un enorme sacrificio. Celaena debe representar al príncipe en un torneo a muerte, en el que deberá luchar con los asesinos y ladrones más peligrosos del reino. Viva o muerta, Celaena será libre. Tanto si gana como si pierde, está a punto de descubrir su verdadero destino. Pero,¿qué pasará entretanto con su corazón de asesina?"),
                        new ApiBook(
                                testWorks.get(0),
                                "OL41356476M",
                                	"Keleana. L'Assassineuse",
                                "2732456039",
                                "9782732456034",
                                "La Martini're Jeunesse",
                                "14836711",
                                null,
                                null,
                                null,
                                null,
                                0,
                                null)
                );
                        
        }

        @Test
        void handleWorkResponse() {

                File testFile = new File("src/test/res/togworkssearch.json");

                Map<String, Object> testMap = new ObjectMapper().readValue(testFile, new TypeReference<Map<String,Object>>(){});

                ResponseEntity<Object> testRE = new ResponseEntity<>(testMap, HttpStatusCode.valueOf(200));

                ApiWorkResponse testResponse = new ApiWorkResponse(testRE);

                System.out.println(testResponse.getDocs());
                List<ApiWork> actual = testResponse.makeApiWorks();

                Iterator<ApiWork> expIterator = testWorks.iterator();
                Iterator<ApiWork> actIterator = actual.iterator();

                while(expIterator.hasNext() && actIterator.hasNext()) {
                        ApiWork exp = expIterator.next();
                        ApiWork act = actIterator.next();

                        System.out.println(exp.equals(act));
                        assertEquals(exp, act);
                }
        }

        @Test
        void handleBookResponse() {

                File testFile = new File("src/test/res/togeditions.json");

                Map<String, Object> testMap = new ObjectMapper().readValue(testFile, new TypeReference<Map<String,Object>>(){});

                ResponseEntity<Object> testRE = new ResponseEntity<>(testMap, HttpStatusCode.valueOf(200));

                ApiBookResponse testResponse = new ApiBookResponse(testWorks.get(0), testRE);

                System.out.println(testResponse.getEntries());

                List<ApiBook> actual = testResponse.makeApiBooks();

                Iterator<ApiBook> expIterator = testBooks.iterator();
                Iterator<ApiBook> actIterator = actual.iterator();

                while(expIterator.hasNext() && actIterator.hasNext()) {
                        ApiBook exp = expIterator.next();
                        ApiBook act = actIterator.next();

                        exp.print();
                        act.print();

                        System.out.println(exp.equals(act));
                        assertEquals(exp, act);
                }
        }
}