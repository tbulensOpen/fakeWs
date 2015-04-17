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

    @Before
    void setUp() {
        mockRequest = mock(HttpServletRequest)
        keyBuilder = new KeyBuilder()
    }

    @Test
    void createKey_OneKey() {
        mockRequest.getParameter("id").returns("keyId")

        play {
            assert "keyId" == keyBuilder.createKey(mockRequest, ["id"])
        }
    }

    @Test
    void createKey_TwoKeys() {
        mockRequest.getParameter("id1").returns("keyId1")
        mockRequest.getParameter("id2").returns("keyId2")

        play {
            assert "keyId1keyId2" == keyBuilder.createKey(mockRequest, ["id1","id2"])
        }
    }

    @Test
    void createKey_LitteralText() {
        mockRequest.getParameter("id1").returns("keyId1")
        mockRequest.getParameter("id2").returns("keyId2")

        play {
            assert "keyId1-keyId2" == keyBuilder.createKey(mockRequest, ["id1","(-)","id2"])
        }
    }
}
