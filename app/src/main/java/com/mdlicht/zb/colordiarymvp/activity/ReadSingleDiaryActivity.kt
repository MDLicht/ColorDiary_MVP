package com.mdlicht.zb.colordiarymvp.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.constract.ReadSingleDiaryContract
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository
import com.mdlicht.zb.colordiarymvp.dialog.DiaryDialog
import com.mdlicht.zb.colordiarymvp.presenter.ReadSingleDiaryPresenter
import kotlinx.android.synthetic.main.activity_read_single_diary.*
import kotlinx.android.synthetic.main.toolbar_simple.view.*

class ReadSingleDiaryActivity : BaseActivity(), ReadSingleDiaryContract.View {
    lateinit var presenter: ReadSingleDiaryContract.Presenter

    private var currentDiary: Diary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_single_diary)
        initPresenter()
    }

    override fun initPresenter() {
        presenter = ReadSingleDiaryPresenter(this, DiaryRepository(this), intent.getStringExtra(KEY_DATE)).apply {
            getDiary().observe(this@ReadSingleDiaryActivity, Observer {
                currentDiary = it
                toolbarWrapper.tvTitle.text = it?.date
                tvContents.text = it?.getDecryptContents()
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
            currentDiary?.let { d ->
                DiaryDialog.newInstance(d.uid, d.contents, d.feel, d.date).apply {
                    setDiaryDialogListener(object : DiaryDialog.OnDiaryDialogListener {
                        override fun onWriteClick() {
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
            currentDiary?.let { d ->
                AlertDialog.Builder(this@ReadSingleDiaryActivity).setMessage(R.string.msg_delete_diary)
                    .setPositiveButton(R.string.common_yes) { dialog, _ ->
                        presenter.deleteDiary(d)
                        dialog.dismiss()
                        finish()
                    }.setNegativeButton(R.string.common_no) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        }
    }

    companion object {
        const val KEY_DATE = "date"
    }
}
