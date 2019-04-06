package com.example.quiz.presentation.ui.test.test_item.question

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import java.util.ArrayList

import android.widget.*
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.model.test.Answer
import com.example.quiz.presentation.model.test.Question
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.ui.test.test_item.finish.FinishFragment
import com.example.quiz.presentation.util.Const.QUESTION_NUMBER
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.TEST_ITEM
import com.example.quiz.presentation.util.Const.TEST_TEXT_TYPE
import com.example.quiz.presentation.util.Const.gson
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.layout_item_add_text_question.*
import kotlinx.android.synthetic.main.toolbar_test.*
import javax.inject.Inject
import javax.inject.Provider


class QuestionFragment : BaseFragment(), QuestionView, BackButtonListener, View.OnClickListener {

    private lateinit var question: Question
    private lateinit var test: Test
    private var number: Int = 0

    private var textViews: MutableList<TextView> = ArrayList()
    private var checkBoxes: MutableList<CheckBox> = ArrayList()

    @InjectPresenter
    lateinit var presenter: QuestionPresenter
    @Inject
    lateinit var presenterProvider: Provider<QuestionPresenter>
    @ProvidePresenter
    fun providePresenter(): QuestionPresenter = presenterProvider.get()

   /* override fun onBackPressed() {
        shouldCancel()
    }

    override fun onCancel() {
        shouldCancel()
    }

    override fun onForward() {
        nextQuestion()
    }

    override fun onOk() {
        finishQuestions()
    }*/

    override fun onBackPressed(): Boolean {
        shouldCancel()
        return true
    }

    fun shouldCancel() {
        MaterialDialog.Builder(activity as Context)
                .title(R.string.question_dialog_title)
                .content(R.string.question_dialog_content)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .onPositive(object :MaterialDialog.SingleButtonCallback {
                    override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                        for(question in test.questions) {
                            question.userRight = false
                            for(answer in question.answers) {
                                answer.userClicked = false
                            }
                        }
//                        removeStackDownTo()
                        val args = Bundle()
                        args.putString(TEST_ITEM, gson.toJson(test))
                        presenter.onTestClick(args)
                        /*val fragment = TestFragment.newInstance(args)
                        pushFragments(fragment, true)*/
//                        TestActivity.start(activity as Activity,test)
                    }

                })
                .show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        arguments?.let {
            number = it.getInt(QUESTION_NUMBER)
            test = gson.fromJson(it.getString(TEST_ITEM), Test::class.java)
            question = test.questions[number]
        }


       /* (activity as BaseBackActivity).currentTag = TestActivity.QUESTION_FRAGMENT + number
        (activity as ChangeToolbarListener).changeToolbar(QUESTION_FRAGMENT,"Вопрос ${number+1}/${test.questions.size}")*/

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews(view: View) {
        setToolbar()
        if(number + 1 == test.questions.size) {
            btn_ok.visibility = View.VISIBLE
//            (activity as ChangeToolbarListener).showOk(true)
        } else {
            btn_ok.visibility = View.GONE
//            (activity as ChangeToolbarListener).showOk(false)
        }
        tv_question.text = question.description

        if(!question.type.equals(TEST_TEXT_TYPE)) {
            setStartAnswers()
        } else {
            setTextAnswer()
        }
    }

    private fun setToolbar() {
        setActionBar(toolbar_test)
        toolbar_title.text = getString(R.string.question_number, number + 1, test.questions.size)
    }

    private fun setTextAnswer() {
        layoutInflater.inflate(R.layout.layout_item_add_text_question,li_answers,true)
//        li_answers.addView(view)
    }

    private fun setStartAnswers() {

        for (answer in question.answers) {
            addAnswer(answer)
        }
        for(tv in textViews!!) {
            Log.d(TAG_LOG,"content = " + tv.text)
        }

        if(number == (test.questions.size-1)) {
            btn_next_question.visibility = View.GONE
            btn_finish_questions.visibility = View.VISIBLE
//            (activity as ChangeToolbarListener).showOk(true)
        }
    }


    private fun setListeners() {
        btn_finish_questions!!.setOnClickListener(this)
        btn_next_question!!.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        btn_forward.setOnClickListener(this)

        btn_back.visibility = View.GONE
    }

    private fun finishQuestions() {
//        removeStackDownTo()
        checkAnswers()
        val args: Bundle = Bundle()
        args.putString(TEST_ITEM, gson.toJson(test))
        val fragment = FinishFragment.newInstance(args)
        presenter.onFinishClick(args)
//        pushFragments(fragment, true)
    }

    private fun nextQuestion() {
        checkAnswers()
        val args: Bundle = Bundle()
        args.putString(TEST_ITEM, gson.toJson(test))
        args.putInt(QUESTION_NUMBER, ++number)
        presenter.onNextQuestionClick(args)
       /* val fragment = QuestionFragment.newInstance(args)
        pushFragments(fragment, true)*/
    }
    override fun onClick(v: View) {


        when (v.id) {

            R.id.btn_finish_questions -> {

               finishQuestions()
               /* activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, FinishFragment.newInstance(args))
                        .addToBackStack("AddQuestionFragment")
                        .commit()      */      }

            R.id.btn_next_question -> {
                nextQuestion()
               /* activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, QuestionFragment.newInstance(args))
                        .addToBackStack("AddQuestionFragment")
                        .commit()*/
            }

            R.id.btn_ok -> finishQuestions()

            R.id.btn_cancel -> shouldCancel()

            R.id.btn_back -> shouldCancel()

            R.id.btn_forward -> nextQuestion()

        }
    }

    private fun checkAnswers() {
        if(!question.type.equals(TEST_TEXT_TYPE)) {
            Log.d(TAG_LOG, "questioin = ${question.description}")
            question.userRight = true
            for (i in question.answers.indices) {
                val answer: Answer = question.answers[i]
                if (checkBoxes.get(i).isChecked) {
                    answer.userClicked = true
                    Log.d(TAG_LOG, "checked answer = ${answer.text}")
                }
                if (answer.userClicked != answer.isRight) {
                    question.userRight = false
                    Log.d(TAG_LOG, "wrong i = $i and answer = " + question.answers[i])
                }
                Log.d(
                    TAG_LOG,
                    "userclick = ${answer.userClicked} and q right = ${answer.isRight} and content = ${answer.text}"
                )
            }
        } else {
            val rightAnswer = question.answers[0].text
            val userAnswer = et_text_answer.text.toString()
            if(rightAnswer.equals(userAnswer)) {
                question.userRight = true
            } else {
                question.userRight = false
            }
        }
    }

    private fun addAnswer(answer: Answer) {
        val view: LinearLayout = layoutInflater.inflate(R.layout.layout_item_question,li_answers,false) as LinearLayout
        val tvAnswer: TextView = view.findViewWithTag("tv_answer")
        tvAnswer.text = answer.text
        textViews?.add(tvAnswer)
        Log.d(TAG_LOG,"content tv = ${tvAnswer.text}")
        val checkBox: CheckBox = view.findViewWithTag("checkbox")
        checkBoxes?.add(checkBox)
        Log.d(TAG_LOG,"checkboxes size = ${checkBoxes?.size}")
        li_answers.addView(view)
    }

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = QuestionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
