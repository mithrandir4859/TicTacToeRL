package reinforcement_learning

import util.AverageKeeper

open class StateValue<ACTION>(val state: State<ACTION>) {
    protected val immediateReward = AverageKeeper()
    open val stateValue: Double
        get() = immediateReward.average;
}
