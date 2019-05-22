package com.mdlicht.zb.colordiarymvp.activity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.widget.Toolbar
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.common.showToast
import com.mdlicht.zb.colordiarymvp.constract.MainConstract
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository
import com.mdlicht.zb.colordiarymvp.dialog.DiaryDialog
import com.mdlicht.zb.colordiarymvp.presenter.MainPresenter
import com.mdlicht.zb.colordiarymvp.util.ColorDiaryUtil
import com.mdlicht.zb.colordiarymvp.util.TimeUtil
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.view.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity(), MainConstract.View {
    lateinit var presenter: MainConstract.Presenter
    private val backPressSubject: BehaviorSubject<Long> = BehaviorSubject.createDefault(0L)
    private var backPressDisposable: Disposable? = null
    private lateinit var diaryLiveData: LiveData<Diary>
    private lateinit var dateLiveData: LiveData<String>
    private var diary: Diary? = null

    private val expandConstraintSet: ConstraintSet by lazy {
        ConstraintSet().apply {
            load(this@MainActivity, R.layout.activity_main_calendar_expand)
        }
    }
    private val collapseConstraintSet: ConstraintSet by lazy {
        ConstraintSet().apply {
            load(this@MainActivity, R.layout.activity_main)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPresenter()

        backPressDisposable = backPressSubject.buffer(2, 1)
            .map {
                Pair<Long, Long>(it[0], it[1])
            }
            .map {
                it.second - it.first < TimeUnit.SECONDS.toMillis(2)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it) {
                    finish()
                } else {
                    showToast(getString(R.string.msg_finish_app_guide))
                }
            }
    }

    override fun initView() {
        setSupportActionBar(toolbarWrapper as Toolbar)

        mcvCalendar.apply {
            topbarVisible = false
            setWeekDayLabels(R.array.weekday_labels)
            selectedDate = CalendarDay.today()
            state().edit().setMinimumDate(
                LocalDate.parse(
                    getString(R.string.min_date),
                    DateTimeFormatter.ofPattern(TimeUtil.YYYY_MM_DD)
                )
            )
                .setMaximumDate(
                    LocalDate.parse(
                        getString(R.string.max_date),
                        DateTimeFormatter.ofPattern(TimeUtil.YYYY_MM_DD)
                    )
                ).commit()

            setOnDateChangedListener { widget, date, selected ->
                widget.selectedDate = CalendarDay.from(
                    date.year,
                    date.month,
                    if (widget.selectedDate!!.day <= date.date.dayOfMonth) widget.selectedDate!!.day else date.date.dayOfMonth
                )
                presenter.dateChanged(
                    TimeUtil.convertDateFormat(
                        widget.selectedDate!!.year,
                        widget.selectedDate!!.month - 1,
                        widget.selectedDate!!.day,
                        TimeUtil.YYYY_MM_DD
                    )
                )
            }
        }

        fabWrite.setOnClickListener {
            val dialog: DiaryDialog = if (diary == null)
                DiaryDialog.newInstance(date = dateLiveData.value)
            else
                DiaryDialog.newInstance(diary!!.uid, diary!!.contents, diary!!.feel, diary!!.date)
            dialog.apply {
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

        navView.setNavigationItemSelectedListener {
            dlConrainter.closeDrawers()
            when (it.itemId) {
//                R.id.nav_colorHistory -> {
//                    startActivity(Intent(this@MainActivity, ColorHistoryActivity::class.java))
//                }
//                R.id.nav_timeline -> {
//                    startActivity(Intent(this@MainActivity, TimelineActivity::class.java))
//                }
//                R.id.nav_settings -> {
//                    startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
//                }
//                else -> throw Exception()
            }
            true
        }

        (toolbarWrapper as Toolbar).apply {
            tvTitle.setOnClickListener {
                if (mcvCalendar.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(clRoot)
                    collapseConstraintSet.applyTo(clRoot)
                } else {
                    TransitionManager.beginDelayedTransition(clRoot)
                    expandConstraintSet.applyTo(clRoot)
                }
            }

            ivColorHistory.setOnClickListener {
                startActivity(Intent(this@MainActivity, ColorHistoryActivity::class.java))
            }

            ivDrawer.setOnClickListener {
                dlConrainter.openDrawer(Gravity.START)
            }
        }
    }

    override fun initPresenter() {
        presenter = MainPresenter(this, DiaryRepository(this)).apply {
            dateLiveData = getDate()
            dateLiveData.observe(this@MainActivity, Observer {
                updateDate(it)
            })
            diaryLiveData = getDiary()
            diaryLiveData.observe(this@MainActivity, Observer {
                updateDiary(it)
            })
        }
    }

    override fun updateDiary(diary: Diary?) {
        this.diary = diary
        if (diary == null) {
            tvTodayGuide.text = ColorDiaryUtil.getTodayGuilde()
        } else {
            tvTodayGuide.text = diary.getDecryptContents()
        }
    }

    override fun onBackPressed() {
        backPressSubject.onNext(System.currentTimeMillis())
    }

    override fun updateDate(date: String?) {
        toolbarWrapper.tvTitle.text = date
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressDisposable?.dispose()
    }
}
