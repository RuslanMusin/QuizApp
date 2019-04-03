package com.example.quiz.presentation.ui.test.test_list.tab_fragment

import android.view.ViewGroup
import android.widget.BaseAdapter

class ThemeAdapter(items: MutableList<Test>) : BaseAdapter<Test, ThemeItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeItemHolder {
        return ThemeItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ThemeItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}