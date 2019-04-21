package com.example.quiz.presentation.ui.main.test.test_item.finish

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
import com.example.quiz.presentation.model.test.Question
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.model.test.TestResult
import com.example.quiz.presentation.util.Const.ANSWERS_TYPE
import com.example.quiz.presentation.util.Const.RIGHT_ANSWERS
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.TEST_ITEM
import com.example.quiz.presentation.util.Const.WRONG_ANSWERS
import com.example.quiz.presentation.util.Const.gson
import kotlinx.android.synthetic.main.fragment_finish_test.*
import javax.inject.Inject
import javax.inject.Provider

class FinishFragment : BaseFragment(), FinishView, View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: FinishPresenter
    @Inject
    lateinit var presenterProvider: Provider<FinishPresenter>
    @ProvidePresenter
    fun providePresenter(): FinishPresenter = presenterProvider.get()

    lateinit var test: Test
    var rightQuestions: MutableList<Question> = ArrayList()
    var wrongQuestions: MutableList<Question> = ArrayList()
    var procent: Long = 0

   /* override fun onBackPressed() {
       *//* val args: Bundle = Bundle()
        args.putString(TEST_JSON, gsonConverter.toJson(test))
        val fragment = FinishFragment.newInstance(args)
        (activity as BaseBackActivity).changeFragment(fragment)*//*
    }

    override fun onOk() {
        btn_finish_test.performClick()
    }*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_finish_test, container, false)
       /* (activity as BaseBackActivity).currentTag = TestActivity.FINISH_FRAGMENT
        (activity as ChangeToolbarListener).changeToolbar(FINISH_FRAGMENT,"Результат")*/

       test = gson.fromJson(arguments?.getString(TEST_ITEM),Test::class.java)
       val testSubmit = TestResult()
       for(question in test.questions) {
            if(question.userRight) {
                val questionRes = Question()
                for(answer in question.answers) {
                    if(answer.userClicked) {
                        questionRes.answers.add(answer)
                    }
                }
                rightQuestions.add(question)
            } else {
                val questionRes = Question()
                for(answer in question.answers) {
                    if(answer.userClicked) {
                        questionRes.answers.add(answer)
                    }
                }
                wrongQuestions.add(question)
            }
        }
        test.rightQuestions = rightQuestions
        test.wrongQuestions = wrongQuestions

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbar()
        tv_right_answers.text = rightQuestions.size.toString()
        tv_wrong_answers.text = wrongQuestions.size.toString()
        setCardText()

        btn_finish_test.setOnClickListener(this)
        li_wrong_answers.setOnClickListener(this)
        li_right_answers.setOnClickListener(this)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setToolbar() {
        setActionBar(toolbar)
//        setActionBarTitle(R.string.test_result)
    }

    fun setCardText() {
        procent = Math.round((test.rightQuestions.size.toDouble() / test.questions.size.toDouble()) * 100)
        Log.d(TAG_LOG, "procent = $procent")
        if (procent >= 80) {
            Log.d(TAG_LOG, "finish it")
            presenter.finishTest(test)


        }
    }


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_finish_test -> {
                for(question in test.questions) {
                    question.userRight = false
                    for(answer in question.answers) {
                        answer.userClicked = false
                    }
                }
//                removeStackDownTo()
                val args: Bundle = Bundle()
                args.putString(TEST_ITEM, gson.toJson(test))
                presenter.onTestClick(args)
            }

            R.id.li_wrong_answers -> {
                if(wrongQuestions.size > 0) {
                    prepareAnswers(WRONG_ANSWERS)
                }
            }

            R.id.li_right_answers -> {
                if(rightQuestions.size > 0) {
                    prepareAnswers(RIGHT_ANSWERS)
                }
            }
        }
    }

    fun prepareAnswers(type: String) {
        val args: Bundle = Bundle()
        args.putString(ANSWERS_TYPE, type)
        args.putString(TEST_ITEM, gson.toJson(test))
        presenter.onAnswersClick(args)
    }


    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = FinishFragment()
            fragment.arguments = args
            return fragment
        }
    }
}