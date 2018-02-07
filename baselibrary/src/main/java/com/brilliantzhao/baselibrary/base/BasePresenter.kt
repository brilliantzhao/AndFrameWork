package com.brilliantzhao.baselibrary.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
open class BasePresenter {

    var compositeSubscription = CompositeSubscription()

    protected fun addSubscription(subscription: Subscription) {
        compositeSubscription.add(subscription)
    }

    fun unSubscribe() {
        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe()
        }
    }
}