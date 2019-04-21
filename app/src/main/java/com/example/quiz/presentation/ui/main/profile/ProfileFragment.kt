package com.example.quiz.presentation.ui.main.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.ui.auth.signup.SignUpFragment
import com.example.quiz.presentation.ui.test.test_item.before_feedback.BeforeFeedbackPresenter
import com.example.quiz.presentation.util.Const
import com.example.quiz.presentation.util.Const.currentUser
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject
import javax.inject.Provider

class ProfileFragment: BaseFragment(), ProfileView, View.OnClickListener, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: ProfilePresenter
    @Inject
    lateinit var presenterProvider: Provider<ProfilePresenter>
    @ProvidePresenter
    fun providePresenter(): ProfilePresenter = presenterProvider.get()

    override fun onBackPressed(): Boolean {
        presenter.onBackClick()
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setData()
    }

    private fun initViews() {
        setActionBar(toolbar)
        setListeners()
    }

    private fun setData() {
        tv_email.text = currentUser.email
        tv_first_name.text = currentUser.name
        tv_last_name.text = currentUser.lastname
        tv_name.text = currentUser.getFullName()
    }

    private fun setListeners() {
        li_logout.setOnClickListener(this)
        li_tests.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {

            R.id.li_logout -> presenter.logout()

            R.id.li_tests -> presenter.onTestListClick()
        }
    }

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): Fragment {
            val fragment = ProfileFragment()
            return fragment
        }
    }
}