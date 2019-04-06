package com.example.quiz.presentation.ui.test.test_list.tab_fragment

import android.view.ViewGroup
import com.example.quiz.presentation.base.recycler.BaseAdapter
import com.example.quiz.presentation.model.test.Test

class TestAdapter(items: MutableList<Test>) : BaseAdapter<Test, TestItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestItemHolder {
        return TestItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TestItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}