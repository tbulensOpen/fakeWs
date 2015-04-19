package fakews

import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml

class UrlMappingsBuilder {

    List<UrlMapping> build(String configFileName) {
        List<UrlMapping> urlMappings = []
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configFileName);
        Yaml yaml = new Yaml();
        Map<String, List<String>> config = yaml.load(inputStream)

        List<String> urls = config['urlMappings']

        urls.each { record ->
            List<String> keys = record["requestParameters"].split(",")
            urlMappings << new UrlMapping(url: record["url"], valueKey: record["value"], requestParamerIds: handleLiterals(keys))
        }
        urlMappings
    }

    private List<String> handleLiterals(List<String> requestParameters) {
        List<String> keys = []

        requestParameters.each { it ->
          keys << it.replace("(", "").replace(")", "")
        }
        keys
    }
}
