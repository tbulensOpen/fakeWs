import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLParser
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HelloWorld extends HttpServlet {
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //			response.setContentType("text/html;charset=UTF-8");
        //
        //	        try (PrintWriter out = response.getWriter()) {
        //	            out.println("<!DOCTYPE html>");
        //	            out.println("<html>");
        //	            out.println("<head>");
        //	            out.println("<title>Servlet HelloWorldDemo</title>");
        //	            out.println("</head>");
        //	            out.println("<body>");
        //	            out.println("<h1>Servlet HelloWorld at " + request.getContextPath() + "</h1>");
        //	            out.println("</body>");
        //	            out.println("</html>");
        //	        }
    }
}
