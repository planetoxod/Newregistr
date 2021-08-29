package com.testforme.newregistr.stuff.baseMVP

abstract class BasePresenterImpl<V : BaseView> : BasePresenter<V>{

    var mView: V? = null

    override fun getView(): V? = mView

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

    override fun isViewAttached(): Boolean = mView != null

}