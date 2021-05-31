package oc.android_exercice.sequence1_todo

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.PreferenceManager
import android.widget.Toast

//SettingsActivity hérite de PreferenceActivity
class SettingsActivity : PreferenceActivity(), OnPreferenceChangeListener {
    var edtPref: EditTextPreference? = null
    var sp: SharedPreferences? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.activity_settings)
        edtPref = findPreference("login") as EditTextPreference
        edtPref?.setOnPreferenceChangeListener(this)
        sp = PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        val t = Toast.makeText(
            this,
            "click cb :" + newValue.toString()
                    + " pref manipulée : " + preference.key, Toast.LENGTH_SHORT
        )
        t.show()

        var sp_editor: SharedPreferences.Editor? = sp?.edit()
        sp_editor?.putString("PSEUDO", newValue.toString())
        sp_editor?.commit()

        return true
    }
}