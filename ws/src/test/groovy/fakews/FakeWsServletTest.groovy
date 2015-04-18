package fakews

import fakews.FakeWsServlet
import org.gmock.WithGMock
import org.junit.Before
import org.junit.Test

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WithGMock
class FakeWsServletTest {
    FakeWsServlet fakeWsServlet
    HttpServletRequest mockRequest
    HttpServletResponse mockResponse
    UrlMatcher mockUrlMatcher
    KeyBuilder mockKeyBuilder
    FakeWsProcessor mockFakeWsProcessor
    PrintWriter mockPrintWriter

    @Before
    void setup() {
        mockRequest = mock(HttpServletRequest)
        mockResponse = mock(HttpServletResponse)
        mockUrlMatcher = mock(UrlMatcher)
        mockKeyBuilder = mock(KeyBuilder)
        mockFakeWsProcessor = mock(FakeWsProcessor)
        mockPrintWriter = mock(PrintWriter)

        fakeWsServlet = new FakeWsServlet()
        fakeWsServlet.urlMappings = []
        fakeWsServlet.urlMatcher = mockUrlMatcher
        fakeWsServlet.keyBuilder = mockKeyBuilder
        fakeWsServlet.fakeWsProcessor = mockFakeWsProcessor
    }


    @Test
    void init_LoadProperties_LoadConfiguration() {
        fakeWsServlet.init()

        assert "ignore" == System.getProperty("urlMappingMissing")
        assert "ignore" == System.getProperty("keyMissing")
        assert "replace" == System.getProperty("dataAlreadyExist")
        assert "ignore" == System.getProperty("findData")
    }

    @Test
    void doGet_returnData_byUrl() {
        String url = new StringBuffer("someUrl")
        String key = "someKey"
        String data = "{value: someData}"

        UrlMapping urlMapping = new UrlMapping(requestParamerIds: [key])

        mockRequest.getRequestURL().returns(url)
        mockUrlMatcher.findMatch(url, []).returns(urlMapping)
        mockKeyBuilder.createKey(mockRequest, [key]).returns(key)
        mockFakeWsProcessor.processGet(key).returns(data)
        mockResponse.writer.returns(mockPrintWriter)
        mockPrintWriter.write(data)

        mockResponse.setContentType("text/plain")

        play {
            fakeWsServlet.doGet(mockRequest, mockResponse)
        }

    }
}
