package com.example.quiz.presentation.ui.main.test.test_item.feedback

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
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
import com.example.quiz.presentation.model.test.TestSubmit
import com.example.quiz.presentation.model.test.TestResult
import com.example.quiz.presentation.ui.main.test.test_item.question.QuestionFragment
import com.example.quiz.presentation.util.Const
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.layout_item_add_text_question.*
import kotlinx.android.synthetic.main.toolbar_test.*
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Provider

class FeedbackFragment : BaseFragment(), FeedbackView, BackButtonListener, View.OnClickListener {

    private lateinit var question: Question
    private lateinit var test: Test
    private var number: Int = 0

    private var textViews: MutableList<TextView> = ArrayList()
    private var checkBoxes: MutableList<CheckBox> = ArrayList()

    @InjectPresenter
    lateinit var presenter: FeedbackPresenter
    @Inject
    lateinit var presenterProvider: Provider<FeedbackPresenter>
    @ProvidePresenter
    fun providePresenter(): FeedbackPresenter = presenterProvider.get()

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
            .onPositive(object : MaterialDialog.SingleButtonCallback {
                override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                    val args = Bundle()
                    args.putString(Const.TEST_ITEM, test.id.toString())
                    presenter.onTestClick(args)
                }

            })
            .show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        arguments?.let {
            number = it.getInt(Const.QUESTION_NUMBER)
            test = Const.gson.fromJson(it.getString(Const.TEST_ITEM), Test::class.java)
            question = test.feedbackQuestions[number]
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        initViews(view)
        hideProgressDialog()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews(view: View) {
        setToolbar()
        if(number + 1 == test.feedbackQuestions.size) {
            btn_ok.visibility = View.VISIBLE
        } else {
            btn_ok.visibility = View.GONE
        }
        tv_question.text = question.description

        if(!question.type.equals(Const.TEST_TEXT_TYPE)) {
            setStartAnswers()
        } else {
            setTextAnswer()
        }
    }

    private fun setToolbar() {
        setActionBar(toolbar_test)
        toolbar_title.text = getString(R.string.question_number, number + 1, test.feedbackQuestions.size)
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
            Log.d(Const.TAG_LOG,"content = " + tv.text)
        }
        changeButtons()
    }

    private fun changeButtons() {
        if(number + 1 == test.feedbackQuestions.size) {
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
                    Log.d(Const.TAG_LOG,"change on one type")
                    changeToOneType(v as CheckBox)

                }
            }
        }
    }

    private fun finishQuestions() {
        if(checkAnswers()) {
            val testResult = getTestResult()
            presenter.postTestResult(test.id, testResult)
        }
    }

    private fun nextQuestion() {
        if(checkAnswers()) {
            val args: Bundle = Bundle()
            args.putString(Const.TEST_ITEM, Const.gson.toJson(test))
            args.putInt(Const.QUESTION_NUMBER, ++number)
            presenter.onNextQuestionClick(args)
        }
    }

    private fun changeToOneType(check: CheckBox?) {
        Log.d(Const.TAG_LOG, "changeToOneType")
        var count = if (check == null) 0 else 1
        val boxes: MutableList<CheckBox> = ArrayList()
        for (checkBox in checkBoxes) {
            if (checkBox.isChecked && check != checkBox) {
                Log.d(Const.TAG_LOG,"add to box")
                boxes.add(checkBox)
            }
        }
        for (checkBox in boxes) {
            if (checkBox.isChecked) {
                count++
                checkBox.isChecked = if (count > 1 ) false else true
                Log.d(Const.TAG_LOG,"checkbox is checked = ${checkBox.isChecked}")
            }
        }

    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.btn_finish_questions -> finishQuestions()

            R.id.btn_next_question -> nextQuestion()

            R.id.btn_ok -> finishQuestions()

            R.id.btn_cancel -> shouldCancel()

            R.id.btn_back -> shouldCancel()

            R.id.btn_forward -> nextQuestion()

        }
    }

    private fun checkAnswers(): Boolean {
        var flag = true
        if(!question.type.equals(Const.TEST_TEXT_TYPE)) {
            var count = 0
            for (i in question.answers.indices) {
                val answer: Answer = question.answers[i]
                if (checkBoxes.get(i).isChecked) {
                    count++
                    answer.userClicked = true
                    Log.d(Const.TAG_LOG, "checked answer = ${answer.content}")
                }
            }
            if(count == 0) {
                flag = false
                showSnackBar("Выберите хотя бы один ответ!")
            }
        } else {
            val userAnswer = et_text_answer.text.toString()
            if(userAnswer.equals("")) {
                flag = false
                ti_answer.error = "Напишите ответ"
            }
            question.userAnswer = userAnswer
        }
        return flag
    }

    private fun getTestResult(): TestSubmit {
        val testResult = TestSubmit()
        var questionRes: Question
        for(question in test.questions) {
            questionRes = Question()
            questionRes.id = question.id
            for(answer in question.answers) {
                if(answer.userClicked) {
                    answer.isRight = question.userRight
                    if(question.type.equals(Const.TEST_TEXT_TYPE)) {
                        answer.content = question.userAnswer
                    }
                    questionRes.answers.add(answer)
                }
            }
            testResult.questions.add(questionRes)
        }
        for(question in test.feedbackQuestions) {
            questionRes = Question()
            questionRes.id = question.id
            for(answer in question.answers) {
                if(answer.userClicked) {
                    if(question.type.equals(Const.TEST_TEXT_TYPE)) {
                        answer.content = question.userAnswer
                    }
                    questionRes.answers.add(answer)
                }
            }
            testResult.feedbackQuestions.add(questionRes)

        }
        return testResult
    }

    private fun addAnswer(answer: Answer) {
        val view: LinearLayout = layoutInflater.inflate(R.layout.layout_item_question,li_answers,false) as LinearLayout
        val tvAnswer: TextView = view.findViewWithTag("tv_answer")
        tvAnswer.text = answer.content
        textViews?.add(tvAnswer)
        Log.d(Const.TAG_LOG,"content tv = ${tvAnswer.text}")
        val checkBox: CheckBox = view.findViewWithTag("checkbox")
        checkBox.setOnClickListener(checkListener)
        checkBoxes?.add(checkBox)
        Log.d(Const.TAG_LOG,"checkboxes size = ${checkBoxes?.size}")
        li_answers.addView(view)
    }

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = FeedbackFragment()
            fragment.arguments = args
            return fragment
        }
    }
}