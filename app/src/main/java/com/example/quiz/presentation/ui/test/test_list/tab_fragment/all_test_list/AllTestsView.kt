package com.example.quiz.presentation.ui.test.test_list.tab_fragment.all_test_list

import com.example.quiz.presentation.base.recycler.BaseRecyclerView
import com.example.quiz.presentation.base.recycler.ReloadableView
import com.example.quiz.presentation.model.test.Test

interface AllTestsView: BaseRecyclerView<Test>, ReloadableView {

    fun showItems(tests: List<Test>)
}