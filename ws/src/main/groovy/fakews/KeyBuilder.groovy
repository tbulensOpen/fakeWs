package fakews

import javax.servlet.http.HttpServletRequest

class KeyBuilder {

    String createKey(HttpServletRequest request, List<String> keyIds) {

        String key = ''
        keyIds.each { it ->
            String tempKey = it
            if (it.contains('(')) {
                tempKey = tempKey.replace('(', '').replace(')', '')
            }
            else {
                tempKey = request.getParameter(it)
            }
            key = key + tempKey
        }
        key
    }
}
