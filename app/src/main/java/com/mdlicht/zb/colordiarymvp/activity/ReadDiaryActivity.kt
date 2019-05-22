package com.mdlicht.zb.colordiarymvp.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.adapter.ReadDiaryVpAdapter
import com.mdlicht.zb.colordiarymvp.common.showToast
import com.mdlicht.zb.colordiarymvp.constract.ReadDiaryContract
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository
import com.mdlicht.zb.colordiarymvp.dialog.DiaryDialog
import com.mdlicht.zb.colordiarymvp.presenter.ReadDiaryPresenter
import kotlinx.android.synthetic.main.activity_read_diary.*
import kotlinx.android.synthetic.main.toolbar_basic.*
import kotlinx.android.synthetic.main.toolbar_basic.ivBack
import kotlinx.android.synthetic.main.toolbar_basic.view.*

class ReadDiaryActivity : BaseActivity(), ReadDiaryContract.View {
    private lateinit var presenter: ReadDiaryContract.Presenter

    companion object {
        const val KEY_DATE = "date"
        const val KEY_DIARY = "diary"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_diary)
        initPresenter()
    }

    override fun initPresenter() {
        val date = intent.getStringExtra(KEY_DATE)
        val diary = intent.getParcelableExtra<Diary>(KEY_DIARY)
        presenter = ReadDiaryPresenter(this, DiaryRepository(this), date, diary).apply {
            getDiarySet().observe(this@ReadDiaryActivity, Observer {
                if(it.isNullOrEmpty()) {
                    this@ReadDiaryActivity.showToast(getString(R.string.msg_empty_diary_database))
                    finish()
                    return@Observer
                }
                vpContainer.apply {
                    (adapter as ReadDiaryVpAdapter).apply {
                        setDiarySet(it)
                        val index = getPositionByDate(presenter.getDate()!!)
                        if (count > index) {
                            presenter.updateDate(getItemByIndex(index)?.date)
                            presenter.updateDiary(getItemByIndex(index))
                            currentItem = index
                        }
                    }
                }
            })
        }
    }

    override fun initView() {
        setSupportActionBar(toolbarWrapper as Toolbar)

        (toolbarWrapper as Toolbar).apply {
            ivBack.setOnClickListener {
                finish()
            }
        }

        tvModify.setOnClickListener {
            presenter.getDiary()?.let {
                DiaryDialog.newInstance(it.uid, it.contents, it.feel, it.date).apply {
                    setDiaryDialogListener(object : DiaryDialog.OnDiaryDialogListener {
                        override fun onWriteClick() {
                            this@ReadDiaryActivity.vpContainer.apply {
                                (adapter as ReadDiaryVpAdapter).apply {
                                    currentItem = getPositionByDate(it.date)
                                    presenter.updateDate(((adapter!! as ReadDiaryVpAdapter).getItemByIndex(currentItem))?.date)
                                    presenter.updateDiary((adapter!! as ReadDiaryVpAdapter).getItemByIndex(currentItem))
                                }
                            }
                            dismissAllowingStateLoss()
                        }

                        override fun onCloseClick() {
                            dismissAllowingStateLoss()
                        }
                    })
                }.show(supportFragmentManager, null)
            }
        }

        tvDelete.setOnClickListener {
            presenter.getDiary()?.let {
                AlertDialog.Builder(this@ReadDiaryActivity).setMessage(R.string.msg_delete_diary)
                    .setPositiveButton(R.string.common_yes) { dialog, which ->
                        presenter.deleteDiary(it)
                        dialog.dismiss()
                    }.setNegativeButton(R.string.common_no) { dialog, which ->
                        dialog.dismiss()
                    }.show()
            }
        }

        vpContainer.apply {
            adapter = ReadDiaryVpAdapter()
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(p0: Int) {

                }

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                }

                override fun onPageSelected(p0: Int) {
                    (adapter!! as ReadDiaryVpAdapter).apply {
                        presenter.updateDate(getItemByIndex(p0)?.date)
                        presenter.updateDiary(getItemByIndex(p0))
                    }
                }
            })
        }
    }

    override fun updateDate(date: String?) {
        (toolbarWrapper as Toolbar).tvTitle.text = date
    }
}
