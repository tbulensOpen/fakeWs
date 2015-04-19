package fakews

import org.junit.Before
import org.junit.Test


class UrlMappingsBuilderTest {
    UrlMappingsBuilder builder

    @Before
    void setUp() {
        builder = new UrlMappingsBuilder()
    }

    @Test
    void build() {
        List<UrlMapping> urlMappings = builder.build("fakeWsConfig.yml")
        assert 3 == urlMappings.size()
        assert "http://localhost:8080/hello" ==  urlMappings[0].url
        assert ['id'] == urlMappings[0].requestParamerIds
        assert !urlMappings[0].valueKey
        assert "value" == urlMappings[2].valueKey
    }
}
