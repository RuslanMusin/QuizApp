package com.example.quiz.presentation.ui.test.test_item.before_feedback

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.util.Const
import kotlinx.android.synthetic.main.fragment_before_feedback.*
import javax.inject.Inject
import javax.inject.Provider

class BeforeFeedbackFragment : BaseFragment(), BeforeFeedbackView, BackButtonListener, View.OnClickListener {

    lateinit var test: Test

    @InjectPresenter
    lateinit var presenter: BeforeFeedbackPresenter
    @Inject
    lateinit var presenterProvider: Provider<BeforeFeedbackPresenter>
    @ProvidePresenter
    fun providePresenter(): BeforeFeedbackPresenter = presenterProvider.get()

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = BeforeFeedbackFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onBackPressed(): Boolean {
        shouldCancel()
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_before_feedback, container, false)

        val testStr: String? = arguments?.getString(Const.TEST_ITEM)
        test = Const.gson.fromJson(testStr, Test::class.java)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews(view: View) {
        setActionBar(toolbar)
        toolbar.title = test.name
        toolbar.setNavigationOnClickListener { shouldCancel() }
    }

    private fun setListeners() {
        btn_evaluate.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_evaluate -> {
                val args: Bundle = Bundle()
                args.putString(Const.TEST_ITEM, Const.gson.toJson(test))
                args.putInt(Const.QUESTION_NUMBER,0)
                presenter.onFeedbackClick(args)

            }
        }
    }

    fun shouldCancel() {
        MaterialDialog.Builder(activity as Context)
            .title(R.string.question_dialog_title)
            .content(R.string.question_dialog_content)
            .positiveText(R.string.agree)
            .negativeText(R.string.disagree)
            .onPositive(object : MaterialDialog.SingleButtonCallback {
                override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                    presenter.onTestClick(test.id)
                }

            })
            .show()
    }


}