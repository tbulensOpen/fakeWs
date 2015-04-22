package fakews

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FakeWsServlet extends HttpServlet {
    static List<UrlMapping> urlMappings = []
    static UrlMatcher urlMatcher = new UrlMatcher()
    static KeyBuilder keyBuilder = new KeyBuilder()
    static FakeWsProcessor fakeWsProcessor = new FakeWsProcessor()
    static final int BAD_REQUEST = 500
    Logger logger = LoggerFactory.getLogger(this.class)

    void init() {
        urlMappings.addAll(new UrlMappingsBuilder().build("fakeWsConfig.yml"))
        Properties prop = new PropertiesLoader().load("fakeWs-env.properties")
        System.setProperties(prop)
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = request.getRequestURL().toString()

        if (!isFavicon(url)) {
            try {
                UrlMapping urlMapping = urlMatcher.findMatch(url, urlMappings)

                String output = (urlMapping) ? process(url, urlMapping, request, response) : missingUrl(response, url)
                writeResponse(output, response)
            } catch (Exception ex) {
                response.setStatus(BAD_REQUEST)
                writeResponse(ex.getMessage(), response)
            }
        }
    }

    private String process(String url, UrlMapping urlMapping, HttpServletRequest request, HttpServletResponse response) {
        List<String> keys = keyBuilder.createKey(request, urlMapping)

        if (!keys) {
            return missingKey(url, urlMapping, response)
        }

        String data = ""
        if (urlMapping.valueKey) {
            keys.each { key ->
                fakeWsProcessor.processPost(key, request.getParameter(urlMapping.valueKey))
            }
            data = "Post Successful -- key = " + keys.join(",")
        } else {
            data = fakeWsProcessor.processGet(keys[0])
        }
        data
    }

    private String missingKey(String url, UrlMapping urlMapping, HttpServletResponse response) {
        if (System.getProperty("keyMissing").equalsIgnoreCase('missingKeyException')) {
            response.setStatus(BAD_REQUEST)
        }
        logMessage("${urlMapping.requestParamerIds} for ${url} -- request parameters does not exist for this url.")
    }

    private String missingUrl(HttpServletResponse response, String url) {
        if (System.getProperty('urlMappingMissing').equalsIgnoreCase("missingUrlException")) {
            response.setStatus(BAD_REQUEST)
        }
        logMessage("${url} -- No Url Match found.")
    }

    private String logMessage(String msg) {
        logger.error(msg)
        msg
    }

    private void writeResponse(String output, HttpServletResponse response) {
        response.setContentType("text/plain")
        response.writer.write(output)
    }

    private boolean isFavicon(String url) {
        url.contains(".ico")
    }
}
