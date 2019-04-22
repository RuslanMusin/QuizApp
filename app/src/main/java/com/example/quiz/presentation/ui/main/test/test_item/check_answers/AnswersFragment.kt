package com.example.quiz.presentation.ui.main.test.test_item.check_answers

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.CompoundButtonCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.model.test.*
import com.example.quiz.presentation.util.Const.ANSWERS_TYPE
import com.example.quiz.presentation.util.Const.QUESTION_NUMBER
import com.example.quiz.presentation.util.Const.RIGHT_ANSWERS
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.TEST_ITEM
import com.example.quiz.presentation.util.Const.TEST_TEXT_TYPE
import com.example.quiz.presentation.util.Const.WRONG_ANSWERS
import com.example.quiz.presentation.util.Const.currentUser
import com.example.quiz.presentation.util.Const.gson
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.layout_text_question.*
import kotlinx.android.synthetic.main.toolbar_test.*
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Provider

class AnswersFragment : BaseFragment(), AnswersView, BackButtonListener, View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: AnswersPresenter
    @Inject
    lateinit var presenterProvider: Provider<AnswersPresenter>
    @ProvidePresenter
    fun providePresenter(): AnswersPresenter = presenterProvider.get()

    private lateinit var question: QuestionResult
    private lateinit var test: TestResult
    private lateinit var type: String
    private var listSize: Int = 0
    private var number: Int = 0

    private lateinit var colorStateList: ColorStateList
    private lateinit var rightStateList: ColorStateList

    private var textViews: MutableList<TextView>? = null
    private var checkBoxes: MutableList<CheckBox>? = null
    private var radioButtons: MutableList<RadioButton>? = null

    override fun onBackPressed(): Boolean {
        beforeQuestion()
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        arguments?.let {
            type = it.getString(ANSWERS_TYPE)
            number = it.getInt(QUESTION_NUMBER)
            test = gson.fromJson(it.getString(TEST_ITEM), TestResult::class.java)

        }
        if(type.equals(RIGHT_ANSWERS)) {
            question =  test.rightQuestions[number]
            listSize = test.rightQuestions.size
        } else {
           question = test.wrongQuestions[number]
            listSize = test.wrongQuestions.size

        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setToolbar() {
        setActionBar(toolbar_test)
        toolbar_title.text = getString(R.string.question_number, number + 1, listSize)
    }

    private fun initViews(view: View) {
        setToolbar()
        textViews = ArrayList()
        radioButtons = ArrayList()
        checkBoxes = ArrayList()

        if(number == (listSize-1)) {
            btn_next_question.visibility = View.GONE
            btn_forward.visibility = View.GONE
            btn_cancel.visibility = View.GONE
            btn_finish_questions.visibility = View.VISIBLE
            btn_ok.visibility = View.VISIBLE
        }

        tv_question.text = question.description

        setStartAnswers()
    }

    private fun setStartAnswers() {
        if(!question.type.equals(TEST_TEXT_TYPE)) {
            colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked), // unchecked
                    intArrayOf(android.R.attr.state_checked)
                )// checked
                ,
                intArrayOf(Color.parseColor("#FFFFFF"), Color.parseColor("#DC143C"))
            )

            rightStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked), // unchecked
                    intArrayOf(android.R.attr.state_checked)
                )// checked
                ,
                intArrayOf(Color.parseColor("#FFFFFF"), Color.parseColor("#00cc00"))
            )

            for (answer in question.answers) {
                addAnswer(answer)
            }
            for (tv in textViews!!) {
                Log.d(TAG_LOG, "content = " + tv.text)
            }
        } else {
            addTextAnswer()
        }
    }

    private fun setListeners() {
        btn_finish_questions!!.setOnClickListener(this)
        btn_next_question!!.setOnClickListener(this)

        btn_ok.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        btn_forward.setOnClickListener(this)
        btn_back.setOnClickListener(this)

        btn_next_question.text = getString(R.string.next_question)
    }

    private fun beforeQuestion() {
        if(number > 0) {
            val args: Bundle = Bundle()
            args.putString(TEST_ITEM, gson.toJson(test))
            args.putString(ANSWERS_TYPE, type)
            args.putInt(QUESTION_NUMBER, --number)
            presenter.onBeforeAnswerClick(args)
        } else {
            finishQuestions()
        }
    }

    private fun finishQuestions() {
        val args: Bundle = Bundle()
        if(currentUser.id.equals(test.owner?.id)) {
            args.putString(TEST_ITEM, test.id.toString())
            presenter.onFinishClick(args, true)
        } else {
            args.putString(TEST_ITEM, gson.toJson(test))
            presenter.onFinishClick(args, false)
        }
    }

    private fun nextQuestion() {
        val args: Bundle = Bundle()
        args.putString(TEST_ITEM, gson.toJson(test))
        args.putString(ANSWERS_TYPE,type)
        args.putInt(QUESTION_NUMBER, ++number)
        presenter.onNextAnswerClick(args)
    }

    override fun onClick(v: View) {


        when (v.id) {

            R.id.btn_finish_questions -> finishQuestions()

            R.id.btn_next_question -> nextQuestion()

            R.id.btn_cancel -> finishQuestions()

            R.id.btn_back -> beforeQuestion()

            R.id.btn_ok -> finishQuestions()

            R.id.btn_forward -> nextQuestion()

        }
    }

    private fun addTextAnswer() {
        val view: LinearLayout = layoutInflater.inflate(R.layout.layout_text_question,li_answers,false) as LinearLayout
        li_answers.addView(view)
        tv_right_answer.text = question.answers[0].content
        if(type.equals(RIGHT_ANSWERS)) {
            question.userAnswer = question.answers[0].content!!
        }
        tv_your_answer.text = question.userAnswer
    }

    private fun addAnswer(answer: Answer) {
        val view: LinearLayout = layoutInflater.inflate(R.layout.layout_item_question,li_answers,false) as LinearLayout
        val tvAnswer: TextView = view.findViewWithTag("tv_answer")
        tvAnswer.text = answer.content
        textViews?.add(tvAnswer)
        val checkBox: CheckBox = view.findViewWithTag("checkbox")
        if(answer.isRight) {
            CompoundButtonCompat.setButtonTintList(checkBox, rightStateList)
            checkBox.isChecked = true
        }
        checkBoxes?.add(checkBox)
        li_answers.addView(view)
        if(type.equals(WRONG_ANSWERS) && !answer.isRight && answer.userClicked != answer.isRight) {
            Log.d(TAG_LOG,"change checkbox color")
            Log.d(TAG_LOG,"content tv = ${tvAnswer.text}")
            Log.d(TAG_LOG,"answer.isRight = ${answer.isRight} and userClick = ${answer.userClicked}")
            CompoundButtonCompat.setButtonTintList(checkBox, colorStateList)
            checkBox.isChecked = true
        }
        checkBox.isEnabled = false
    }

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = AnswersFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
