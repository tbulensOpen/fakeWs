package fakews

import javax.servlet.http.HttpServletRequest


class FakeWsProcessor {
    FakeWsRepository fakeWsRepository = new FakeWsRepository()

    String processGet(String key) {
        fakeWsRepository.find(key)
    }

    void processPost(String key, String value) {
      fakeWsRepository.update(key, value)
    }
}
