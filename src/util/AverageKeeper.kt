package util

class AverageKeeper {

    val counter: Int
        get() = _counter

    val average: Double
        get() = _average

    private var _average: Double = 0.0

    private var _counter: Int = 0

    fun add(newValue: Double){
        val newSum = average * counter + newValue
        _average = newSum / ++_counter
    }

}
