package com.example.quiz.presentation.ui.test.test_list.tab_fragment.all_test_list

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.ui.test.test_list.tab_fragment.TestAdapter
import com.example.quiz.presentation.util.Const
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.gson
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_theme_list.*
import kotlinx.android.synthetic.main.layout_recycler_list.*
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Provider

class AllTestsFragment : BaseFragment(), AllTestsView, View.OnClickListener {

    private lateinit var adapter: TestAdapter

    var tests: MutableList<Test> = ArrayList()
    lateinit var userId: String

    @InjectPresenter
    lateinit var presenter: AllTestsPresenter
    @Inject
    lateinit var presenterProvider: Provider<AllTestsPresenter>
    @ProvidePresenter
    fun providePresenter(): AllTestsPresenter = presenterProvider.get()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_theme_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.loadTests()
    }

    override fun reloadList() {
        if(tests.size > 0) {
            presenter.loadTests()
        }
    }

    private fun initViews() {
        initRecycler()
        setListeners()
    }

    private fun setListeners() {
        btn_add_theme.setOnClickListener(this)
    }

    override fun setNotLoading() {

    }

    override fun showLoading(disposable: Disposable) {
        pb_list.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_list.visibility = View.GONE
    }

    override fun loadNextElements(i: Int) {
    }


    override fun changeDataSet(tests: List<Test>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun showItems(tests: List<Test>) {
        this.tests = tests.toMutableList()
        changeDataSet(tests)
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = TestAdapter(ArrayList())
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)

        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && btn_add_theme.isShown())
                    btn_add_theme.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    btn_add_theme.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    override fun onItemClick(item: Test) {
        val args = Bundle()
        args.putString(Const.TEST_ITEM, gson.toJson(item))
        presenter.onTestClick(args)
    }

    override fun findByQuery(query: String) {
        val pattern: Pattern = Pattern.compile("${query.toLowerCase()}.*")
        val list: MutableList<Test> = java.util.ArrayList()
        for(skill in tests) {
            if(pattern.matcher(skill.name?.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        Log.d(TAG_LOG, "list.size = ${list.size}")
        changeDataSet(list)
    }

    override fun onClick(view: View) {
        when (view.id) {

            R.id.btn_add_theme -> {
                presenter.onAddTestClick()
            }
        }
    }

    companion object {

        fun newInstance(): Fragment {
            val fragment = AllTestsFragment()
            return fragment
        }
    }
}
