package com.example.quiz.presentation.ui.test.test_list.tab_fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Test) {
        itemView.tv_theme.text = item.title
        itemView.tv_student.text = getStudentName(itemView, item)
        itemView.setOnLongClickListener {
            listener.openStudentAction(adapterPosition)
            true
        }
    }


    companion object {

        fun create(parent: ViewGroup, listener: MyThemeListView): TestItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_theme, parent, false);
            val holder = TestItemHolder(view)
            holder.listener = listener
            return holder
        }
    }
}
