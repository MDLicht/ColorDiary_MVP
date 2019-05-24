package com.mdlicht.zb.colordiarymvp.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.adapter.SettingsRvAdapter
import com.mdlicht.zb.colordiarymvp.common.App
import com.mdlicht.zb.colordiarymvp.common.showToast
import com.mdlicht.zb.colordiarymvp.constract.SettingsContract
import com.mdlicht.zb.colordiarymvp.database.DB
import com.mdlicht.zb.colordiarymvp.presenter.SettingsPresenter
import com.mdlicht.zb.colordiarymvp.util.FileUtil
import com.tedpark.tedpermission.rx2.TedRx2Permission
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar_simple.view.*

class SettingsActivity : BaseActivity(), SettingsContract.View, SettingsRvAdapter.OnSettingsItemClickListener {
    lateinit var presenter: SettingsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initPresenter()
    }

    override fun initPresenter() {
        presenter = SettingsPresenter(this)
    }

    override fun initView() {
        setSupportActionBar(toolbarWrapper as Toolbar)

        (toolbarWrapper as Toolbar).apply {
            tvTitle.text = getString(R.string.drawer_menu_settings)

            ivBack.setOnClickListener {
                finish()
            }
        }

        rvSettings.apply {
            layoutManager = LinearLayoutManager(this@SettingsActivity)
            adapter = SettingsRvAdapter(this@SettingsActivity, this@SettingsActivity)
            addItemDecoration(DividerItemDecoration(this@SettingsActivity, DividerItemDecoration.VERTICAL))
        }
    }

    @SuppressLint("CheckResult")
    override fun onBackUpClick() {
        TedRx2Permission.with(this)
            .setRationaleTitle(R.string.permission_msg_title)
            .setRationaleMessage(R.string.permission_msg_contents) // "we need permission for read contact and find your location"
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request()
            .subscribe({ tedPermissionResult ->
                if (tedPermissionResult.isGranted) {
                    presenter.export()
                    showToast(getString(R.string.msg_export_complete), Toast.LENGTH_LONG)
                    App.getInstance().restartApp(1000)
                } else {
                    showToast(
                        getString(R.string.permission_msg_denied)
                    )
                }
            }, { throwable ->
                throwable.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    override fun onImportClick() {
        TedRx2Permission.with(this)
            .setRationaleTitle(R.string.permission_msg_title)
            .setRationaleMessage(R.string.permission_msg_contents) // "we need permission for read contact and find your location"
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request()
            .subscribe({ tedPermissionResult ->
                if (tedPermissionResult.isGranted) {
                    AlertDialog.Builder(this@SettingsActivity)
                        .setTitle(R.string.common_notice)
                        .setMessage(R.string.msg_import_alert)
                        .setPositiveButton(R.string.common_yes) { dialog, which ->
                            dialog.dismiss()
                            startFileChooserActivity()
                        }
                        .setNegativeButton(R.string.common_no) { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                } else {
                    showToast(
                        getString(R.string.permission_msg_denied)
                    )
                }
            }, { throwable ->
                throwable.printStackTrace()
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_FILE_SELECT -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data?.data
                    // Get the path
                    val path = FileUtil.getPath(this, uri!!)
                    path?.let {
                        val result = presenter.import(it)
                        if (result) {
                            // success
                            showToast(getString(R.string.msg_import_success))
                            App.getInstance().restartApp(1000)
                        } else {
                            showToast(getString(R.string.msg_import_failed))
                        }
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun startFileChooserActivity() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
        }
        try {
            startActivityForResult(
                Intent.createChooser(intent, getString(R.string.msg_select_import_db_file)),
                REQ_CODE_FILE_SELECT
            )
        } catch (ex: android.content.ActivityNotFoundException) {
            // Potentially direct the user to the Market with a Dialog
            showToast(
                "Please install a File Manager."
            )
        }
    }

    companion object {
        const val REQ_CODE_FILE_SELECT = 100
    }
}
