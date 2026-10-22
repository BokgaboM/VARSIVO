package com.varsivo.app.auth

import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.varsivo.app.R
import com.varsivo.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRoleDropdown()
        setupLanguageToggle()

        binding.btnLogin.setOnClickListener { attemptLogin() }
    }

    private fun setupRoleDropdown() {
        val roles = listOf(getString(R.string.role_learner), getString(R.string.role_student))
        binding.actvRole.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_list_item_1, roles)
        )
        binding.actvRole.setText(roles.first(), false)
    }

    private fun setupLanguageToggle() {
        val current = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        binding.toggleLanguage.check(
            when {
                current.startsWith("zu") -> R.id.btnLangZu
                current.startsWith("af") -> R.id.btnLangAf
                else -> R.id.btnLangEn
            }
        )

        binding.toggleLanguage.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            val tag = when (checkedId) {
                R.id.btnLangZu -> "zu"
                R.id.btnLangAf -> "af"
                else -> "en"
            }
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
        }
    }

    private fun attemptLogin() {
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        val password = binding.etPassword.text?.toString().orEmpty()

        binding.tilEmail.error = when {
            email.isEmpty() -> getString(R.string.error_email_required)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> getString(R.string.error_email_invalid)
            else -> null
        }
        binding.tilPassword.error =
            if (password.isEmpty()) getString(R.string.error_password_required) else null

        if (binding.tilEmail.error != null || binding.tilPassword.error != null) return

        // Valid input: the API call is wired in once the auth routes are confirmed.
    }
}
