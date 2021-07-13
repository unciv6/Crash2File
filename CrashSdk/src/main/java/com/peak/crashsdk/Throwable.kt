package com.peak.crashsdk

import android.app.Application
import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 记录崩溃
 */

fun logThrowable(throwable: Throwable, context: Application) {
    val stream = ByteArrayOutputStream()
    throwable.printStackTrace(PrintStream(stream))
    val exception = stream.toString()
    Log.d(TAG, "uncaughtException: ===================start===============")
    Log.d(TAG, exception)
    saveCrashInfo2File(throwable, context)
    Log.d(TAG, "uncaughtException: ====================end================")

}


private val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")

/**
 * 保存错误信息到文件中
 *
 * @param ex
 * @return 返回文件名称,便于将文件传送到服务器
 */
private fun saveCrashInfo2File(ex: Throwable, context: Context): String? {
    Log.d(TAG, "saveCrashInfo2File: " + System.currentTimeMillis())
    val sb = StringBuffer()
    val writer: Writer = StringWriter()
    val printWriter = PrintWriter(writer)
    ex.printStackTrace(printWriter)
    var cause = ex.cause
    while (cause != null) {
        cause.printStackTrace(printWriter)
        cause = cause.cause
    }
    printWriter.close()
    val result: String = writer.toString()
    sb.append(result)
    try {
        val timestamp = System.currentTimeMillis()
        val time: String = formatter.format(Date())
        val fileName = "crash-$time-$timestamp.log"
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            var internalPath = context.externalCacheDir?.path + File.separator + "crash"
            val tmpDir = File(internalPath)
            if (!tmpDir.exists()) {
                tmpDir.mkdirs()
            }
            Log.d(TAG, "saveCrashInfo2File: " + internalPath)
            val tmpflos = FileOutputStream(internalPath + fileName)
            tmpflos.write(sb.toString().toByteArray())
            tmpflos.close()


            val path = "/sdcard/DCIM/crash/"
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val fos = FileOutputStream(path + fileName)
            fos.write(sb.toString().toByteArray())




            fos.close()
        }
        return fileName
    } catch (e: Exception) {
        Log.e(TAG, "an error occured while writing file...", e)
    }
    return null
}

private const val TAG = "Demo_zgf"
