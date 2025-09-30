import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }

        val masked = buildString {
            for (i in digits.indices) {
                when (i) {
                    0 -> append('(').append(digits[i])
                    1 -> append(digits[i]).append(") ")
                    6 -> append(digits[i]).append('-')
                    else -> append(digits[i])
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var extra = 0
                if (offset > 0) extra++   // '('
                if (offset > 1) extra += 2 // ') '
                if (offset > 6) extra++  // '-'
                return offset + extra
            }

            override fun transformedToOriginal(offset: Int): Int {
                var original = offset
                if (offset > 0) original--
                if (offset > 3) original -= 2
                if (offset > 10) original--
                if (original < 0) original = 0
                return original
            }
        }

        return TransformedText(AnnotatedString(masked), offsetMapping)
    }
}
