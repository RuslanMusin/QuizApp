package com.example.quiz.presentation.ui.test.test_list

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.base.recycler.ReloadableView
import com.example.quiz.presentation.base.recycler.SearchListener
import com.example.quiz.presentation.ui.test.test_list.tab_fragment.all_test_list.AllTestsFragment
import com.example.quiz.presentation.ui.test.test_list.tab_fragment.all_test_list.AllTestsPresenter
import com.example.quiz.presentation.ui.test.test_list.tab_fragment.ended_test_list.EndedTestsFragment
import com.example.quiz.presentation.util.Const.TAG_LOG
import kotlinx.android.synthetic.main.fragment_theme_tabs.*
import javax.inject.Inject
import javax.inject.Provider

class TestListFragment : BaseFragment(), TestListView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: TestListPresenter
    @Inject
    lateinit var presenterProvider: Provider<TestListPresenter>
    @ProvidePresenter
    fun providePresenter(): TestListPresenter = presenterProvider.get()

    private var fragments: MutableList<Fragment> = ArrayList()
    lateinit private var currentFragment: SearchListener

    override fun onBackPressed(): Boolean {
        presenter.onBackClick()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_theme_tabs, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setActionBar(toolbar)
        toolbar.setNavigationOnClickListener { presenter.onBackClick() }
        setupViewPager(viewpager)
        tab_layout.setupWithViewPager(viewpager)
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        setTabListener()
    }

    private fun setTabListener() {
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d(TAG_LOG, "on tab selected")
                viewpager.currentItem = tab.position
                (fragments[tab.position] as ReloadableView).reloadList()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = FragViewPagerAdapter(childFragmentManager)
        fragments.add(AllTestsFragment.newInstance())
        fragments.add(EndedTestsFragment.newInstance())
        adapter.addFragment(fragments[0], getString(R.string.all_tests))
        adapter.addFragment(fragments[1], getString(R.string.my_tests))
        viewPager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        menu?.let { setSearchMenuItem(it) }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setSearchMenuItem(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)

        val searchView: SearchView = searchItem.actionView as SearchView
        val finalSearchView = searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
//                presenter.loadOfficialTestsByQUery(query)

                if (!finalSearchView.isIconified) {
                    finalSearchView.isIconified = true
                }
                searchItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val pos = viewpager.currentItem
                currentFragment = fragments[pos] as SearchListener
                currentFragment.findByQuery(newText)
                return false
            }
        })

    }

    companion object {

        fun newInstance(): Fragment {
            val fragment = TestListFragment()
            return fragment
        }
    }

}