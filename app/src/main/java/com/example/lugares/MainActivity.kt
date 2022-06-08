package com.example.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lugares.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        //Se define el metodo para el login
        binding.btnLogin.setOnClickListener(){
            haceLogin()
        }

        binding.btnRegister.setOnClickListener(){
            haceRegister()
        }
    }

    private fun haceRegister() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        //SE HACE EL REGISTRO
        auth.createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    Log.d("Creando Usuario", "Registrado")
                    val user = auth.currentUser
                    actualiza(user)
                }else{
                    Log.d("Creando Usuario", "Fallo")
                    Toast.makeText(this, "Fallo en la creación del usuario", Toast.LENGTH_LONG).show()
                    actualiza(null)
                }
            }
    }

    private fun haceLogin() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        //SE HACE EL LOGIN
        auth.signInWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    Log.d("Autenticando", "Autenticado")
                    val user = auth.currentUser
                    actualiza(user)
                }else{
                    Log.d("Autenticando", "Falló")
                    Toast.makeText(baseContext, "Falló", Toast.LENGTH_LONG)
                    actualiza(null)
                }
            }
    }
    private fun actualiza(user: FirebaseUser?){
        if (user != null){
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }
    //ESTO HARÁ UNA VEZ AUTENTICADO
    public override fun onStart(){
        super.onStart()
        val usuario = auth.currentUser
        actualiza(usuario)
    }
}