package com.osp.library

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import com.example.osp.R
import com.example.osp.databinding.LayoutFilterCalenderBinding
import com.google.android.material.radiobutton.MaterialRadioButton
import com.osp.library.utils.CalenderChangeListener
import com.osp.library.utils.Selector.Companion.colorStateList
import com.osp.library.utils.DefaultSelectedOption
import com.osp.library.utils.FilterCalender
import com.osp.library.utils.Selector.Companion.selectorDrawable

class FilterCalenderView : ConstraintLayout {
    private var binding: LayoutFilterCalenderBinding
    private lateinit var calendar: FilterCalender
    private lateinit var fromDatePickerDialog: DatePickerDialog
    private lateinit var toDatePickerDialog: DatePickerDialog
    private var radioGroupCheckedId: Int = 0
    private var calenderChangeListener: CalenderChangeListener? = null
    private var _minDate: Long = 0
    private var _maxDate: Long = 0
    private var _themeId: Int = R.style.CustomDatePickerDialog

    private var _arrowBackgroundColor: ColorStateList? = null
    var arrowBackgroundColor: ColorStateList?
        get() = _arrowBackgroundColor
        set(value) {
            _arrowBackgroundColor = value
            setArrowBackgroundColor()
        }

    private var _arrowIconColor: Int = 0
    var arrowIconColor: Int
        get() = _arrowIconColor
        set(value) {
            _arrowIconColor = value
            setArrowIconColor()
        }


    private var _displayTextColor = 0
    var displayTextColor: Int
        get() = _displayTextColor
        set(value) {
            _displayTextColor = value
            setDisplayTextColor()
        }

    private var _optionSelectedBackgroundColor = 0
    var optionSelectedBackgroundColor: Int
        get() = _optionSelectedBackgroundColor
        set(value) {
            _optionSelectedBackgroundColor = value
            setOptionBackground()
        }

    private var _optionUnselectedBackgroundColor = 0
    var optionUnselectedBackgroundColor: Int
        get() = _optionUnselectedBackgroundColor
        set(value) {
            _optionUnselectedBackgroundColor = value
            setOptionBackground()
        }

    private var _optionSelectedStrokeColor = 0
    var optionSelectedStrokeColor: Int
        get() = _optionSelectedStrokeColor
        set(value) {
            _optionSelectedStrokeColor = value
            setOptionBackground()
        }

    private var _optionUnselectedStrokeColor = 0
    var optionUnselectedStrokeColor: Int
        get() = _optionUnselectedStrokeColor
        set(value) {
            _optionUnselectedStrokeColor = value
            setOptionBackground()
        }

    private var _optionSelectedTextColor = 0
    var optionSelectedTextColor: Int
        get() = _optionSelectedTextColor
        set(value) {
            _optionSelectedTextColor = value
            setOptionTextColor()
        }

    private var _optionUnselectedTextColor = 0
    var optionUnselectedTextColor: Int
        get() = _optionUnselectedTextColor
        set(value) {
            _optionUnselectedTextColor = value
            setOptionTextColor()
        }

    private var _defaultSelectedOption = DefaultSelectedOption.None
    var defaultSelectedOption: DefaultSelectedOption
        get() = _defaultSelectedOption
        set(value) {
            _defaultSelectedOption = value
            setDefaultSelectedOption()
        }


    private var _optionTextSize = 20f
    var optionTextSize: Float
        get() = _optionTextSize
        set(value) {
            _optionTextSize = value
            setOptionTextSize()
        }

    private var _displayTextSize = 15f
    var displayTextSize: Float
        get() = _displayTextSize
        set(value) {
            _displayTextSize = value
            setDisplayTextSize()
        }

    constructor(context: Context) : super(context) {
        binding = LayoutFilterCalenderBinding.inflate(LayoutInflater.from(context), this, true)
        getDisplayTextFromRadio()
        onArrowClick()
        onClick()
    }


