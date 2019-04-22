package com.example.quiz.presentation.ui.main.testresult.author

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.model.result.AuthorQuestion
import com.example.quiz.presentation.model.result.AuthorResult
import com.example.quiz.presentation.util.BarChartFormatter
import com.example.quiz.presentation.util.Const.TEST_ITEM
import com.example.quiz.presentation.util.parseToLocalDate
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_author_test_result.*
import kotlinx.android.synthetic.main.layout_progress_error.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class AuthorResultFragment : BaseFragment(), AuthorResultView {

    companion object {
        fun getInstance(args: Bundle): AuthorResultFragment {
            val fragment = AuthorResultFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: AuthorResultPresenter

    @Inject
    lateinit var presenterProvider: Provider<AuthorResultPresenter>

    @ProvidePresenter
    fun providePresenter(): AuthorResultPresenter = presenterProvider.get()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_author_test_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val testId = it.getInt(TEST_ITEM)
            presenter.id = testId
            presenter.update()

        }
        setActionBar(toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    override fun showBarChartAnswers(questions: List<AuthorQuestion>) {
        bar_chart.apply {
            description.isEnabled = false
            setMaxVisibleValueCount(15)
            setPinchZoom(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f

            axisLeft.axisMinimum = 0f
            axisLeft.granularity = 1f
            axisLeft.axisMaximum = questions[0].tryAnswerNumber?.toFloat() ?: 0f

            axisRight.isEnabled = false
            isAutoScaleMinMaxEnabled = false
            animateY(1500)
            legend.isEnabled = false
        }

        val values = mutableListOf<BarEntry>()

        for (i in 0 until questions.size) {
            values.add(BarEntry((i + 1f), questions[i].rightAnswerNumber?.toFloat() ?: 0f))
        }

        val set = BarDataSet(values, "Test").apply {
            barBorderWidth = 1f
            colors = ColorTemplate.VORDIPLOM_COLORS.toList()
            setDrawValues(false)
        }

        val dataSet = listOf<IBarDataSet>(set)
        bar_chart.data = BarData(dataSet)
        bar_chart.setFitBars(true)
    }

    override fun showPieChartFeedbackOne(feedback: AuthorQuestion) {
        pie_chart.apply {
            setUsePercentValues(true)
            description.isEnabled = false

            setExtraOffsets(0f, 0f, 0f, 50f)

            dragDecelerationFrictionCoef = 0.9f
            centerText = feedback.description
            isDrawHoleEnabled = true
            setHoleColor(Color.parseColor("#ffffff"))
            rotationAngle = 0f
            isRotationEnabled = true
            setDrawEntryLabels(false)
            isHighlightPerTapEnabled = true
            animateY(1400, Easing.EaseInOutQuad)
        }

        pie_chart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            orientation = Legend.LegendOrientation.VERTICAL
            setDrawInside(false)
            xEntrySpace = 5f
            yEntrySpace = 0f
            yOffset = 250f
            xOffset = 20f
            textSize = 14f

        }

        val entries = mutableListOf<PieEntry>()
        val count = feedback.answers?.size ?: 0

        for (i in 0 until count) {
            val fb = feedback.answers?.get(i)
            entries.add(PieEntry(fb?.choicesNumber?.toFloat() ?: 0f, fb?.content))

        }

        val dataSet = PieDataSet(entries, "").apply {
            sliceSpace = 3f
            selectionShift = 10f
            valueTextSize = 12f
            colors = ColorTemplate.MATERIAL_COLORS.toList()
        }

        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueFormatter(BarChartFormatter())
        data.setValueTextColor(Color.WHITE)

        pie_chart.setUsePercentValues(false)
        pie_chart.data = data
    }

    override fun showPieChartFeedbackTwo(feedback: AuthorQuestion) {
        pie_chart2.apply {
            setUsePercentValues(true)
            description.isEnabled = false

            setExtraOffsets(0f, 0f, 0f, 50f)

            dragDecelerationFrictionCoef = 0.9f
            centerText = feedback.description
            isDrawHoleEnabled = true
            setHoleColor(Color.parseColor("#ffffff"))
            rotationAngle = 0f
            isRotationEnabled = true
            setDrawEntryLabels(false)
            isHighlightPerTapEnabled = true
            animateY(1400, Easing.EaseInOutQuad)
        }

        pie_chart2.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            orientation = Legend.LegendOrientation.VERTICAL
            setDrawInside(false)
            xEntrySpace = 5f
            yEntrySpace = 0f
            yOffset = 250f
            xOffset = 20f
            textSize = 14f

        }

        val entries = mutableListOf<PieEntry>()
        val count = feedback.answers?.size ?: 0

        for (i in 0 until count) {
            val fb = feedback.answers?.get(i)
            entries.add(PieEntry(fb?.choicesNumber?.toFloat() ?: 0f, fb?.content))

        }

        val dataSet = PieDataSet(entries, "").apply {
            sliceSpace = 3f
            selectionShift = 10f
            valueTextSize = 12f
            colors = ColorTemplate.MATERIAL_COLORS.toList()
        }

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pie_chart))
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.WHITE)
        data.setValueFormatter(BarChartFormatter())

        pie_chart2.setUsePercentValues(false)
        pie_chart2.data = data
    }

    override fun showPieChartFeedbackThree(feedback: AuthorQuestion) {
        pie_chart3.apply {
            setUsePercentValues(true)
            description.isEnabled = false

            setExtraOffsets(0f, 0f, 0f, 50f)

            dragDecelerationFrictionCoef = 0.9f
            centerText = feedback.description
            isDrawHoleEnabled = true
            setHoleColor(Color.parseColor("#ffffff"))
            rotationAngle = 0f
            isRotationEnabled = true
            setDrawEntryLabels(false)
            isHighlightPerTapEnabled = true
            animateY(1400, Easing.EaseInOutQuad)
        }

        pie_chart3.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            orientation = Legend.LegendOrientation.VERTICAL
            setDrawInside(false)
            xEntrySpace = 5f
            yEntrySpace = 0f
            yOffset = 250f
            xOffset = 20f
            textSize = 14f

        }

        val entries = mutableListOf<PieEntry>()
        val count = feedback.answers?.size ?: 0

        for (i in 0 until count) {
            val fb = feedback.answers?.get(i)
            entries.add(PieEntry(fb?.choicesNumber?.toFloat() ?: 0f, fb?.content))

        }

        val dataSet = PieDataSet(entries, "").apply {
            sliceSpace = 3f
            selectionShift = 10f
            valueTextSize = 12f
            colors = ColorTemplate.MATERIAL_COLORS.toList()
        }

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pie_chart))
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.WHITE)
        data.setValueFormatter(BarChartFormatter())

        pie_chart3.setUsePercentValues(false)
        pie_chart3.data = data
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showRetry(errorText: String) {
        retry_title.text = errorText
        btn_retry.setOnClickListener { presenter.onRetry() }

        progress_error.visibility = View.VISIBLE
    }

    override fun hideRetry() {
        progress_error.visibility = View.GONE
    }

    override fun setTestInfo(item: AuthorResult) {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("ru"))

        tv_author_result_name.text = item.name
        tv_author_result_desc.text = item.description
        tv_author_result_create_date_name.text = item.createDate?.parseToLocalDate()
        tv_author_result_participants.text = item.participantsNumber.toString()
    }
}