package com.mdlicht.zb.colordiarymvp.dialog


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton

import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.common.AESHelper
import com.mdlicht.zb.colordiarymvp.common.App
import com.mdlicht.zb.colordiarymvp.common.showToast
import com.mdlicht.zb.colordiarymvp.constract.DiaryDialogConstract
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository
import com.mdlicht.zb.colordiarymvp.presenter.DiaryDialogPresenter
import kotlinx.android.synthetic.main.fragment_diary_dialog.*

class DiaryDialog : FullScreenDialog(), DiaryDialogConstract.View {
    private var id: Int? = -1
    private var contents: String? = null
    private var feel: String? = null
    private var date: String? = null

    private var listener: OnDiaryDialogListener? = null

    private lateinit var presenter: DiaryDialogConstract.Presenter

    fun setDiaryDialogListener(listener: OnDiaryDialogListener?) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(KEY_ID, -1)
            contents = it.getString(KEY_CONTENTS, "")
            feel = it.getString(KEY_FEEL, "")
            date = it.getString(KEY_DATE, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
    }

    override fun initPresenter() {
        presenter = DiaryDialogPresenter(this, DiaryRepository(context!!))
    }

    override fun initView() {
        tvTitle.text = date
        etContents.setText(contents?.let { AESHelper.decrypt(it, App.getInstance().getNativeKey2()) })

        etContents.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                contents = p0?.toString() ?: ""
            }
        })

        ivConfirm.setOnClickListener {
            presenter.saveDiary(id, contents, feel, date)
        }

        ivClose.setOnClickListener {
            listener?.onCloseClick()
        }

        rgFeel.setOnCheckedChangeListener { radioGroup, i ->
            radioGroup.findViewById<RadioButton>(i)?.let {
                feel = it.tag as String
            }
        }

        addRadioButtons()
    }

    override fun showMsg(msg: String) {
        context?.showToast(msg)
    }

    override fun diaryUpdated() {
        listener?.onWriteClick()
    }

    private fun addRadioButtons() {
        val shapeList = arrayOf(R.drawable.shape_radio_happy, R.drawable.shape_radio_soso, R.drawable.shape_radio_sad, R.drawable.shape_radio_angry, R.drawable.shape_radio_worst)
        val feelList = resources.getStringArray(R.array.feelings)
        for((index, feel)in feelList.withIndex()) {
            val radio = LayoutInflater.from(context).inflate(R.layout.item_radio, rgFeel, false).apply {
                setBackgroundResource(shapeList[index])
                tag = feel
            }
            rgFeel.addView(radio)
        }
        arguments?.getString(KEY_FEEL)?.let {
            rgFeel.findViewWithTag<RadioButton>(it)?.let{ rb ->
                rgFeel.check(rb.id)
            }
        }
    }

    interface OnDiaryDialogListener {
        fun onWriteClick()
        fun onCloseClick()
    }

    companion object {
        private const val KEY_ID = "id"
        private const val KEY_CONTENTS = "contents"
        private const val KEY_FEEL = "feel"
        private const val KEY_DATE = "date"

        @JvmStatic
        fun newInstance(id: Int = -1, contents: String? = null, feel: String? = null, date: String? = null) =
            DiaryDialog().apply {
                arguments = Bundle().apply {
                    putInt(KEY_ID, id)
                    putString(KEY_CONTENTS, contents)
                    putString(KEY_FEEL, feel)
                    putString(KEY_DATE, date)
                }
            }
    }
}