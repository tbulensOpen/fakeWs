package fakews

import org.apache.log4j.spi.LoggerFactory
import org.slf4j.Logger
import javax.servlet.http.HttpServletRequest


class FakeWsProcessor {
    FakeWsRepository fakeWsRepository = new FakeWsRepository()
    Logger logger = org.slf4j.LoggerFactory.getLogger(this.class)

    String processGet(String key) {
        String data = fakeWsRepository.find(key)

        if (!data) {
            logger.error("Data missing for ${key}")
            if (System.getProperty("dataMissing").equalsIgnoreCase('missingDataException')) {
                throw new RuntimeException("Data missing for ${key}")
            }
            data = ""
        }
        data
    }

    void processPost(String key, String value) {
        fakeWsRepository.update(key, value)
    }
}
