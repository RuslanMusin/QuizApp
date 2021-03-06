package com.example.quiz.presentation.ui.main.test.test_item.finish

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.model.test.*
import com.example.quiz.presentation.util.Const
import com.example.quiz.presentation.util.Const.ANSWERS_TYPE
import com.example.quiz.presentation.util.Const.RIGHT_ANSWERS
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.TEST_ITEM
import com.example.quiz.presentation.util.Const.TEST_MANY_TYPE
import com.example.quiz.presentation.util.Const.TEST_ONE_TYPE
import com.example.quiz.presentation.util.Const.TEST_TEXT_TYPE
import com.example.quiz.presentation.util.Const.WRONG_ANSWERS
import com.example.quiz.presentation.util.Const.gson
import kotlinx.android.synthetic.main.fragment_finish_test.*
import javax.inject.Inject
import javax.inject.Provider

class FinishFragment : BaseFragment(), FinishView, View.OnClickListener, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: FinishPresenter
    @Inject
    lateinit var presenterProvider: Provider<FinishPresenter>
    @ProvidePresenter
    fun providePresenter(): FinishPresenter = presenterProvider.get()

    lateinit var test: TestResult
    var rightQuestions: MutableList<QuestionResult> = ArrayList()
    var wrongQuestions: MutableList<QuestionResult> = ArrayList()
    var procent: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_finish_test, container, false)
       test = gson.fromJson(arguments?.getString(TEST_ITEM),TestResult::class.java)
       for(question in test.questions) {
           if(question.type.equals(TEST_TEXT_TYPE)) {
               val answer = question.participantAnswers[0]
               question.userAnswer = answer.content!!
               if(answer.isRight) {
                   rightQuestions.add(question)
               } else {
                   wrongQuestions.add(question)
               }
           }
           if(question.type.equals(TEST_ONE_TYPE)) {
               val userAnswer = question.participantAnswers[0]
               for(answer in question.answers) {
                   if(answer.id == userAnswer.id) {
                       answer.userClicked = true
                       if(answer.isRight) {
                           rightQuestions.add(question)
                       } else {
                           wrongQuestions.add(question)
                       }
                   }
               }
           }
           if(question.type.equals(TEST_MANY_TYPE)) {
               var flag = true
               var count = 0
               var userCount = 0
               for(userAnswer in question.participantAnswers) {
                   if(userAnswer.isRight) {
                       userCount++
                   }
                   for(answer in question.answers) {
                       if(answer.id == userAnswer.id) {
                           answer.userClicked = true
                           if(answer.isRight != answer.userClicked) {
                               flag = false
                           }
                       }
                   }
               }
               for(answer in question.answers) {
                   if(answer.isRight) {
                       count++
                   }
               }
               if(userCount != count) {
                   flag = false
               }
               if(flag) {
                   rightQuestions.add(question)
               } else {
                   wrongQuestions.add(question)
               }
           }
       }
        test.rightQuestions = rightQuestions
        test.wrongQuestions = wrongQuestions

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbar()
        tv_right_answers.text = test.rightQuestions.size.toString()
        tv_wrong_answers.text = test.wrongQuestions.size.toString()

        hideProgressDialog()

        btn_finish_test.setOnClickListener(this)
        li_wrong_answers.setOnClickListener(this)
        li_right_answers.setOnClickListener(this)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setToolbar() {
        setActionBar(toolbar)
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
                val args: Bundle = Bundle()
                args.putString(TEST_ITEM, test.id.toString())
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

    override fun onBackPressed(): Boolean {
        val args: Bundle = Bundle()
        args.putString(TEST_ITEM, test.id.toString())
        presenter.onTestClick(args)
        return true
    }

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = FinishFragment()
            fragment.arguments = args
            return fragment
        }
    }
}