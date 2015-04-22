package fakews

import javax.servlet.http.HttpServletRequest

class KeyBuilder {

    List<String> createKey(HttpServletRequest request, UrlMapping urlMapping) {

        List<String> contexts = urlMapping.context.split(",")
        String key = ""
        List<String> keys = []

        contexts.each { context ->
            key = context
            urlMapping.requestParamerIds.each { it ->
                key = key + request.getParameter(it)
            }
            keys.add(key)
        }

        keys
    }
}
