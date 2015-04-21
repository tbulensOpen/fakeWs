package fakews

import org.gmock.WithGMock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger

import static org.junit.Assert.*

@WithGMock
class FakeWsProcessorTest {
    static final String KEY = "key"
    static final String DATA = "someData"
    FakeWsProcessor fakeWsProcessor
    FakeWsRepository mockFakeWsRepository
    Logger mockLogger

    @Before
    void setUp() {
        mockFakeWsRepository = mock(FakeWsRepository)
        mockLogger = mock(Logger)

        fakeWsProcessor = new FakeWsProcessor()
        fakeWsProcessor.fakeWsRepository = mockFakeWsRepository
        fakeWsProcessor.logger = mockLogger
    }

    @Test
    void processGet_DataFound() {
        mockFakeWsRepository.find(KEY).returns(DATA)

        play {
            assert DATA == fakeWsProcessor.processGet(KEY)
        }
    }

    @Test
    void processGet_DataNotFound_Ignore() {
        System.setProperty("dataMissing", "ignore")

        mockFakeWsRepository.find(KEY).returns(null)
        mockLogger.error("Data missing for ${KEY}")

        play {
            assert !fakeWsProcessor.processGet(KEY)
        }
    }

    @Test
    void processGet_DataNotFound_MissingDataException() {
        System.setProperty("dataMissing", "missingDataException")

        mockFakeWsRepository.find(KEY).returns(null)
        mockLogger.error("Data missing for ${KEY}")

        play {
            try {
                fakeWsProcessor.processGet(KEY)
                fail("An exception should of been thrown")
            } catch (Exception ex) {
                assert "Data missing for ${KEY}" == ex.getMessage()
            }
        }
    }

    @Test
    void processPost_Successful() {
        mockFakeWsRepository.find(KEY).returns(null)
        mockFakeWsRepository.update(KEY, DATA)

        play {
            fakeWsProcessor.processPost(KEY, DATA)
        }
    }

    @Test
    void processPost_DataExists_Ignore() {
        System.setProperty("dataAlreadyExist", "ignore")

        mockFakeWsRepository.find(KEY).returns(DATA)
        mockLogger.debug("Data already exists for ${KEY} - update ignored.")

        play {
            fakeWsProcessor.processPost(KEY, DATA)
        }
    }

    @Test
    void processPost_Replaced() {
        System.setProperty("dataAlreadyExist", "replace")

        mockFakeWsRepository.find(KEY).returns(DATA)
        mockFakeWsRepository.update(KEY, DATA)
        mockLogger.debug("Data already exists for ${KEY} - update replaced existing data.")

        play {
            fakeWsProcessor.processPost(KEY, DATA)
        }
    }

    @Test
    void processPost_writeAfterWriteException() {
        System.setProperty("dataAlreadyExist", "writeAfterWriteException")

        mockFakeWsRepository.find(KEY).returns(DATA)
        mockLogger.error("Data already exists for ${KEY} - update failed.")

        play {
            try {
                fakeWsProcessor.processPost(KEY, DATA)
                fail("Exception should of been thrown.")
            } catch (Exception ex) {
                assert "Data already exists for ${KEY} - update failed." == ex.getMessage()

            }
        }
    }
}
