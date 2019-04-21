package com.example.quiz.presentation.ui.main.test.test_list.tab_fragment.ended_test_list

import com.example.quiz.presentation.base.recycler.BaseRecyclerView
import com.example.quiz.presentation.base.recycler.ReloadableView
import com.example.quiz.presentation.model.test.Test

interface EndedTestsView: BaseRecyclerView<Test>, ReloadableView {

    fun showItems(tests: List<Test>)

}