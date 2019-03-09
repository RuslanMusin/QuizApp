package com.example.quiz.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.quiz.R
import com.example.quiz.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_profile.*

class StudentFragment: BaseFragment(), StudentView, View.OnClickListener {

    @InjectPresenter
    lateinit var studentPresenter: StudentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomVisibility(true)
        initViews()
    }

    private fun initViews() {
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
        Navigation.findNavController(btn_quit).navigate(R.id.action_studentFragment2_to_loginFragment)
    }
}