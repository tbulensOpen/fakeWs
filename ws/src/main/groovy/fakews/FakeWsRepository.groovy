package fakews


class FakeWsRepository {
    static data = [:]

    void clear() {
        data.clear()
    }

    void update(String key, String value) {
        data.putAt(key, value)
    }

    String find(String key) {
        data[key]
    }

}
