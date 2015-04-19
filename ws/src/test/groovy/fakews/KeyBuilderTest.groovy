package fakews

import fakews.KeyBuilder
import org.gmock.WithGMock
import org.junit.Before
import org.junit.Test
import javax.servlet.http.HttpServletRequest

@WithGMock
class KeyBuilderTest {
    KeyBuilder keyBuilder
    HttpServletRequest mockRequest
    UrlMapping urlMapping

    @Before
    void setUp() {
        mockRequest = mock(HttpServletRequest)
        keyBuilder = new KeyBuilder()
        urlMapping = new UrlMapping(context: "clientAppName", requestParamerIds: ['id'])
    }

    @Test
    void createKey_OneKey() {
        mockRequest.getParameter("id").returns("keyId")

        play {
            assert "clientAppNamekeyId" == keyBuilder.createKey(mockRequest, urlMapping)
        }
    }

    @Test
    void createKey_TwoKeys() {
        mockRequest.getParameter("id1").returns("keyId1")
        mockRequest.getParameter("id2").returns("keyId2")
        urlMapping.requestParamerIds = ["id1","id2"]

        play {
            assert "clientAppNamekeyId1keyId2" == keyBuilder.createKey(mockRequest, urlMapping)
        }
    }
}
