import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FakeWsServlet extends HttpServlet {
    static List<UrlMapping> urlMappings = []
    static Properties prop
    static UrlMatcher urlMatcher = new UrlMatcher()

    void init() {
        urlMappings.addAll(new UrlMappingsBuilder().build("fakeWsConfig.yml"))
        prop = new PropertiesLoader().load("fakews-env.properties")
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = request.getRequestURL().toString()
        if (!url.contains(".ico")) {
            response.setContentType("text/plain")

            UrlMapping urlMapping = urlMatcher.findMatch(url, urlMappings)
            if (urlMapping) {
                response.writer.write("Hello World")
            } else {
                response.writer.write("No Url Match found.")
            }
        }
    }
}
