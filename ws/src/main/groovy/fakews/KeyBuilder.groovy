package fakews

import javax.servlet.http.HttpServletRequest

class KeyBuilder {

    String createKey(HttpServletRequest request, List<String> keyIds) {

        String key = ''
        keyIds.each { it ->
            key = key + request.getParameter(it)
        }
        key
    }
}
