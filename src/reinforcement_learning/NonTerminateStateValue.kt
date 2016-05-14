package reinforcement_learning

import util.HashMapWithDefaultValue
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class NonTerminateStateValue<ACTION>(state: State<ACTION>) : StateValue<ACTION>(state) {

    private val actionValues = HashMapWithDefaultValue({ key: ACTION -> ActionValue(key) })

    override val stateValue: Double
        get() = immediateReward.average + bestAction.longtermReturn.average

    val bestAction: ActionValue<ACTION>
        get() = _bestAction

    private var _bestAction: ActionValue<ACTION> = actionValues[state.actions.iterator().next()]

    fun getExploratoryAction(): ACTION {
        val suboptimalActions = HashSet(state.actions)
        suboptimalActions.remove(bestAction.action)
        val randomIndex = ThreadLocalRandom.current().nextInt(suboptimalActions.size)
        return suboptimalActions.toList()[randomIndex]
    }

    operator fun get(action: ACTION) = actionValues[action]

    fun update(action: ACTION, stateAfterAction: NonTerminateStateValue<ACTION>) {
        val actionValue = actionValues[action]
        actionValue.update(stateAfterAction)
        if (actionValue > _bestAction){
            _bestAction = actionValue
        }
    }

    fun update(reword: Double) = immediateReward.add(reword)

}
