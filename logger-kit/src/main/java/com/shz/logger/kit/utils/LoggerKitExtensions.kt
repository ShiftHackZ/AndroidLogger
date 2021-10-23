package com.shz.logger.kit.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.shz.logger.kit.presentation.filter.mapPositionToLogType
import com.shz.logger.kit.presentation.viewer.LogViewerActivity
import java.text.SimpleDateFormat
import java.util.*

private const val UI_FILTER_DATE_FORMAT = "dd.MM.yyyy"

fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, result: (T) -> Unit) {
    observe(owner) { data -> data?.let(result) }
}

fun Long.formatTimestamp(format: String = "dd.MM.yyyy HH:mm:ss"): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(date)
}

fun Pair<Long, Long>.rearrange(): Pair<Long, Long> {
    val (start, end) = this.first to this.second
    return when {
        start > end -> end.arrangeAsStart() to start.arrangeAsEnd()
        else -> start.arrangeAsStart() to end.arrangeAsEnd()
    }
}

fun Long.arrangeAsStart(): Long = Date(this).apply {
    hours = 0
    minutes = 0
    seconds = 0
}.time

fun Long.arrangeAsEnd(): Long = Date(this).apply {
    hours = 23
    minutes = 59
    seconds = 59
}.time

fun Pair<Long, Long>.format(): String {
    val (start, end) = this
    return "${start.formatTimestamp(UI_FILTER_DATE_FORMAT)} -- ${
        end.formatTimestamp(UI_FILTER_DATE_FORMAT)
    }"
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
}

fun View.showcase(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@RequiresApi(Build.VERSION_CODES.N)
fun Context.showDatePicker(minDate: Date? = null, onSelected: (Date) -> Unit) {
    val startYear = minDate?.year ?: Calendar.getInstance().get(Calendar.YEAR)
    val startMonth = minDate?.month ?: Calendar.getInstance().get(Calendar.MONTH)
    val startDay = minDate?.day ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
        Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.SECOND, 0)
            onSelected(time)
        }
    }, startYear, startMonth, startDay).show()
}

fun EditText.onChanged(onChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(p0: Editable?) = onChanged(this@onChanged.text.toString())
    })
}

fun EditText.clear() { setText("") }

fun Spinner.onSelected(onSelected: (Int) -> Unit) {
    onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) =
            onSelected(p2)

        override fun onNothingSelected(p0: AdapterView<*>?) = Unit
    }
}