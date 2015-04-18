package fakews

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FakeWsServlet extends HttpServlet {
    static List<UrlMapping> urlMappings = []
    static Properties prop
    static UrlMatcher urlMatcher = new UrlMatcher()
    static KeyBuilder keyBuilder = new KeyBuilder()
    static FakeWsProcessor fakeWsProcessor = new FakeWsProcessor()

    void init() {
        urlMappings.addAll(new UrlMappingsBuilder().build("fakeWsConfig.yml"))
        prop = new PropertiesLoader().load("fakews-env.properties")
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String output = ""
        String url = request.getRequestURL().toString()

        if (!isFavicon(url)) {
            UrlMapping urlMapping = urlMatcher.findMatch(url, urlMappings)
            if (urlMapping) {
                output = process(urlMapping, request)
            } else {
                output = "No Url Match found."
            }
            writeResponse(output, response)
        }
    }

    private String process(UrlMapping urlMapping, HttpServletRequest request) {
        String key = keyBuilder.createKey(request, urlMapping.requestParamerIds)
        if (urlMapping.valueKey) {
            fakeWsProcessor.processPost(key, request.getParameter(urlMapping.valueKey))
            return "Hello World Post"
        } else {
            String data = fakeWsProcessor.processGet(key)
            return "Hello World Get"
        }
    }

    private void writeResponse(String output, HttpServletResponse response) {
        response.setContentType("text/plain")
        response.writer.write(output)
    }

    private boolean isFavicon(String url) {
        url.contains(".ico")
    }
}
