package org.dongguk.lavieenrosehotel.utility

object PasswordUtil {
    fun generateRandomPassword(): String {
        val password = StringBuilder()
        val random = java.util.Random()
        val passwordLength = random.nextInt(11) + 10
        for (i in 0 until passwordLength) {
            val type = random.nextInt(3)
            when (type) {
                0 -> password.append((random.nextInt(26) + 65).toChar())
                1 -> password.append((random.nextInt(26) + 97).toChar())
                2 -> password.append((random.nextInt(10) + 48).toChar())
            }
        }
        return password.toString()
    }
}