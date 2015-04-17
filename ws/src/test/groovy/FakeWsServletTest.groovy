import org.junit.Test

class FakeWsServletTest {

    @Test
    void dummy() {
        FakeWsServlet fakeWsServlet = new FakeWsServlet()
        fakeWsServlet.init()
        assert true
    }
}
