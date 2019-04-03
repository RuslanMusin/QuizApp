package com.example.quiz.presentation.ui.bottom

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.ui.common.BackButtonListener
import com.example.quiz.presentation.ui.common.RouterProvider
import com.example.quiz.presentation.ui.forward.ForwardPresenter
import com.example.quiz.presentation.ui.forward.ForwardView

class ForwardFragment : BaseFragment(), ForwardView, BackButtonListener {

    private var toolbar: Toolbar? = null
    private var chainTV: TextView? = null
    private var forwardBt: View? = null
    private var githubBt: View? = null

    @InjectPresenter
    lateinit var presenter: ForwardPresenter

    @ProvidePresenter
    internal fun provideForwardPresenter(): ForwardPresenter {
        return ForwardPresenter(
            getArguments()?.getString(EXTRA_NAME)!!,
            (getParentFragment() as RouterProvider).router,
            getArguments()?.getInt(EXTRA_NUMBER)!!
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forward, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        chainTV = view.findViewById<View>(R.id.chain_text) as TextView
        forwardBt = view.findViewById(R.id.forward_button)
        githubBt = view.findViewById(R.id.github_button)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar!!.setTitle(getArguments()?.getString(EXTRA_NAME))
        toolbar!!.setNavigationOnClickListener { presenter!!.onBackPressed() }
        forwardBt!!.setOnClickListener { presenter!!.onForwardPressed() }
        githubBt!!.setOnClickListener { presenter!!.onGithubPressed() }
    }

    override fun setChainText(chainText: String) {
        chainTV!!.text = chainText
    }

    override fun onBackPressed(): Boolean {
        presenter!!.onBackPressed()
        return true
    }

    companion object {
        private val EXTRA_NAME = "extra_name"
        private val EXTRA_NUMBER = "extra_number"

        fun getNewInstance(name: String, number: Int): ForwardFragment {
            val fragment = ForwardFragment()

            val arguments = Bundle()
            arguments.putString(EXTRA_NAME, name)
            arguments.putInt(EXTRA_NUMBER, number)
            fragment.setArguments(arguments)

            return fragment
        }
    }
}
