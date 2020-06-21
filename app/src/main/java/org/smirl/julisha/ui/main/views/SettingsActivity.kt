package org.smirl.julisha.ui.main.views;

//import kotlinx.android.synthetic.main.activity_kaninda.*

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.containt_settings.*
import org.smirl.julisha.MainActivity
import org.smirl.julisha.R
import org.smirl.julisha.ui.main.models.MyPreferences
import java.util.*

class SettingsActivity : AppCompatActivity() {
    var appTheme=2
    lateinit var sharedPreferences: SharedPreferences
    private val themeKey = "currentTheme"
    private val thememode = "currentTheme"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appTheme= MyPreferences(this).darkMode
        sharedPreferences = getSharedPreferences(
                "ThemePref",
                Context.MODE_PRIVATE
        )
        applyTheme()



        setContentView(R.layout.activity_settings)
        title="Paramètres"
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        checkTheme()
        checkStyle()


        card_theme.setOnClickListener{
            chooseThemeDialog()
        }
        card_style.setOnClickListener {
            chooseStyleDialog()
        }


    }

    private fun chooseThemeDialog(){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Mode d'apparence")
        val res=resources
        val themes=res.getStringArray(R.array.theme_mode)

        // val checkedItem=MyPreferences(this).darkMode=0
        builder.setSingleChoiceItems(themes,MyPreferences(this).darkMode){
            dialog, which ->
            when(which){
                0->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode=0
                    sharedPreferences.edit().putInt(thememode, 0).apply()


                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode=1
                    sharedPreferences.edit().putInt(thememode, 1).apply()


                    delegate.applyDayNight()
                    dialog.dismiss()

                }
                2->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

                    MyPreferences(this).darkMode=2
                    sharedPreferences.edit().putInt(thememode, 2).apply()


                    delegate.applyDayNight()
                    dialog.dismiss()

                }
            }


        }
                .setPositiveButton("Fermer",DialogInterface.OnClickListener { dialog, which ->
                })
        val dialog=builder.create()
        dialog.show()
    }

    @SuppressLint("ResourceType")
    private fun chooseStyleDialog(){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Couleur")
        val res:Resources= resources
        val styles=res.getStringArray(R.array.theme_styles)
        val stylevalue=0


        val checkedItem=sharedPreferences.getInt(themeKey,stylevalue)

        builder.setSingleChoiceItems(styles,checkedItem){
            dialog, which ->
            when(which){
                0-> {
                    sharedPreferences.edit().putInt(themeKey, 0).apply()

                    if (checkedItem==0){

                        dialog.dismiss()
                    }else {

                        val rIntent = Intent()
                        rIntent.putExtra(themeKey, 0)
                        setResult(Activity.RESULT_OK, rIntent)
                        //startActivity(rIntent)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }
                1-> {
                    sharedPreferences.edit().putInt(themeKey, 1).apply()

                    if (checkedItem==1){

                        dialog.dismiss()
                    }else {

                        val rIntent = Intent()
                        rIntent.putExtra(themeKey, 1)
                        setResult(Activity.RESULT_OK, rIntent)
                        //startActivity(rIntent)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }


                }

                2->{
                    sharedPreferences.edit().putInt(themeKey, 2).apply()
                    if (checkedItem==2){

                        dialog.dismiss()
                    }else {

                        val rIntent = Intent()
                        rIntent.putExtra(themeKey, 2)
                        setResult(Activity.RESULT_OK, rIntent)
                        //startActivity(rIntent)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }






                }

            }


        }
                .setPositiveButton("Annuler",DialogInterface.OnClickListener { dialog, which ->
                })
        val dialog=builder.create()
        dialog.show()
    }
    private fun checkStyle() {
        when (sharedPreferences.getInt(themeKey, 0)) {

            0 -> {
                style_status.text = "Rouge"


            }

            1 -> {
                style_status.text = "Move"

            }
            2 -> {
                style_status.text = "Lime"

            }

        }

    }


    @SuppressLint("ResourceAsColor")
    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                theme_mode.text = "Mode clair"

                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                theme_mode.text = "Mode sombre"


                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                theme_mode.text = "Mode Automatique"


                delegate.applyDayNight()
            }

        }

    }
    fun applyTheme(){
        when (sharedPreferences.getInt(themeKey, 0)) {

            0 -> {

                theme.applyStyle(R.style.AppTheme_NoActionBar, true)

            }
            1 -> {
                theme.applyStyle(R.style.ThemeMoveNoActionBar, true)


            }
            2 -> {
                theme.applyStyle(R.style.ThemeLimeNoActionBar, true)


            }


        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
