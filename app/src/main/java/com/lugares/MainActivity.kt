package com.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lugares.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.btLogin.setOnClickListener{haceLogin()}
        binding.btLogin.setOnClickListener{haceRegister()}
    }

    private fun haceLogin(){
        var email = binding.etEmail.text.toString()
        var clave = binding.etClave.text.toString()

        // Se hace el registro en Firebase

        auth.createUserWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    Log.d("Autenticando", "Usuario creado ")
                    val user = auth.currentUser
                    actualiza(user)
                }
                else{
                    Log.d("Autenticando", "Login falló ")
                    actualiza(null)
                }
            }
    }

    private fun actualiza(user: FirebaseUser?) {
        if (user != null){
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }

    private fun haceRegister(){
        var email = binding.etEmail.text.toString()
        var clave = binding.etClave.text.toString()

        // Se hace el registro en Firebase
        auth.signInWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    Log.d("Autenticando", "Login usuario ")
                    val user = auth.currentUser
                    actualiza(user)
                } else{
                    Log.d("Autenticando", "Registro falló ")
                    actualiza(null)
                }
            }
    }
}