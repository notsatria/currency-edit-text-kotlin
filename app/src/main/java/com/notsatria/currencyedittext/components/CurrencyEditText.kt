package com.notsatria.currencyedittext.components

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class CurrencyEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : TextInputEditText(context, attrs) {
    private var isFormatting = false

    init {
        inputType = InputType.TYPE_CLASS_NUMBER

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No-op
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No-op
            }

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return

                isFormatting = true
                val originalString = s.toString()

                if (originalString.isEmpty()) {
                    isFormatting = false
                    return
                }

                val cleanString = originalString.replace("[,.]".toRegex(), "")
                val parsed = cleanString.toDoubleOrNull() ?: 0.0

                val formattedString = getFormattedNumber(parsed)

                setText(formattedString)
                setSelection(formattedString.length)

                isFormatting = false
            }
        })
    }

    private fun getFormattedNumber(number: Double): String {
        val symbols = DecimalFormatSymbols(Locale.GERMANY).apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }
        val decimalFormat = DecimalFormat("#,###", symbols)
        return decimalFormat.format(number)
    }
}