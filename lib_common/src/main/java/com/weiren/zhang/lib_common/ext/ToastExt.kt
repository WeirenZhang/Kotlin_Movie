package com.weiren.zhang.lib_common.ext

import com.weiren.zhang.lib_common.util.AppGlobal
import com.sdsmdg.tastytoast.TastyToast

fun infoToast(message: String) {
    AppGlobal.get()
        ?.let { TastyToast.makeText(it, message, TastyToast.LENGTH_LONG, TastyToast.INFO) }
}

fun errorToast(message: String) {
    AppGlobal.get()
        ?.let { TastyToast.makeText(it, message, TastyToast.LENGTH_LONG, TastyToast.ERROR) }
}