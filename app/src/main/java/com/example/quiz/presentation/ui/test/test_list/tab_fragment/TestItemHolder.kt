package com.example.quiz.presentation.ui.test.test_list.tab_fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quiz.R
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.util.Const
import com.example.quiz.presentation.util.Const.MAX_LENGTH
import com.example.quiz.presentation.util.Const.cutLongDescription
import kotlinx.android.synthetic.main.item_test.view.*

class TestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Test) {
        itemView.tv_name.text = item.name
        itemView.tv_description.text = item.description?.let { cutLongDescription(it, MAX_LENGTH) }
        if(item.dateOpen != null && item.dateClose == null) {
            itemView.tv_open_test.text = itemView.context.getString(R.string.yes)
        } else {
            itemView.tv_open_test.text = itemView.context.getString(R.string.no)
        }
    }

    companion object {

        fun create(parent: ViewGroup): TestItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false);
            val holder = TestItemHolder(view)
            return holder
        }
    }
}
