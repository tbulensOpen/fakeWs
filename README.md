# fakeWs
Universal Fake Web Service with configurable behavior.

Description:
   Key value map and the key is dynamically generated based on configuration.
   Each url indicates what request parameters makes up a key, along with a context
   so that the same id can be used to retrieve different values per url.
   The value is a string ( I typically would pass in either xml or json ).

The first implementation is just supports a in-memory map.
I would like to convert the in-memory map to use REDIS.

Currently, I execute the fake web service using jetty: mvn jetty:run

Go to the browser: localhost:8080/setupFakeData?id=1&value={someKey: someData}
                   localhost:8080/hello?id=1

Fake Web Service takes two files within the classpath for configuration:
   Fakews-env.properties
   FakeWsConfig.yml

Fakews-env.properties controlls the behavior of the fake web service.
  urlMappingMissing=ignore  # Values: ignore, missingUrlException
  keyMissing=ignore         # Values: ignore, missingKeyException
  dataAlreadyExist=replace  # Post: Values: ignore, replace, writeAfterWriteException
  dataMissing=ignore        # Get: Values: ignore, missingDataException

FakeWsConfig.yml is responsible for each client defining which urls are coming in.
   Each url, then indicates with request parameters makes up the key, along which request parameter makes retrieves the data.

