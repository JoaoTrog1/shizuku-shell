package joao.trog.shizuku.shell

import android.content.Context
import android.content.pm.PackageManager
import rikka.shizuku.Shizuku
import java.io.BufferedReader
import java.io.InputStreamReader

@Suppress("DEPRECATION")
class ShizukuUtils {

    companion object {
        const val REQUEST_CODE_SHIZUKU = 1001
    }


    fun isShizukuActive(): Boolean {
        return Shizuku.pingBinder()
    }

    fun checkShizukuPermission(context: Context): Boolean {
        return if (Shizuku.pingBinder()) {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }

    fun requestShizukuPermission(context: Context) {
        if (Shizuku.pingBinder() && Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED) {
            Shizuku.requestPermission(REQUEST_CODE_SHIZUKU)
        }
    }

    fun executeShellCommandWithShizuku(command: String): String {
        var response = ""
        try {
            val cmd = command.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val process = Shizuku.newProcess(cmd, null, null)
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
            response = output.toString()
            reader.close()
            process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response;
    }
}