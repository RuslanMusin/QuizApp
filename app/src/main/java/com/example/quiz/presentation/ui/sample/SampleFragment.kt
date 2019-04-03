package com.example.quiz.presentation.ui.sample

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.ui.common.BackButtonListener
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SampleFragment : BaseFragment(), SampleView, BackButtonListener {

    private var toolbar: Toolbar? = null
    private var backCommandBt: View? = null
    private var forwardCommandBt: View? = null
    private var replaceCommandBt: View? = null
    private var newChainCommandBt: View? = null
    private var newRootCommandBt: View? = null
    private var forwardWithDelayCommandBt: View? = null
    private var backToCommandBt: View? = null
    private var finishChainCommandBt: View? = null

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: SamplePresenter

    var number: Int = 1

    var creationTime: Long = 0

    @ProvidePresenter
    fun createSamplePresenter(): SamplePresenter {
        return SamplePresenter(router, getArguments()?.getInt(EXTRA_NUMBER)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        SampleApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar) as Toolbar
        backCommandBt = view.findViewById(R.id.back_command)
        forwardCommandBt = view.findViewById(R.id.forward_command)
        replaceCommandBt = view.findViewById(R.id.replace_command)
        newChainCommandBt = view.findViewById(R.id.new_chain_command)
        newRootCommandBt = view.findViewById(R.id.new_root_command)
        forwardWithDelayCommandBt = view.findViewById(R.id.forward_delay_command)
        backToCommandBt = view.findViewById(R.id.back_to_command)
        finishChainCommandBt = view.findViewById(R.id.finish_chain_command)

        arguments?.let {
            creationTime = it.getLong(EXTRA_TIME, 0L)
            number = it.getInt(EXTRA_NUMBER)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar!!.setNavigationOnClickListener(View.OnClickListener { presenter!!.onBackCommandClick() })
        backCommandBt!!.setOnClickListener { presenter!!.onBackCommandClick() }
        forwardCommandBt!!.setOnClickListener { presenter!!.onForwardCommandClick() }
        replaceCommandBt!!.setOnClickListener { presenter!!.onReplaceCommandClick() }
        newChainCommandBt!!.setOnClickListener { presenter!!.onNewChainCommandClick() }
        newRootCommandBt!!.setOnClickListener { presenter!!.onNewRootCommandClick() }
        forwardWithDelayCommandBt!!.setOnClickListener { presenter!!.onForwardWithDelayCommandClick() }
        backToCommandBt!!.setOnClickListener { presenter!!.onBackToCommandClick() }
        finishChainCommandBt!!.setOnClickListener { presenter!!.onFinishChainCommandClick() }
    }

    override fun setTitle(title: String) {
        toolbar!!.setTitle(title)
    }

    override fun onBackPressed(): Boolean {
        presenter!!.onBackCommandClick()
        return true
    }

    companion object {
        private val EXTRA_NUMBER = "extra_number"
        private val EXTRA_TIME = "extra_time"

        fun getNewInstance(number: Int): SampleFragment {
            val fragment = SampleFragment()

            val args = Bundle()
            args.putInt(EXTRA_NUMBER, number)
            args.putLong(EXTRA_TIME, System.currentTimeMillis())
            fragment.setArguments(args)

            return fragment
        }
    }
}
