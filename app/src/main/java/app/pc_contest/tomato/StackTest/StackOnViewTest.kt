package app.pc_contest.tomato.StackTest

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import app.pc_contest.tomato.R


class StackOnViewTest : AppCompatActivity() {

    private lateinit var intentService: Intent

    @RequiresApi(Build.VERSION_CODES.O)
    private var launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if(Settings.canDrawOverlays(this)){

                startForegroundService(intentService)
            }
            else{
                Toast.makeText(applicationContext, "設定してください",
                    Toast.LENGTH_LONG).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp)

        intentService = Intent(application, TestService::class.java)

        val buttonStart: Button = findViewById(R.id.button_throw)
        buttonStart.setOnClickListener {

            if (Settings.canDrawOverlays(this)){
                startForegroundService(intentService)
            }
            else{
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
                launcher.launch(intent)
            }

        }
    }
}