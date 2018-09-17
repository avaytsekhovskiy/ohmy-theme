package ru.ohmy.theme.presentation.common.mvp.strategy


import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.ViewCommand
import com.arellomobile.mvp.viewstate.strategy.StateStrategy

class AddToEndSingleByTagStrategy : StateStrategy {
    override fun <View : MvpView> beforeApply(
            currentState: MutableList<ViewCommand<View>>,
            incomingCommand: ViewCommand<View>
    ): Unit = currentState.run {
        find { it.tag == incomingCommand.tag }?.let(::remove)
        add(incomingCommand)
    }

    override fun <View : MvpView> afterApply(
            currentState: List<ViewCommand<View>>,
            incomingCommand: ViewCommand<View>
    ) {
        //no operation
    }
}
