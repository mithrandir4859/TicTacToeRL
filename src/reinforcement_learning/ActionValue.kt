package reinforcement_learning

import util.AverageKeeper

class ActionValue<ACTION>(val action: ACTION) : Comparable<ActionValue<ACTION>>{

    override fun compareTo(other: ActionValue<ACTION>) = (longtermReturn.average - other.longtermReturn.average).toInt()

    val longtermReturn = AverageKeeper()

    fun update(state: NonTerminateStateValue<ACTION>){
        longtermReturn.add(state.stateValue)
    }

}
