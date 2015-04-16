import org.junit.Before
import org.junit.Test

class UrlMatcherTest {
    UrlMatcher urlMatcher
    List<UrlMapping> urlMappings = []

    @Before
    void setUp() {
        urlMatcher = new UrlMatcher()
        urlMappings << new UrlMapping(url: "http://localhost:8080/hello")
        urlMappings << new UrlMapping(url: "http://localhost:8080/*")
    }

    @Test
    void findMatch_exactMatch() {
        UrlMapping urlMapping = urlMatcher.findMatch("http://localhost:8080/HellO", urlMappings)
        assert urlMapping == urlMappings[0]
    }

    @Test
    void findMatch_match_WildCardMatch() {
        UrlMapping urlMapping = urlMatcher.findMatch("http://localhost:8080/AnyValue", urlMappings)
        assert urlMapping == urlMappings[1]
    }
}
