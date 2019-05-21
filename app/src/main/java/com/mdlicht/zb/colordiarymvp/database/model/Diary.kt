package com.mdlicht.zb.colordiarymvp.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.mdlicht.zb.colordiarymvp.common.AESHelper
import com.mdlicht.zb.colordiarymvp.common.App

@Entity
data class Diary(@PrimaryKey(autoGenerate = true)var uid: Int, var contents: String, var feel: String, var date: String) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    fun getDecryptContents(): String {
        return AESHelper.decrypt(contents, App.getInstance().getNativeKey2())
    }

    fun getEncryptContents(): String {
        return contents
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uid)
        parcel.writeString(contents)
        parcel.writeString(feel)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Diary> {
        override fun createFromParcel(parcel: Parcel): Diary {
            return Diary(parcel)
        }

        override fun newArray(size: Int): Array<Diary?> {
            return arrayOfNulls(size)
        }
    }
}