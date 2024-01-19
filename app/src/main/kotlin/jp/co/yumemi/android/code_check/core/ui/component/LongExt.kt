package jp.co.yumemi.android.code_check.core.ui.component

/**
 * 999以下 -> そのまま
 * 1000 -> 1.0k
 * 1900 -> 1.9k
 * 10000 -> 10k
 * 10900 -> 10.9k
 * 100000 -> 100k
 * のように文字列を変換する
 *
 * 現状、他の単位は考慮していない。
 */
fun Long.toKString(): String {
    // 999以下の場合は数値をそのまま文字列として返す
    if (this <= 999) return this.toString()

    // 1000以上の場合、単位kを使用する
    val valueInK = this / 1000.0
    return when {
        this % 1000 == 0L -> "${valueInK.toInt()}k" // 1000の倍数の場合
        else -> String.format("%.1fk", valueInK)   // それ以外の場合、小数点以下1桁まで表示
    }
}