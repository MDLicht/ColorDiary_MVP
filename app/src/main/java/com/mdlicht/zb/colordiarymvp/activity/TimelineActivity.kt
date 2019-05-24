package com.mdlicht.zb.colordiarymvp.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.adapter.TimelinePagedRvAdapter
import com.mdlicht.zb.colordiarymvp.constract.TimelineContract
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository
import com.mdlicht.zb.colordiarymvp.presenter.TimelinePresenter
import kotlinx.android.synthetic.main.activity_timeline.*
import kotlinx.android.synthetic.main.toolbar_simple.view.*

class TimelineActivity : BaseActivity(), TimelineContract.View {
    lateinit var presenter: TimelineContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        initPresenter()
    }

    override fun initPresenter() {
        presenter = TimelinePresenter(this, DiaryRepository(this)).apply {
            getPagedTimeline().observe(this@TimelineActivity, Observer {
                (rvTimeline.adapter as TimelinePagedRvAdapter).submitList(it)
                if(it.isNullOrEmpty()) {
                    tvEmptyGuide.visibility = View.VISIBLE
                } else {
                    tvEmptyGuide.visibility = View.GONE
                }
            })
        }
    }

    override fun initView() {
        setSupportActionBar(toolbarWrapper as Toolbar)

        (toolbarWrapper as Toolbar).apply {
            tvTitle.text = getString(R.string.drawer_menu_timeline)
            ivBack.setOnClickListener {
                finish()
            }
        }

        rvTimeline.apply {
            layoutManager = LinearLayoutManager(this@TimelineActivity)
            adapter = TimelinePagedRvAdapter()
            addItemDecoration(DividerItemDecoration(this@TimelineActivity, DividerItemDecoration.VERTICAL))
        }
    }
}
