package fakews

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FakeWsServlet extends HttpServlet {
    static List<UrlMapping> urlMappings
    static UrlMatcher urlMatcher = new UrlMatcher()
    static KeyBuilder keyBuilder = new KeyBuilder()
    static FakeWsProcessor fakeWsProcessor = new FakeWsProcessor()
    static final int BAD_REQUEST = 500
    Logger logger = LoggerFactory.getLogger(this.class)


    void init() {
        urlMappings.addAll(new UrlMappingsBuilder().build("fakeWsConfig.yml"))
        Properties prop = new PropertiesLoader().load("fakews-env.properties")
        System.setProperties(prop)
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
                response.setStatus(BAD_REQUEST)
                output = "${url} -- No Url Match found."
                logger.error(output)
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
            return fakeWsProcessor.processGet(key)
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
