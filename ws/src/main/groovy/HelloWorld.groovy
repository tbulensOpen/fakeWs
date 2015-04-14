import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLParser
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HelloWorld extends HttpServlet {

    void init() {
//        InputStream inputStream = ClassLoader.getSystemResourceAsStream("fakeWsConfig.yml")
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("fakeWsConfig.yml");
        Yaml yaml = new Yaml();
        Map<String, List<String>> config = yaml.load(inputStream)

        List<String> urls = config['urlMappings']

        urls.each { record ->
            println record["url"]
            List<String> requestParameters = record["requestParameters"].split(",")
            println requestParameters
            new UrlMapping(url: record["url"], value: record["value"], requestParamers: requestParameters)
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        println "***** URL = " + request.getRequestURL().toString()
        response.setContentType("text/plain")
        response.writer.write("Hello World")
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
