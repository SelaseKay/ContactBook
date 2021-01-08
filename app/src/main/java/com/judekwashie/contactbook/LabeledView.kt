package com.judekwashie.contactbook

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import kotlin.properties.Delegates

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

    var hasExtraViewsAttached by Delegates.notNull<Boolean>()

    init {
        orientation = VERTICAL

        inflate(context, R.layout.labeled_view, this)

        labelTextView = findViewById(R.id.label)
        descriptionTextView = findViewById(R.id.description)
        phoneNumbersSpinner = findViewById(R.id.phone_numbers)

        descriptionTextView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty() ){
                    visibility = View.VISIBLE
                }
                else if(s.toString().isEmpty()){
                    visibility = View.GONE
                }
            }

        })

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