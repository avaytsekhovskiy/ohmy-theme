package com.noveogroup.template.domain.interactor

import com.noveogroup.template.domain.common.Publisher
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PaletteInteractor @Inject constructor() : Publisher() {

    private val explainSubject: PublishSubject<Any> = PublishSubject.create()
    private val disabledSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    val disabled: Boolean
        get() = when {
            disabledSubject.hasValue() -> disabledSubject.value
            else -> false
        }

    fun explain() {
        explainSubject.onNext(SIGNAL)
    }

    fun disable(disabled: Boolean) {
        disabledSubject.onNext(disabled)
    }

    fun observeExplain(): Observable<Any> {
        return explainSubject
    }

    fun observeDisable(): Observable<Boolean> {
        return disabledSubject
    }

    companion object {
        private val SIGNAL = Any()
    }

}