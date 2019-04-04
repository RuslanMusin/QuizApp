package com.example.quiz.presentation.ui.main.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.ui.auth.signup.SignUpFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: BaseFragment(), ProfileView, View.OnClickListener {

    @InjectPresenter
    lateinit var studentPresenter: ProfilePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
//        setBottomVisibility(true)
        setActionBar(toolbar)
        setToolbarTitle(R.string.menu_profile)
        setListeners()
    }

    private fun setListeners() {
        btn_quit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {

            R.id.btn_quit -> studentPresenter.logout()
        }
    }

    override fun logout() {

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