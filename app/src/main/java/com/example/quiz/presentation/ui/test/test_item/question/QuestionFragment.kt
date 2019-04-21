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
import com.example.quiz.presentation.util.Const
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

    private lateinit var checkListener: View.OnClickListener

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
                        /*for(question in test.questions) {
                            question.userRight = false
                            for(answer in question.answers) {
                                answer.userClicked = false
                            }
                        }*/
                        val args = Bundle()
                        args.putString(TEST_ITEM, test.id.toString())
                        presenter.onTestClick(args)
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        initViews(view)
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
        changeButtons()
        question.answers[0].userClicked = true
    }

    private fun setStartAnswers() {

        for (answer in question.answers) {
            addAnswer(answer)
        }
        for(tv in textViews!!) {
            Log.d(TAG_LOG,"content = " + tv.text)
        }
        changeButtons()
    }

    private fun changeButtons() {
        if(number + 1 == test.questions.size) {
            btn_next_question.visibility = View.GONE
            btn_finish_questions.visibility = View.VISIBLE
        }
    }


    private fun setListeners() {
        btn_finish_questions!!.setOnClickListener(this)
        btn_next_question!!.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        btn_forward.setOnClickListener(this)
        btn_back.visibility = View.GONE
        checkListener = object: View.OnClickListener{
            override fun onClick(v: View?) {
                if(question.type.equals(Const.TEST_ONE_TYPE)) {
                    Log.d(TAG_LOG,"change on one type")
                    changeToOneType(v as CheckBox)

                }
            }
        }
    }

    private fun finishQuestions() {
        checkAnswers()
        val args: Bundle = Bundle()
        args.putString(TEST_ITEM, gson.toJson(test))
        presenter.onBeforeFeedbackClick(args)
    }

    private fun nextQuestion() {
        checkAnswers()
        val args: Bundle = Bundle()
        args.putString(TEST_ITEM, gson.toJson(test))
        args.putInt(QUESTION_NUMBER, ++number)
        presenter.onNextQuestionClick(args)
    }

    private fun changeToOneType(check: CheckBox?) {
        Log.d(TAG_LOG, "changeToOneType")
        var count = if (check == null) 0 else 1
        val boxes: MutableList<CheckBox> = ArrayList()
        for (checkBox in checkBoxes) {
            if (checkBox.isChecked && check != checkBox) {
                Log.d(TAG_LOG,"add to box")
                boxes.add(checkBox)
            }
        }
        for (checkBox in boxes) {
            if (checkBox.isChecked) {
                count++
                checkBox.isChecked = if (count > 1 ) false else true
                Log.d(TAG_LOG,"checkbox is checked = ${checkBox.isChecked}")
            }
        }

    }

    override fun onClick(v: View) {


        when (v.id) {

            R.id.btn_finish_questions -> {

               finishQuestions()
            }

            R.id.btn_next_question -> {
                nextQuestion()
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
                    Log.d(TAG_LOG, "checked answer = ${answer.content}")
                }
                if (answer.userClicked != answer.isRight) {
                    question.userRight = false
                    Log.d(TAG_LOG, "wrong i = $i and answer = " + question.answers[i])
                }
                Log.d(
                    TAG_LOG,
                    "userclick = ${answer.userClicked} and q right = ${answer.isRight} and content = ${answer.content}"
                )
            }
        } else {
            val rightAnswer = question.answers[0].content
            val userAnswer = et_text_answer.text.toString()
            question.userAnswer = userAnswer
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
        tvAnswer.text = answer.content
        textViews?.add(tvAnswer)
        Log.d(TAG_LOG,"content tv = ${tvAnswer.text}")
        val checkBox: CheckBox = view.findViewWithTag("checkbox")
        checkBox.setOnClickListener(checkListener)
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
