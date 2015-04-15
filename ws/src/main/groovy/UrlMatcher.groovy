class UrlMatcher {

    UrlMapping findMatch(String url, List<UrlMapping> urlMappings) {
        exactMatch(url, urlMappings)

    }

    private UrlMapping exactMatch(String url, List<UrlMapping> urlMappings) {
        List<UrlMapping> found = urlMappings.findAll { url.contains(it.url)}
        found ? found[0] : null
    }
}
