package fakews

import javax.servlet.http.HttpServletRequest

class KeyBuilder {

    String createKey(HttpServletRequest request, UrlMapping urlMapping) {

        String key = urlMapping.context
        urlMapping.requestParamerIds.each { it ->
            key = key + request.getParameter(it)
        }
        key
    }
}
