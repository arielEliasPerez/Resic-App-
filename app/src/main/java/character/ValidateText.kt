package character

import repositories.UserRepository

object ValidateText {
    private val regexPassword = "^[a-zA-Z0-9@_]*$".toRegex()
    private val regexNick = "^[a-zA-Z0-9_]*$".toRegex()
    const val MIN_LENGTH = 4
    const val MAX_LENGTH = 11

    fun minLength(text: String): Boolean {
        return text.length < MIN_LENGTH
    }

    fun maxLength(text: String): Boolean {
        return text.length > MAX_LENGTH
    }

    fun validCharactersPassword(text : String): Boolean {
        return regexPassword.matches(text)
    }

    fun validCharactersNick(text : String): Boolean {
        return regexNick.matches(text)
    }

    fun nickNameExists(nickName : String) = UserRepository.userNameExists(nickName = nickName )

}