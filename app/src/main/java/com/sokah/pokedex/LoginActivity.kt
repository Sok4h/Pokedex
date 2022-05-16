package com.sokah.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sokah.pokedex.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            var newUsername = binding.inputUsername.editText?.text.toString()
            lifecycleScope.launch(Dispatchers.IO){
                var userFinder = Firebase.firestore.collection("users").document().get().await()

                if (userFinder.exists()){
                    login(newUsername)
                }else{
                    createUser(newUsername)
                }
            }

        }

    }

    private  fun  login(newUsername: String){
        val intent = Intent(applicationContext,MainActivity::class.java)
        intent.putExtra("username", newUsername)
        startActivity(intent)
    }

    private suspend fun createUser(newUsername: String){
        Firebase.firestore.collection("users").document(newUsername).set({}).await()

        val intent = Intent(applicationContext,MainActivity::class.java)
        intent.putExtra("username", newUsername)
        startActivity(intent)
    }

}