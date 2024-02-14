Apackage com.sphirye.shared.exception.http

enum class CustomHttpStatus(val value: Int) {

    CREDENTIALS_EXPIRED(499);

    fun value(): Int {
        return this.value
    }

}