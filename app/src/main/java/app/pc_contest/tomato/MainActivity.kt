package app.pc_contest.tomato

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

@Suppress("ControlFlowWithEmptyBody")
class MainActivity : AppCompatActivity() {

    private lateinit var buttonHome: ImageButton
    private lateinit var buttonPomo: ImageButton
    private lateinit var buttonHelp: ImageButton

    private var launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Settings.canDrawOverlays(this)) {
            } else {
                Toast.makeText(
                    applicationContext, R.string.message_overlay,
                    Toast.LENGTH_LONG).show()
            }
        }


    @SuppressLint("ServiceCast", "BatteryLife")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        //contentViewのid紐づけ
        buttonHome = findViewById(R.id.imageButton2)
        buttonPomo = findViewById(R.id.imageButton7)
        buttonHelp = findViewById(R.id.imageButton9)

        //戻るボタン無効化設定
        onBackPressedDispatcher.addCallback(callback)

        val powerManager =
            applicationContext!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
            val intent = Intent(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) //バッテリ最適化を無効化するダイアログ表示
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)

            //ボタンごとの画面遷移先設定
            buttonHome.setOnClickListener {

            }
            buttonPomo.setOnClickListener {
                val intentPomo1 = Intent(this, PomoPage1Activity::class.java)
                startActivity(intentPomo1)
            }
            buttonHelp.setOnClickListener {
                val intentHelp = Intent(this, HelpPageActivity::class.java)
                startActivity(intentHelp)
            }
        }

        //overlay permission check
        if (Settings.canDrawOverlays(this)){}
        else{
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
            launcher.launch(intent)
        }
    }

    //戻るボタン無効化
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {}
    }

    //通知許可の確認
    /*private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted: Boolean ->
            if(isGranted) {
                //通知　許可する選択時
                viewModel.startTimer()
            } else {
                //通知　許可しない選択時

            }
        }*/
}