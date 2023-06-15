package engine

import java.io.*
import java.net.URISyntaxException
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer
import javax.swing.JOptionPane

object UserManager {
    private var USER_DATA_FILE: File? = null
    fun createUsersFile() {
        try {
            val path = UserManager::class.java.protectionDomain.codeSource.location.toURI().path
            val jarDir = File(path).parentFile
            USER_DATA_FILE = File(jarDir, "users.txt")
            if (!USER_DATA_FILE!!.exists()) {
                if (!USER_DATA_FILE!!.createNewFile()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to create users file",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    )
                } else {
                    BufferedWriter(FileWriter(USER_DATA_FILE, true)).use { writer ->
                        writer.write("admin,123,0") // Add admin user
                        writer.newLine()
                        writer.flush() // flush the changes immediately.
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    /* forEach method for the users
     This method reads the users.txt file line by line, splits each line into 3 parts and puts them into a
     String[] where indices 0, 1, 2 are username, password, high score. The method takes a
     Consumer<String[]> as an argument, which represents the user array. This method will be called
     with a lambda expression that will run for each user that is registered (except for admin).
     The overloaded version gets a parameter for sorting prior to performing the action.*/
    @JvmStatic
    fun forEach(action: Consumer<Array<String>>) {
        try {
            BufferedReader(FileReader(USER_DATA_FILE)).use { reader ->
                var line: String
                while (reader.readLine().also { line = it } != null) {
                    val parts = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    if (parts.size == 3 && parts[0] != "admin") {
                        action.accept(parts)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun forEach(sorted: Boolean, action: Consumer<Array<String>>) {
        if (sorted) {
            val usersToSort = ArrayList<Array<String>>()
            forEach { e: Array<String> -> usersToSort.add(e) }
            usersToSort.sortWith(Comparator.comparingInt { user: Array<String> -> -user[2].toInt() })
            usersToSort.forEach(action)
        } else {
            forEach(action)
        }
    }

    @JvmStatic
    fun forEach(ignoreAdmin: String, action: Consumer<Array<String>>) { // a hacky solution
        if (ignoreAdmin == "no") {
            try {
                BufferedReader(FileReader(USER_DATA_FILE)).use { reader ->
                    var line: String
                    while (reader.readLine().also { line = it } != null) {
                        val parts = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (parts.size == 3) {
                            action.accept(parts)
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            forEach(action)
        }
    }

    @JvmStatic
    fun clearFile() {
        try {
            PrintWriter(USER_DATA_FILE).use { writer -> writer.print("") }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun addUser(userCredentials: String?) {
        try {
            BufferedWriter(FileWriter(USER_DATA_FILE, true)).use { writer ->
                writer.write(userCredentials)
                writer.newLine()
            }
        } catch (ignored: IOException) {
        }
    }

    @JvmStatic
    val userCount: Int
        get() {
            val count = AtomicInteger()
            forEach { u: Array<String>? -> count.getAndIncrement() }
            return count.get()
        }

    @JvmStatic
    fun getHighScoreForUser(username: String): Int {
        val highScore = AtomicInteger()
        forEach { user: Array<String> ->
            if (user[0] == username) {
                highScore.set(user[2].toInt())
            }
        }
        return highScore.get()
    }
}
