package com.example.quiz.presentation.ui.test.add_test.question

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.model.test.Question
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.model.test.Answer
import com.example.quiz.presentation.ui.test.add_test.main.AddMainTestPresenter
import com.example.quiz.presentation.util.Const.QUESTION_NUMBER
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.TEST_ITEM
import com.example.quiz.presentation.util.Const.TEST_MANY_TYPE
import com.example.quiz.presentation.util.Const.TEST_ONE_TYPE
import com.example.quiz.presentation.util.Const.gson
import com.google.gson.Gson
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.fragment_add_question.*
import kotlinx.android.synthetic.main.toolbar_back_cancel_forward.*
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Provider

class AddQuestionTestFragment : BaseFragment(), AddQuestionTestView,
    BackButtonListener, View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: AddQuestionTestPresenter
    @Inject
    lateinit var presenterProvider: Provider<AddQuestionTestPresenter>
    @ProvidePresenter
    fun providePresenter(): AddQuestionTestPresenter = presenterProvider.get()

    lateinit var test: Test
    private lateinit var question: Question
    private var number: Int = 0

    private var answers: MutableList<Answer> = ArrayList()
    private var rightAnswers: MutableList<String> = ArrayList()
    private var answerSize: Int = 0

    private var editTexts: MutableList<EditText> = ArrayList()
    private var checkBoxes: MutableList<CheckBox> = ArrayList()
    private var radioButtons: MutableList<RadioButton> = ArrayList()

    private var testType: String = TEST_ONE_TYPE

    private lateinit var liParams: LinearLayout.LayoutParams
    private lateinit var tiParams: LinearLayout.LayoutParams
    private lateinit var etParams: LinearLayout.LayoutParams
    private lateinit var rbParams: LinearLayout.LayoutParams

    private lateinit var checkListener: View.OnClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_question, container, false)

        arguments?.let {
            test = gson.fromJson(it.getString(TEST_ITEM), Test::class.java)
            number = it.getInt(QUESTION_NUMBER)
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setListeners()
        if(number >= 2) {
            btn_ok.visibility = View.VISIBLE
//            (activity as ChangeToolbarListener).showOk(true)
        } else {
            btn_ok.visibility = View.GONE
//            (activity as ChangeToolbarListener).showOk(false)
        }

        if(test.questions.size > number) {
            question = test.questions[number]
            setQuestionData()
        } else {
            question = Question()
            test.questions.add(question)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setQuestionData() {
        et_question.setText(question.description)
        for(i in question.answers.indices) {
            if(i >= checkBoxes.size) {
                addAnswer()
            }
            checkBoxes[i].isChecked = question.answers?.get(i)?.isRight
            editTexts[i].setText(question.answers?.get(i).text)
        }
        /*for(i in question.rightAnswers) {
            checkBoxes[i.toInt()].isChecked = true
        }*/
    }

    private fun initViews(view: View) {
        setActionBar(toolbar_back_cancel_forward)
        toolbar_title.text = getString(R.string.add_question_number, number + 1)
        spinner.setItems(getString(R.string.test_type_one), getString(R.string.test_type_many))

        answers = ArrayList()
        editTexts = ArrayList()
        radioButtons = ArrayList()
        checkBoxes = ArrayList()

        setStartAnswers()
    }

    private fun setStartAnswers() {
        checkListener = object: View.OnClickListener{
            override fun onClick(v: View?) {
                if(testType.equals(TEST_ONE_TYPE)) {
                    Log.d(TAG_LOG,"change on one type")
                    changeToOneType(v as CheckBox)

                }
            }

        }


        for (i in 0..2) {
            addAnswer()

        }
    }


    private fun setListeners() {
        btn_add_answer.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
        btn_back.setOnClickListener(this)
        btn_forward.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        spinner?.setOnItemSelectedListener(object : MaterialSpinner.OnItemSelectedListener<Any> {
            override fun onItemSelected(view: MaterialSpinner?, position: Int, id: Long, item: Any?) {
                when (position) {
                    0 -> {
                        changeToOneType(null)
                    }

                    1 -> {
                        changeToManyType()
                    }
                }
            }

        })
    }

    private fun changeToManyType() {
        testType = TEST_MANY_TYPE

    }

    private fun changeToOneType(check: CheckBox?) {
        Log.d(TAG_LOG,"change to one type")
        testType = TEST_ONE_TYPE
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

    private fun finishQuestions() {
        prepareQuestion()
//        addTestView!!.createTest()
        Log.d(TAG_LOG, "after preparing")
        if(checkQuestion()) {
            Log.d(TAG_LOG, "create test")
            presenter.createTest(test)
        }
    }

    override fun navigateToTest() {
//        removeStackDownTo()
        val args = Bundle()
        args.putString(TEST_ITEM, gson.toJson(test))
        presenter.onTestClick(args)
    }

    private fun nextQuestion() {
        if(number <= 9) {
            prepareQuestion()
            if(checkQuestion()) {
                val args: Bundle = Bundle()
                args.putString(TEST_ITEM, gson.toJson(test))
                args.putInt(QUESTION_NUMBER, ++number)
                presenter.onNextQuestionClick(args)
            }
        }
    }

    private fun checkQuestion(): Boolean {
        var flag: Boolean = true
        if(question.description == null || question.description?.trim().equals("")) {
            ti_question.error = "Введите вопрос!"
            flag = false
        } else {
            ti_question.error = null
        }
        var count: Int = 0
        for(i in question.answers.indices) {
            if(question.answers[i].isRight) {
                count++
            }
            if(question.answers[i].text == null || question.answers[i].text?.trim().equals("")) {
                editTexts[i].error = "Напишите вариант ответа"
                flag = false
            }else {
                editTexts[i].error = null
            }
        }
        if(count == 0) {
            flag = false
            showSnackBar("Выберите хотя бы один ответ!")
        }

        answers.clear()

        return flag
    }

    private fun beforeQuestion() {
//        removeStackDownTo(1)
        val args = Bundle()
        args.putString(TEST_ITEM, gson.toJson(test))
        args.putInt(QUESTION_NUMBER, --number)
        presenter.onBeforeQuestionClick(args)
    /*    model.selectTest(test)
        model.selectNumber(--number)
        super.performBackPressed()*/
    }

    override fun onClick(v: View) {


        when (v.id) {

            R.id.btn_ok -> {
                Log.d(TAG_LOG, "finish quetions")
                finishQuestions()
            }

            R.id.btn_forward -> {
                nextQuestion()
            }

            R.id.btn_back -> {
                beforeQuestion()

                /*if(number != 0) {
                    beforeQuestion()
                } else {
                    val args: Bundle = Bundle()
                    args.putString(TEST_ITEM, gson.toJson(test))
                    val fragment = AddMainTestFragment.newInstance(args)

//                    (activity as BaseBackActivity).changeFragment(fragment, ADD_TEST_FRAGMENT)
                }*/
            }

            R.id.btn_cancel -> {
                MaterialDialog.Builder(activity as Context)
                    .title(R.string.question_dialog_title)
                    .content(R.string.question_dialog_content)
                    .positiveText(R.string.agree)
                    .negativeText(R.string.disagree)
                    .onPositive(object : MaterialDialog.SingleButtonCallback {
                        override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                            presenter.onTestListClick()
                        }

                    })
                    .show()
            }

            R.id.btn_add_answer -> {
                if(answerSize < 5) {
                    addAnswer()

                }

            }

        }
    }

    /* fun finishQuestion() {
         prepareQuestion()
         addTestView!!.createTest()
     }
 */
    private fun addAnswer() {
        answerSize++
        val view: View = layoutInflater.inflate(R.layout.layout_item_add_question,li_answers,false)
        val editText: EditText = view.findViewById(R.id.et_answer)
        val checkBox: CheckBox = view.findViewById(R.id.checkbox)

        checkBox.setOnClickListener(checkListener)

        editTexts?.add(editText)
        checkBoxes?.add(checkBox)

        li_answers.addView(view)
    }


    private fun prepareQuestion() {

        for (i in checkBoxes!!.indices) {

            val answer = Answer()
            answer.text = editTexts!![i].text.toString()
            if (checkBoxes!![i].isChecked) {
//                rightAnswers.add(i.toString())
                answer.isRight = true
            }
            answers!!.add(answer)
        }

        question!!.description = et_question.text.toString()
        question!!.answers = answers.toMutableList()
        question.id = number.toString()

    }

    override fun onBackPressed(): Boolean {
        beforeQuestion()
        return true
    }

    companion object {

        private val RESULT_LOAD_IMG = 0

        fun newInstance(args: Bundle): Fragment {
            val fragment = AddQuestionTestFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
