package com.example.quiz.presentation.ui.test.test_list.tab_fragment.ended_test_list

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.Const
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.currentUser
import com.example.quiz.presentation.util.Const.gson
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import com.google.gson.reflect.TypeToken
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class EndedTestsPresenter @Inject constructor() : BasePresenter<EndedTestsView>() {
    @Inject
    lateinit var testRepository: TestRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var prefs: SharedPreferences

    fun loadTests() {
       /* testRepository
            .findByUser(userId)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({
                viewState.changeDataSet(it)
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()*/
        val type = object : TypeToken<List<Test>>(){}.type
        val listStr = prefs.getString(Const.TESTS, "")
        var list: MutableList<Test> = ArrayList()
        if(!listStr.equals("")) {
            list = Const.gson.fromJson(listStr, type)
        }
        val userList: MutableList<Test> = ArrayList()
        var testStr = ""
        var userTest = Test()
        var key = ""
        for(item in list) {
            key = "${currentUser.email}${item.name}"
            testStr = prefs.getString(key, "")
            Log.d(TAG_LOG, "testStr = $testStr")
            if(!testStr.equals("")) {
                userTest = gson.fromJson(testStr, Test::class.java)
                userList.add(userTest)
            }
        }
        Log.d(TAG_LOG, "userList.size = ${userList.size}")
        viewState.showItems(userList)
    }

    fun onTestClick(args: Bundle) {
        router.navigateTo(Screens.TestScreen(args))
    }

    fun onAddTestClick() {
        router.navigateTo(Screens.AddTestScreen(null))
    }

}