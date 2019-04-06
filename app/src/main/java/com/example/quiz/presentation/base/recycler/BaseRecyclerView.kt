package com.example.quiz.presentation.base.recycler

import com.example.quiz.presentation.base.BaseView
import io.reactivex.disposables.Disposable

interface BaseRecyclerView<Entity> : BaseView, BaseAdapter.OnItemClickListener<Entity> {

    fun handleError(throwable: Throwable)

    fun setNotLoading()

    fun showLoading(disposable: Disposable)

    fun hideLoading()

    fun loadNextElements(i: Int)

    fun changeDataSet(tests: List<Entity>)
}
