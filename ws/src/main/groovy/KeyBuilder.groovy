import javax.servlet.http.HttpServletRequest

class KeyBuilder {

    String createKey(HttpServletRequest request, String configRequestParameters) {
        String[] keyId = configRequestParameters.split(",")

        String key = ''
        keyId.each { it ->
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
