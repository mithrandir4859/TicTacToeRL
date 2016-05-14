package util

import java.util.*

class HashMapWithDefaultValue<K, V>(val factory: (key: K) -> V) : HashMap<K, V>() {

    override operator fun get(key: K): V {
        var value = super.get(key)
        if (value == null) {
            value = factory(key)
            set(key, value)
        }
        return value!!
    }

}
