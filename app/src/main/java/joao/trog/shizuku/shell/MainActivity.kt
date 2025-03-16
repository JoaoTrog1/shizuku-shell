package joao.trog.shizuku.shell

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shizukuUtils = ShizukuUtils()
        if (shizukuUtils.isShizukuActive()){
            Toast.makeText(this, "Shizuku is active", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Shizuku is not active", Toast.LENGTH_SHORT).show()
        }

        if(!shizukuUtils.checkShizukuPermission(this)){
            shizukuUtils.requestShizukuPermission(this)
        }
        val linearLayoutBackground = LinearLayout(this)
        linearLayoutBackground.orientation = LinearLayout.VERTICAL

        val editTextInput = EditText(this)
        editTextInput.hint = "Command:"

        val buttonExecute = Button(this)
        buttonExecute.text = "Execute"

        val editTextOutput = EditText(this)
        editTextOutput.hint = "Output"


        buttonExecute.setOnClickListener{
            if(shizukuUtils.checkShizukuPermission(this)){
                val input = editTextInput.text
                val output = shizukuUtils.executeShellCommandWithShizuku(input.toString())
                editTextOutput.setText(output)
            }else{
                shizukuUtils.requestShizukuPermission(this)
            }
        }


        linearLayoutBackground.addView(editTextInput)
        linearLayoutBackground.addView(buttonExecute)
        linearLayoutBackground.addView(editTextOutput)
        setContentView(linearLayoutBackground)
    }

}