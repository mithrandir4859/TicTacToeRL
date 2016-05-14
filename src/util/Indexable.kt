package util

interface Indexable<T> {
    operator fun get(i: Int): T
}