    internal constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        binding = LayoutFilterCalenderBinding.inflate(LayoutInflater.from(context), this, true)
        getDisplayTextFromRadio()
        getStuffFromXml(context, attrs)
        onArrowClick()
        onClick()
    }


    private fun getStuffFromXml(context: Context, attrs: AttributeSet?) {
        val data = context.obtainStyledAttributes(attrs, R.styleable.FilterCalenderView)

        _arrowBackgroundColor =
            data.getColorStateList(R.styleable.FilterCalenderView_arrowBackgroundColor)
                ?: ContextCompat.getColorStateList(context, R.color.light_blue_900)
        _arrowIconColor = data.getColor(
            R.styleable.FilterCalenderView_arrowIconColor,
            ContextCompat.getColor(context, R.color.white)
        )
        _displayTextColor = data.getColor(
            R.styleable.FilterCalenderView_displayTextColor,
            ContextCompat.getColor(context, R.color.black)
        )

        _optionSelectedBackgroundColor = data.getColor(
            R.styleable.FilterCalenderView_optionSelectedBackgroundColor,
            ContextCompat.getColor(context, R.color.light_blue_900)
        )
        _optionUnselectedBackgroundColor = data.getColor(
            R.styleable.FilterCalenderView_optionUnselectedBackgroundColor,
            ContextCompat.getColor(context, R.color.light_blue_A400)
        )
        _optionSelectedStrokeColor = data.getColor(
            R.styleable.FilterCalenderView_optionSelectedStrokeColor,
            ContextCompat.getColor(context, R.color.light_blue_900)
        )
        _optionUnselectedStrokeColor = data.getColor(
            R.styleable.FilterCalenderView_optionUnselectedStrokeColor,
            ContextCompat.getColor(context, R.color.light_blue_900)
        )
        _optionSelectedTextColor = data.getColor(
            R.styleable.FilterCalenderView_optionSelectedTextColor,
            ContextCompat.getColor(context, R.color.white)
        )
        _optionUnselectedTextColor = data.getColor(
            R.styleable.FilterCalenderView_optionUnselectedTextColor,
            ContextCompat.getColor(context, R.color.black)
        )
        _defaultSelectedOption =
            when (data.getInt(R.styleable.FilterCalenderView_defaultSelectedOption, 0)) {
                1 -> DefaultSelectedOption.All
                2 -> DefaultSelectedOption.Daily
                3 -> DefaultSelectedOption.Weekly
                4 -> DefaultSelectedOption.Monthly
                5 -> DefaultSelectedOption.Yearly
                else -> {
                    DefaultSelectedOption.None
                }
            }
        _optionTextSize = data.getDimensionPixelSize(R.styleable.FilterCalenderView_optionTextSize, 35).toFloat()
        _displayTextSize = data.getDimensionPixelSize(R.styleable.FilterCalenderView_displayTextSize, 30).toFloat()
        data.recycle()
        setDataFromXml()
    }

    private fun setDataFromXml() {
        binding.apply {
            setDefaultSelectedOption()
            setArrowBackgroundColor()
            setArrowIconColor()
            setDisplayTextColor()
            setOptionTextColor()
            setOptionBackground()
            setDisplayTextSize()
            setOptionTextSize()
        }
    }

    private fun setDatePicker() {
        val fromCalender = FilterCalender()
        val toCalender = FilterCalender()
        setDisplayText(fromCalender.getDate(30), toCalender.getDate())
        fromDatePickerDialog = fromCalender.customDatePickerDialog(context, _themeId) {
            setDisplayText(it, null)
        }
        toDatePickerDialog = toCalender.customDatePickerDialog(context, _themeId) {
            setDisplayText(null, it)
        }

        if (_minDate != 0L) {
            fromDatePickerDialog.datePicker.minDate = _minDate
            toDatePickerDialog.datePicker.minDate = _minDate
        }

        if (_maxDate != 0L) {
            fromDatePickerDialog.datePicker.maxDate = _maxDate
            toDatePickerDialog.datePicker.maxDate = _maxDate
        }
    }

    private fun setArrowBackgroundColor() {
        binding.apply {
            leftArrow.backgroundTintList = _arrowBackgroundColor
            rightArrow.backgroundTintList = _arrowBackgroundColor
        }
    }

    private fun setArrowIconColor() {
        binding.apply {
            leftArrow.setColorFilter(_arrowIconColor)
            rightArrow.setColorFilter(_arrowIconColor)
        }
    }

    private fun setDisplayTextColor() {
        binding.apply {
            tvDisplay.setTextColor(_displayTextColor)
            tvTo.setTextColor(_displayTextColor)
        }
    }

    private fun setOptionTextColor() {
        binding.apply {
            radioGroup.children
                .filterIsInstance<MaterialRadioButton>()
                .forEach {
                    it.setTextColor(
                        colorStateList(
                            _optionSelectedTextColor,
                            _optionUnselectedTextColor
                        )
                    )
                }
        }
    }

    private fun setOptionBackground() {
        binding.apply {
            radioGroup.children
                .filterIsInstance<MaterialRadioButton>()
                .forEach {
                    it.setBackgroundDrawable(
                        selectorDrawable(
                            _optionSelectedBackgroundColor,
                            _optionUnselectedBackgroundColor,
                            _optionSelectedStrokeColor,
                            _optionUnselectedStrokeColor
                        )
                    )
                }
        }
    }

    private fun onClick() {
        binding.apply {
            dateFrom.setOnClickListener {
                fromDatePickerDialog.show()
            }
            dateTo.setOnClickListener {
                toDatePickerDialog.show()
            }
        }
    }

    private fun setDefaultSelectedOption() {
        binding.apply {
            when (_defaultSelectedOption) {
                DefaultSelectedOption.All -> all.isChecked = true
                DefaultSelectedOption.Daily -> daily.isChecked = true
                DefaultSelectedOption.Weekly -> weekly.isChecked = true
                DefaultSelectedOption.Monthly -> monthly.isChecked = true
                DefaultSelectedOption.Yearly -> yearly.isChecked = true
                else -> {
                    radioGroup.children
                        .filterIsInstance<MaterialRadioButton>()
                        .forEach { it.isChecked=false }
                }
            }
        }
    }

    private fun setOptionTextSize(){
      binding.apply {
          radioGroup.children
              .filterIsInstance<MaterialRadioButton>()
              .forEach { it.setTextSize(TypedValue.COMPLEX_UNIT_PX, _optionTextSize) }
      }
    }

    private fun setDisplayTextSize(){
        binding.apply {
            tvDisplay.setTextSize(TypedValue.COMPLEX_UNIT_PX, _displayTextSize)
            tvTo.setTextSize(TypedValue.COMPLEX_UNIT_PX, _displayTextSize)
        }
    }

    private fun getDisplayTextFromRadio() {
        binding.apply {
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                radioGroupCheckedId = checkedId
                calendar = FilterCalender()

                constraintAll.isVisible = checkedId == R.id.all
                constraintOther.isVisible = checkedId != R.id.all

                when (checkedId) {
                    R.id.all -> setDatePicker()
                    R.id.daily -> calendar.setDay { text: String, from: String, to: String ->
                        setDisplayText(text, from, to)
                    }

                    R.id.weekly -> calendar.setWeek { text: String, from: String, to: String ->
                        setDisplayText(text, from, to)
                    }

                    R.id.monthly -> calendar.setMonth { text: String, from: String, to: String ->
                        setDisplayText(text, from, to)
                    }

                    R.id.yearly -> calendar.setYear { text: String, from: String, to: String ->
                        setDisplayText(text, from, to)
                    }
                }
            }
        }
    }

    private fun onArrowClick() {
        binding.apply {
            leftArrow.setOnClickListener {
                getDisplayTextFromLeftArrow()
                tvDisplay.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_left))
            }
            rightArrow.setOnClickListener {
                getDisplayTextFromRightArrow()
                tvDisplay.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_right))
            }
        }
    }

    private fun getDisplayTextFromLeftArrow() {
        binding.apply {
            when (radioGroupCheckedId) {
                R.id.daily -> calendar.setDay(FilterCalender.INCREMENT) { text: String, from: String, to: String ->
                    setDisplayText(text, from, to)
                }

                R.id.weekly -> calendar.setWeek(FilterCalender.INCREMENT) { text: String, from: String, to: String ->
                    setDisplayText(text, from, to)
                }

                R.id.monthly -> calendar.setMonth(FilterCalender.INCREMENT) { text: String, from: String, to: String ->
                    setDisplayText(text, from, to)
                }

                R.id.yearly -> calendar.setYear(FilterCalender.INCREMENT) { text: String, from: String, to: String ->
                    setDisplayText(text, from, to)
                }
            }
        }
    }

    private fun getDisplayTextFromRightArrow() {
        binding.apply {
            when (radioGroupCheckedId) {
                R.id.daily -> calendar.setDay(FilterCalender.DECREMENT) { text: String, from: String, to: String ->
                    setDisplayText(text, from, to)
                }

                R.id.weekly -> calendar.setWeek(FilterCalender.DECREMENT) { text: String, from: String, to: String ->
                    setDisplayText(text, from, to)
                }

                R.id.monthly -> calendar.setMonth(FilterCalender.DECREMENT) { text: String, from: String, to: String ->
                    setDisplayText(text, from, to)
                }

                R.id.yearly -> calendar.setYear(FilterCalender.DECREMENT) { text: String, from: String, to: String ->
                    setDisplayText(text, from, to)
                }
            }
        }
    }

    private fun setDisplayText(text: String, from: String, to: String) {
        binding.apply {
            tvDisplay.text = text
            calenderChangeListener?.onDateChange(from, to)
        }
    }

    private fun setDisplayText(from: String?, to: String?) {
        binding.apply {
            from?.let { dateFrom.text = it }
            to?.let { dateTo.text = it }
            calenderChangeListener?.onDateChange(dateFrom.text.toString(), dateTo.text.toString())
        }
    }


    fun setCalendarTheme(@StyleRes themeResId: Int) {
        _themeId = themeResId
    }

    fun setMinDate(timeInMillis: Long) {
        _minDate = timeInMillis
    }

    fun setMaxDate(timeInMillis: Long) {
        _maxDate = timeInMillis
    }

    fun setOnCalenderChangeListener(listener: (dateFrom: String, dateTo: String) -> Unit) {
        calenderChangeListener = object : CalenderChangeListener {
            override fun onDateChange(from: String, to: String) {
                listener(from, to)
            }
        }
    }
}