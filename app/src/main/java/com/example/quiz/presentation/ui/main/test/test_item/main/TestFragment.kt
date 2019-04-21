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
import com.example.quiz.presentation.util.Const.QUESTION_NUMBER
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.TEST_ITEM
import com.example.quiz.presentation.util.Const.currentUser
import com.example.quiz.presentation.util.Const.gson
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.layout_expandable_text_view.*
import kotlinx.android.synthetic.main.layout_test.*
import javax.inject.Inject
import javax.inject.Provider



class TestFragment : BaseFragment(), TestView, BackButtonListener, View.OnClickListener {

    lateinit var test: Test

    @InjectPresenter
    lateinit var presenter: TestPresenter
    @Inject
    lateinit var presenterProvider: Provider<TestPresenter>
    @ProvidePresenter
    fun providePresenter(): TestPresenter = presenterProvider.get()

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = TestFragment()
            fragment.arguments = args
            return fragment
        }
    }

  /*  override fun onBackPressed() {
        *//* val args: Bundle = Bundle()
         args.putString(TEST_JSON, gsonConverter.toJson(test))
         val fragment = FinishFragment.newInstance(args)
         (activity as BaseBackActivity).changeFragment(fragment)*//*

        TestListActivity.start(activity as Activity)
    }*/

    override fun onBackPressed(): Boolean {
        presenter.onTestListClick()
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_test, container, false)

        val testStr: String? = arguments?.getString(TEST_ITEM)
        test = gson.fromJson(testStr,Test::class.java)
        Log.d(TAG_LOG, "test json = \n$testStr")
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setListeners()
        setData()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setData() {
        expand_text_view.text = test.description
        tv_name.text = test.name
        tv_questions.text = test.questions.size.toString()

        test.owner?.pk?.let {
            if(it.equals(currentUser.pk)) {
                tv_do_test.visibility = View.GONE
                tv_open_test.visibility = View.VISIBLE
            }
        }
    }

    private fun initViews(view: View) {
        setActionBar(toolbar)
        toolbar.title = test.name
        toolbar.setNavigationOnClickListener { presenter.onTestListClick() }
    }

    private fun setListeners() {
        tv_do_test.setOnClickListener(this)
        tv_open_test.setOnClickListener(this)
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
        }
    }

    override fun afterTestOpened() {
        showSnackBar(R.string.test_opened)
        tv_open_test.visibility = View.GONE
        tv_close_test.visibility = View.VISIBLE
    }

    override fun afterTestClosed() {
        showSnackBar(R.string.test_closed)
        tv_open_test.visibility = View.VISIBLE
        tv_close_test.visibility = View.GONE
    }


}


