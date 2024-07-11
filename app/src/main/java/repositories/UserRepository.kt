package repositories

import data.User
import java.time.LocalDate

object UserRepository {

    private val users = mutableListOf<User>()

    init {
        users.add(User(1504L, "bbayarri", "abc123", "Brian", "Bayarri", 3500000.50, "10/12/2022"))
        users.add(User(2802L, "AHOZ", "lock_password", "Aylen", "Hoz", 200000.50, "11/01/2021"))
        users.add(User(1510L, "Diegote", "@12345", "Diego", "Gonzalez", 120000.0, "15/04/2018"))
    }

    fun login(nickName: String, password: String): User? {
        val user = this.users.find { nickName.equals(it.nickName, true) && password == it.password }
        return user
    }

    fun getUserLogged(): User? {
        return users.find { it.logged }
    }

    fun userNameExists(nickName : String): Boolean {
        return users.any { it.nickName == nickName }
    }

    fun addUser(nickName: String, password: String, name: String, suerName: String): Boolean {
        val id = users.maxOfOrNull { it.id }?.plus(1)


        return users.add(
            User(
                id!!,
                nickName = nickName,
                password = password,
                name = name,
                suerName,
                money = 120000.0,
                createdDate = LocalDate.now()
                    .toString(),
                logged = true
            )
        )
    }
}