package com.example.quiz.presentation.ui.main.test.test_item.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.util.Const
import com.example.quiz.presentation.util.Const.QUESTION_NUMBER
import com.example.quiz.presentation.util.Const.RIGHT_ANSWERS
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.TEST_ITEM
import com.example.quiz.presentation.util.Const.TEST_PASSED
import com.example.quiz.presentation.util.Const.currentUser
import com.example.quiz.presentation.util.Const.getStringFromDate
import com.example.quiz.presentation.util.Const.gson
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.layout_expandable_text_view.*
import kotlinx.android.synthetic.main.layout_test.*
import javax.inject.Inject
import javax.inject.Provider



class TestFragment : BaseFragment(), TestView, BackButtonListener, View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: TestPresenter
    @Inject
    lateinit var presenterProvider: Provider<TestPresenter>
    @ProvidePresenter
    fun providePresenter(): TestPresenter = presenterProvider.get()

    lateinit var test: Test
    var isPassed: Boolean = false

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = TestFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.onTestListClick()
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_test, container, false)

        isPassed = arguments?.getBoolean(TEST_PASSED)!!
        val testId: Int? = arguments?.getString(TEST_ITEM)?.toInt()
        testId?.let { presenter.findTest(it) }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        setListeners()
    }

    override fun setData(test: Test) {
        this.test = test
        setToolbar()
        setTextFields()
        setVisibilityFields()
    }

    private fun setTextFields() {
        expand_text_view.text = test.description
        tv_name.text = test.name
        tv_id.text = test.id.toString()
        tv_questions.text = test.questions.size.toString()
        tv_date_creation.text = test.dateCreation?.let { getStringFromDate(it) }
        if(test.dateClose != null) {
            tv_status.text = getString(R.string.test_closed)
            li_do_test.visibility = View.GONE
        } else if(test.dateOpen != null) {
            tv_status.text = getString(R.string.test_opened)
            if(isPassed) {
                li_do_test.visibility = View.GONE
            }
        } else {
            tv_status.text = getString(R.string.test_not_started)
            li_do_test.visibility = View.GONE
        }
    }

    private fun setVisibilityFields() {
        test.owner?.id?.let {
            if(it == currentUser.id) {
                li_do_test.visibility = View.VISIBLE
                tv_do_test.visibility = View.GONE
                li_show_answers.visibility = View.VISIBLE
                if(test.dateClose != null) {
                    li_do_test.visibility = View.GONE
                    li_show_result.visibility = View.VISIBLE
                } else if(test.dateOpen != null) {
                    tv_open_test.visibility = View.GONE
                    tv_close_test.visibility = View.VISIBLE
                } else {
                    tv_open_test.visibility = View.VISIBLE
                    tv_close_test.visibility = View.GONE
                }
            } else {
                if(test.dateClose != null) {
                    if(isPassed) {
                        li_show_result.visibility = View.VISIBLE
                    }
                    li_do_test.visibility = View.GONE
                }
            }
            hideProgressDialog()
        }
    }

    private fun setToolbar() {
        setActionBar(toolbar)
        toolbar.title = test.name
        toolbar.setNavigationOnClickListener { presenter.onTestListClick() }
    }

    private fun setListeners() {
        tv_do_test.setOnClickListener(this)
        tv_open_test.setOnClickListener(this)
        tv_close_test.setOnClickListener(this)
        tv_show_result.setOnClickListener(this)
        li_show_answers.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.tv_do_test -> {
                val args: Bundle = Bundle()
                args.putString(TEST_ITEM, gson.toJson(test))
                args.putInt(QUESTION_NUMBER,0)
                presenter.onQuestionClick(args)

            }

            R.id.tv_open_test -> {
                presenter.openTest(test.id)
            }

            R.id.tv_close_test -> {
                presenter.closeTest(test.id)
            }

            R.id.tv_show_result -> showResult()

            R.id.li_show_answers -> showAnswers()
        }
    }

    private fun showAnswers() {
        test.rightQuestions = ArrayList()
        for(question in test.questions) {
            test.rightQuestions.add(question)
        }
        val args: Bundle = Bundle()
        args.putString(Const.ANSWERS_TYPE, RIGHT_ANSWERS)
        args.putString(TEST_ITEM, gson.toJson(test))
        presenter.onAnswersClick(args)
    }

    private fun showResult() {
        currentUser.id.let {
            if(it == test.owner?.id) {
                presenter.showResultOverview(test.id)
            } else {
                presenter.showResult(test.id, it)
            }
        }
    }

    override fun afterTestOpened() {
        showSnackBar(R.string.test_opened)
        tv_open_test.visibility = View.GONE
        tv_close_test.visibility = View.VISIBLE
        li_show_result.visibility = View.GONE
        tv_status.text = getString(R.string.test_opened)
    }

    override fun afterTestClosed() {
        showSnackBar(R.string.test_closed)
        li_do_test.visibility = View.GONE
        tv_close_test.visibility = View.GONE
        li_show_result.visibility = View.VISIBLE
        tv_status.text = getString(R.string.test_closed)
    }
}


