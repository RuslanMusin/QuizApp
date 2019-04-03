package com.example.quiz.presentation.ui.bottom

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quiz.R
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.ui.common.BackButtonListener
import com.example.quiz.presentation.ui.common.RouterProvider
import com.example.quiz.presentation.ui.subnavigation.LocalCiceroneHolder
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class TabContainerFragment : Fragment(), RouterProvider, BackButtonListener {

    private var navigator: Navigator? = null

    @Inject
    lateinit var ciceroneHolder: LocalCiceroneHolder

    private lateinit var containerName: String

    private lateinit var cicerone: Cicerone<Router>
    override lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
//        SampleApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            containerName = it.getString(EXTRA_NAME)

        }
        ciceroneHolder.getCicerone(containerName)?.let {
            cicerone = it
        }
        router = cicerone.router

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.ftc_container) == null) {
            cicerone.router.replaceScreen(Screens.ForwardScreen(containerName, 0))
        }
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun getNavigator(): Navigator {
        if (navigator == null) {
            navigator = SupportAppNavigator(activity, childFragmentManager, R.id.ftc_container)
        }
        return navigator as Navigator
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.ftc_container)
        if (fragment != null
            && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return true
        } else {
            (activity as RouterProvider).router.exit()
            return true
        }
    }

    companion object {
        private val EXTRA_NAME = "tcf_extra_name"

        fun getNewInstance(name: String): TabContainerFragment {
            val fragment = TabContainerFragment()

            val arguments = Bundle()
            arguments.putString(EXTRA_NAME, name)
            fragment.arguments = arguments

            return fragment
        }
    }
}
