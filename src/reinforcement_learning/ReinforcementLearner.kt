package reinforcement_learning

import util.HashMapWithDefaultValue
import java.util.concurrent.ThreadLocalRandom

class ReinforcementLearner<ACTION, STATE : State<ACTION>> {

    private val stateValues = HashMapWithDefaultValue({ state: STATE -> NonTerminateStateValue(state)})
    private var lastActionValue: ActionValue<ACTION>? = null

    fun getAction(state: STATE, reward: Double = .0): ACTION {
        val stateValue = stateValues[state]
        stateValue.update(reward)
        if (lastActionValue != null) {
            lastActionValue!!.update(stateValue)
        }
        val action = if (ThreadLocalRandom.current().nextDouble() > .9)
            stateValue.getExploratoryAction() else stateValue.bestAction.action
        lastActionValue = stateValue[action]
        return action
    }

}
