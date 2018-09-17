package ru.ohmy.theme.domain.interactor

import ru.ohmy.theme.domain.common.Publisher
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PaletteInteractor @Inject constructor() : Publisher() {

    private val explainSubject: PublishSubject<Any> = PublishSubject.create()
    private val enabledSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    val disabled: Boolean
        get() = when {
            enabledSubject.hasValue() -> enabledSubject.value
            else -> false
        }

    fun explain() {
        explainSubject.onNext(SIGNAL)
    }

    fun enable(enabled: Boolean) {
        enabledSubject.onNext(enabled)
    }

    fun observeExplain(): Observable<Any> {
        return explainSubject
    }

    fun observeEnable(): Observable<Boolean> {
        return enabledSubject
    }

    companion object {
        private val SIGNAL = Any()
    }

}