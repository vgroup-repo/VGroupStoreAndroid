package com.vgroup.vgroupstore.core

import android.content.Context
import com.google.gson.Gson
import com.vgroup.vgroupstore.domain.model.UserProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPrefs @Inject constructor(
    @ApplicationContext context: Context,
    private val gson: Gson
) {

    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveUser(email: String, token: String) {
        prefs.edit()
            .putString("email", email)
            .putString("token", token)
            .apply()
    }

    fun getEmail(): String? = prefs.getString("email", null)

    fun getToken(): String? = prefs.getString("token", "")

    fun isLoggedIn(): Boolean = !getToken().isNullOrEmpty()

    fun saveUserObject(user: UserProfile) {
        val json = gson.toJson(user)
        prefs.edit().putString("user_object", json).apply()
    }

    fun getUserObject(): UserProfile? {
        val json = prefs.getString("user_object", null) ?: return null
        return gson.fromJson(json, UserProfile::class.java)
    }


    fun logout() {
        prefs.edit().clear().apply()
    }
}
