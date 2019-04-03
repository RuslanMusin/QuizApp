package com.example.quiz.presentation.ui.test.add_test.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.util.Const.QUESTION_NUMBER
import com.example.quiz.presentation.util.Const.TEST_ITEM
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_test.*
import javax.inject.Inject

class AddMainTestFragment : BaseFragment(), AddMainTestView, View.OnClickListener {

    @Inject
    lateinit var gson: Gson

    @InjectPresenter
    lateinit var presenter: AddMainTestPresenter

    lateinit var test: Test

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_test, container, false)
        return view
    }

    private fun setTestData() {
        et_test_name.setText(test?.name)
        et_test_desc.setText(test?.description)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setListeners()

        if(arguments == null) {
            test = Test()
        } else {
            test = gson.fromJson(arguments?.getString(TEST_ITEM), Test::class.java)
            setTestData()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews(view: View) {
        setToolbar()
    }

    private fun setToolbar() {
    }

    private fun setListeners() {
        btn_create_questions.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_create_questions -> {
                test!!.name = et_test_name.text.toString()
                test!!.description = et_test_desc.text.toString()

                if(checkTest()) {//
                    val args: Bundle = Bundle()
                    args.putString(TEST_ITEM, gson.toJson(test))
                    args.putInt(QUESTION_NUMBER, 0)
                    presenter.onCreateQuestionCommandClick(args)
                } else {

                }
            }
        }
    }

    private fun checkTest(): Boolean {
        var flag: Boolean = if(test == null) false else true
        test?.let {
            if(it.name == null  || it.name?.trim().equals("")) {
                ti_test_name.error = "Введите название теста!"
                flag = false
            } else {
                ti_test_name.error = null
            }
            if(it.description == null  || it.description?.trim().equals("")) {
                ti_test_desc.error = "Введите описание теста!"

                flag = false

            } else {
                ti_test_desc?.error = null
            }
        }
        return flag
    }

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = AddMainTestFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): Fragment {
            val fragment = AddMainTestFragment()
            return fragment
        }
    }
}
