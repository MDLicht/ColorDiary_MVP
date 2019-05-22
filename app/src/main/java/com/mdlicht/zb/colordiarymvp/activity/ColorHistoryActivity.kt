package com.mdlicht.zb.colordiarymvp.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.adapter.ColorHistoryCountRvAdapter
import com.mdlicht.zb.colordiarymvp.adapter.ColorHistoryRvAdapter
import com.mdlicht.zb.colordiarymvp.adapter.YearRvAdapter
import com.mdlicht.zb.colordiarymvp.common.GridDividerDecoration
import com.mdlicht.zb.colordiarymvp.constract.ColorHistoryContract
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository
import com.mdlicht.zb.colordiarymvp.presenter.ColorHistoryPresenter
import com.mdlicht.zb.colordiarymvp.view.MaxHeightRecyclerView
import kotlinx.android.synthetic.main.activity_color_history.*
import kotlinx.android.synthetic.main.toolbar_color_history.view.*

class ColorHistoryActivity : BaseActivity(), ColorHistoryContract.View {
    lateinit var presenter: ColorHistoryContract.Presenter

    private val expandConstraintSet: ConstraintSet by lazy {
        ConstraintSet().apply {
            load(this@ColorHistoryActivity, R.layout.activity_color_history_expand)
        }
    }
    private val collapseConstraintSet: ConstraintSet by lazy {
        ConstraintSet().apply {
            load(this@ColorHistoryActivity, R.layout.activity_color_history)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_history)
        initPresenter()
    }

    override fun initPresenter() {
        presenter = ColorHistoryPresenter(this, DiaryRepository(this)).apply {
            getDate().observe(this@ColorHistoryActivity, Observer {
                (toolbarWrapper as Toolbar).tvTitle.text = it
                rvYear?.adapter?.let {adapter ->
                    (adapter as YearRvAdapter).setSelectedYear(it)
                }
                rvColorHistory?.adapter?.let {adapter ->
                    (adapter as ColorHistoryRvAdapter).setSelectedYear(it)
                }
            })
            getDiarySet().observe(this@ColorHistoryActivity, Observer {
                (rvColorHistory.adapter as ColorHistoryRvAdapter).dataSet = it
                it?.let { dataSet ->val map: MutableMap<String, Int> = mutableMapOf()
                    val feelings = resources.getStringArray(R.array.feelings)
                    for(feel in feelings) {
                        val count: Int = dataSet.filter { diary ->  diary.feel == feel }.size
                        map[feel] = count
                    }
                    this.updateColorCountMap(map)
                }
            })
            getColorCountMap().observe(this@ColorHistoryActivity, Observer {
                (rvFeelCount.adapter as ColorHistoryCountRvAdapter).setDataMap(it)
            })
        }
    }

    override fun initView() {
        setSupportActionBar(toolbarWrapper as Toolbar)

        (toolbarWrapper as Toolbar).apply {
            this.tvTitle.setOnClickListener {
                if (rvYear.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(clRoot, ChangeBounds().apply { duration = 100 })
                    collapseConstraintSet.applyTo(clRoot)
                } else {
                    TransitionManager.beginDelayedTransition(clRoot, ChangeBounds().apply { duration = 100 })
                    expandConstraintSet.applyTo(clRoot)
                }
            }

            this.ivBack.setOnClickListener {
                finish()
            }
        }

        (rvYear as MaxHeightRecyclerView).apply {
            adapter = YearRvAdapter(this@ColorHistoryActivity)
            scrollToPosition((adapter as YearRvAdapter).itemCount - 1)
        }

        rvColorHistory.apply {
            adapter = ColorHistoryRvAdapter(this@ColorHistoryActivity)
            addItemDecoration(
                GridDividerDecoration(
                    resources.getDimension(R.dimen.divier_width).toInt(),
                    (layoutManager as GridLayoutManager).spanCount
                )
            )
            setHasFixedSize(true)
        }

        rvFeelCount.apply {
            layoutManager = LinearLayoutManager(this@ColorHistoryActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ColorHistoryCountRvAdapter()
        }
    }
}
