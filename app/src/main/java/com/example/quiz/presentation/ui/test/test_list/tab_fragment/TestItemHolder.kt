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
import kotlinx.android.synthetic.main.layout_test.*

class TestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Test) {
        itemView.tv_name.text = item.name
        itemView.tv_id.text = item.id.toString()

        if(item.dateClose != null) {
            itemView.tv_status.text = itemView.context.getString(R.string.test_closed)
        } else if(item.dateOpen != null) {
            itemView.tv_status.text = itemView.context.getString(R.string.test_opened)
        } else {
            itemView.tv_status.text = itemView.context.getString(R.string.test_not_started)
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
