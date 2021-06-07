package oc.android_exercice.sequence1_todo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.Toast

//SettingsActivity hérite de PreferenceActivity
class SettingsActivity : PreferenceActivity(), OnPreferenceChangeListener {
    var edtLogin: EditTextPreference? = null
    var edtBaseUrl: EditTextPreference? = null
    var sp: SharedPreferences? = null;
    var logout: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.activity_settings)
        edtLogin = findPreference("login") as EditTextPreference
        edtBaseUrl = findPreference("baseURL") as EditTextPreference
        edtLogin?.setOnPreferenceChangeListener(this)
        edtBaseUrl?.setOnPreferenceChangeListener(this)
        sp = PreferenceManager.getDefaultSharedPreferences(this)
    }


    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        var sp_editor: SharedPreferences.Editor? = sp?.edit()
        if (preference.key == "login") {
            val t =
                Toast.makeText(this, "Nouveau login : " + newValue.toString(), Toast.LENGTH_SHORT)
            t.show()
            sp_editor?.putString("pseudo", newValue.toString())
            sp_editor?.commit()
        }
        if (preference.key == "baseURL") {
            val t = Toast.makeText(
                this,
                "Nouveau URL de base : " + newValue.toString(),
                Toast.LENGTH_SHORT
            )
            t.show()
            sp_editor?.putString("baseURL", newValue.toString())
            sp_editor?.commit()
        }
        return true
    }
}
