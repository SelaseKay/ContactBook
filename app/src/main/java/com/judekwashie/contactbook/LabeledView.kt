package com.judekwashie.contactbook

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView

class LabeledView @JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val labelTextView: TextView
    private val descriptionTextView: TextView
    private val phoneNumbersSpinner: Spinner

    init {
        orientation = VERTICAL

        inflate(context, R.layout.labeled_view, this)

        labelTextView = findViewById(R.id.label)
        descriptionTextView = findViewById(R.id.description)
        phoneNumbersSpinner = findViewById(R.id.phone_numbers)

        context.theme.obtainStyledAttributes(attrs, R.styleable.LabeledView, 0, 0).apply {
            try {
                setLabel(this)
                setDescription(this)
                setDescriptionVisibility(this)
            } finally {

                recycle()
            }
        }

    }

    private fun setLabel(typedArray: TypedArray) {
        labelTextView.text = typedArray.getString(R.styleable.LabeledView_label)
    }

    private fun setDescription(typedArray: TypedArray) {
        descriptionTextView.text = typedArray.getString(R.styleable.LabeledView_description)
    }

    private fun setDescriptionVisibility(typedArray: TypedArray){
        if (!typedArray.getBoolean(R.styleable.LabeledView_description_visibility, true)){
            descriptionTextView.visibility = GONE
            return
        }
        descriptionTextView.visibility = VISIBLE
        phoneNumbersSpinner.visibility = GONE
    }
}