package fakews

import org.slf4j.Logger
import org.slf4j.LoggerFactory


class FakeWsRepository {
    Logger logger = LoggerFactory.getLogger(this.class)
    static data = [:]

    void clear() {
        data.clear()
    }

    void update(String key, String value) {
        logger.debug("Post: key = " + key + "  value = " + value)
        data.put(key, value)
    }

    String find(String key) {
        data[key]
    }

}
