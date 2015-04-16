class UrlMatcher {

    UrlMapping findMatch(String url, List<UrlMapping> urlMappings) {

        UrlMapping foundMatch
        urlMappings.each { urlMapping ->
            if (!foundMatch && isMatch(url, urlMapping.url)) {
                foundMatch = urlMapping
            }
            if (!foundMatch && urlMapping.url.contains("*")) {
                int urlPrefixLen = urlMapping.url.indexOf("*") - 1
                String urlPrefix = url.substring(0, urlPrefixLen)

                if (isMatch(urlPrefix, urlMapping.url.substring(0, urlPrefixLen))) {
                    foundMatch = urlMapping
                }
            }
        }
        foundMatch
    }

    private boolean isMatch(String url, String urlConfig) {
        urlConfig.equalsIgnoreCase(url)
    }
}
