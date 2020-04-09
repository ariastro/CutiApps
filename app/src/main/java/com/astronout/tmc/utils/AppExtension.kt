package com.astronout.tmc.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.astronout.tmc.BuildConfig
import com.astronout.tmc.R
import com.astronout.tmc.utils.glide.GlideApp
import com.bumptech.glide.GenericTransitionOptions
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import de.hdodenhof.circleimageview.CircleImageView
import org.joda.time.DateTime
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

fun logDebug(message: String) {
    if (BuildConfig.DEBUG)
        Log.d(Constants.TAG_DEBUG, message)
}

/**
 * Log Error Message Locally and Send to Error Reporting Server
 */
fun logError( message: String, throwable : Throwable? = null) {
    if (BuildConfig.DEBUG) {
//        DEBUG VERSION
//        LOG LOCALLY
        Log.e(Constants.TAG_ERROR, message)
    }
}

val dateTimeFormat = "dd MMMM, yyyy"
val defaultDateFormat = "yyyy-MM-dd"
val dateFormat = "dd MMMM yyyy"
val defaultDateTimeFormat = "yyyy-MM-dd HH:mm:ss"
val jodaTimeDefaultFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

fun dialogDate(context: Context, dateResult: (dateResult: String) -> Unit){
    val dateNow = DateTime.now()
    MaterialDialog(context).show {
        datePicker(maxDate = dateNow.toCalendar(Locale.getDefault())) { dialog, date ->
            val dateTime = DateTime(date)
            dateResult(dateTime.toString(dateTimeFormat))
        }
    }
}

fun dialogChooseLeaveDate(context: Context, dateResult: (dateResult: String) -> Unit){
    val dateNow = DateTime.now()
    MaterialDialog(context).show {
        datePicker(minDate = dateNow.toCalendar(Locale.getDefault())) { dialog, date ->
            val dateTime = DateTime(date)
            dateResult(dateTime.toString(dateTimeFormat))
        }
    }
}

fun dialogChooseLeaveUntil(startDate: Calendar, context: Context, dateResult: (dateResult: String) -> Unit){
//    val dateNow = DateTime.now()
    MaterialDialog(context).show {
        datePicker(minDate = startDate) { dialog, date ->
            val dateTime = DateTime(date)
            dateResult(dateTime.toString(dateTimeFormat))
        }
    }
}

fun dialogChoosefixedMinMaxDate(startDate: Calendar, maxDate: Calendar, context: Context, dateResult: (dateResult: String) -> Unit){
    MaterialDialog(context).show {
        datePicker(minDate = startDate, maxDate = maxDate) { dialog, date ->
            val dateTime = DateTime(date)
            dateResult(dateTime.toString(dateTimeFormat))
        }
    }
}

private val VALID_EMAIL_ADDRESS_REGEX: Pattern =
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

fun isValidEmail(emailStr: String?): Boolean {
    val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
    return matcher.find().not()
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.checkPermissionForLocation(context: Context, REQUEST_PERMISSION_LOCATION : Int): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            // Show the permission request
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSION_LOCATION)
            false
        }
    } else {
        true
    }
}

fun Activity.imagePicker(){
    ImagePicker.create(this)
        .folderMode(true)
        .toolbarFolderTitle("Pilih Gambar")
        .toolbarImageTitle("Tap untuk memilih")
        .toolbarArrowColor(Color.WHITE)
        .single()
        .returnMode(ReturnMode.ALL)
        .limit(1)
        .showCamera(true)
        .start()
}

fun Fragment.imagePicker(){
    ImagePicker.create(this)
        .folderMode(true)
        .toolbarFolderTitle("Pilih Gambar")
        .toolbarImageTitle("Tap untuk memilih")
        .toolbarArrowColor(Color.WHITE)
        .single()
        .returnMode(ReturnMode.ALL)
        .limit(1)
        .showCamera(true)
        .start()
}

fun visible() = View.VISIBLE
fun invisible() = View.INVISIBLE
fun gone() = View.GONE

fun autoNextEditText(editText: AppCompatEditText, editText2: AppCompatEditText){
    editText.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (editText.length() == 1)
            {
                editText2.requestFocus()
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
        }

    })
}

@BindingAdapter("setImageUrl")
fun AppCompatImageView.setImageUrl(imageUrl: String?) {
    GlideApp.with(context)
        .load(imageUrl)
        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
        .into(this)
}

@BindingAdapter("setCircleImageUrl")
fun CircleImageView.setCircleImageUrl(imageUrl: String?) {
    GlideApp.with(context)
        .load(imageUrl)
        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
        .into(this)
}

@BindingAdapter("loadImage")
fun AppCompatImageView.loadImage(imageDrawable: Int) {
    GlideApp.with(context)
        .load(imageDrawable)
        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
        .into(this)
}

@BindingAdapter("binding")
fun bindAppCompatEditText(appCompatEditText: AppCompatEditText, string: ObservableField<String>){
    val pair: Pair<ObservableField<String>, TextWatcherAdapter>? = appCompatEditText.getTag(R.id.bound_observable) as Pair<ObservableField<String>, TextWatcherAdapter>?
    if (pair==null || pair.first != string) {
        if (pair != null) {
            appCompatEditText.removeTextChangedListener(pair.second)
        }
        val watcher = object : TextWatcherAdapter(){
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                super.onTextChanged(p0, p1, p2, p3)
                string.set(p0?.toString())
            }
        }
        appCompatEditText.setTag(R.id.bound_observable, Pair(string, watcher))
        appCompatEditText.addTextChangedListener(watcher)
    }
    val newValue = string.get()
    if (appCompatEditText.text.toString() != newValue){
        appCompatEditText.setText(newValue)
    }
}

@BindingAdapter("bindingTextInputEditText")
fun bindAppCompatEditText(appCompatEditText: AppCompatEditText, string: MutableLiveData<String>){
    val pair: Pair<MutableLiveData<String>, TextWatcherAdapter>? = appCompatEditText.getTag(R.id.bound_observable) as Pair<MutableLiveData<String>, TextWatcherAdapter>?
    if (pair==null || pair.first != string) {
        if (pair != null) {
            appCompatEditText.removeTextChangedListener(pair.second)
        }
        val watcher = object : TextWatcherAdapter(){
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                super.onTextChanged(p0, p1, p2, p3)
                string.value = p0?.toString()
            }
        }
        appCompatEditText.setTag(R.id.bound_observable, Pair(string, watcher))
        appCompatEditText.addTextChangedListener(watcher)
    }
    val newValue = string.value
    if (appCompatEditText.text.toString() != newValue){
        appCompatEditText.setText(newValue)
    }
}

@BindingAdapter("bindingTextView")
fun bindAppCompatTextView(appCompatTextView: AppCompatTextView, string: ObservableField<String>){
    val pair: Pair<ObservableField<String>, TextWatcherAdapter>? = appCompatTextView.getTag(R.id.bound_observable) as Pair<ObservableField<String>, TextWatcherAdapter>?
    if (pair==null || pair.first != string) {
        if (pair != null) {
            appCompatTextView.removeTextChangedListener(pair.second)
        }
        val watcher = object : TextWatcherAdapter(){
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                super.onTextChanged(p0, p1, p2, p3)
                string.set(p0?.toString())
            }
        }
        appCompatTextView.setTag(R.id.bound_observable, Pair(string, watcher))
        appCompatTextView.addTextChangedListener(watcher)
    }
    val newValue = string.get()
    if (appCompatTextView.text.toString() != newValue){
        appCompatTextView.setText(newValue)
    }
}