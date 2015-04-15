import org.junit.Before
import org.junit.Test

class UrlMatcherTest {
    UrlMatcher urlMatcher
    List<UrlMapping> urlMappings = []

    @Before
    void setUp() {
        urlMatcher = new UrlMatcher()
        urlMappings << new UrlMapping(url: "http://localhost:8080/hello")
    }

    @Test
    void findMatch_exactMatch() {
        UrlMapping urlMapping = urlMatcher.findMatch("http://localhost:8080/hello", urlMappings)
        assert urlMapping
    }

    @Test
    void findMatch_match_withRequestParameters() {
        UrlMapping urlMapping = urlMatcher.findMatch("http://localhost:8080/hello?id=world", urlMappings)
        assert urlMapping
    }
}
