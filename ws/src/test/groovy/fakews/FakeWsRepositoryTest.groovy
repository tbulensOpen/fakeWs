package fakews

import org.junit.Before
import org.junit.Test


class FakeWsRepositoryTest {
    public static final String KEY = "a"
    public static final String VALUE = "someValue"
    FakeWsRepository fakeWsRepository

    @Before
    void setUp() {
       fakeWsRepository = new FakeWsRepository()
    }

    @Test
    void clear() {
       fakeWsRepository.update(KEY, VALUE)
       fakeWsRepository.clear()
       assert null == fakeWsRepository.find(KEY)
    }

    @Test
    void update() {
        fakeWsRepository.update(KEY, VALUE)
        assert VALUE == fakeWsRepository.find(KEY)
    }

    @Test
    void find() {
        fakeWsRepository.update(KEY, VALUE)
        assert VALUE == fakeWsRepository.find(KEY)
    }
}
