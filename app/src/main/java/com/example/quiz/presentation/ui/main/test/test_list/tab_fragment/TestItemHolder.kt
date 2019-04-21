package com.example.quiz.presentation.ui.main.test.test_list.tab_fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quiz.R
import com.example.quiz.presentation.model.test.Test
import kotlinx.android.synthetic.main.item_text.view.*

class TestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Test) {
        itemView.tv_name.text = item.name
    }

    companion object {

        fun create(parent: ViewGroup): TestItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false);
            val holder = TestItemHolder(view)
            return holder
        }
    }
}
