class PropertiesLoader {

    Properties load(String fileName) {
        Properties prop = new Properties()
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName)
        prop.load(inputStream)
        prop
    }
}
