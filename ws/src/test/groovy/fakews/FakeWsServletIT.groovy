package fakews

import org.gmock.WithGMock
import org.junit.Test

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WithGMock
class FakeWsServletIT {

    @Test
    void realFakeWsServlet() {
        HttpServletRequest mockRequest = mock(HttpServletRequest)
        HttpServletResponse mockResponse = mock(HttpServletResponse)

        FakeWsServlet fakeWsServlet = new FakeWsServlet()
        fakeWsServlet.init()

        mockRequest.getRequestURL().returns("http://localhost:8080/hello")

        play {
            fakeWsServlet.doGet(mockRequest, mockResponse)
        }

    }
}
