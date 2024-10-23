package com.proyect.class1a

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent


class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los elementos de la UI
        val btningresar: Button = findViewById(R.id.btnIngresar)
        val txtemail: TextView = findViewById(R.id.edtEmail)
        val txtpass: TextView = findViewById(R.id.edtPassword)

        // Inicializamos FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        btningresar.setOnClickListener {
            val email = txtemail.text.toString().trim()
            val password = txtpass.text.toString().trim()

            // Validación sencilla del email y la contraseña
            when {
                email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(baseContext, "Por favor, ingrese un email válido", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() || password.length < 6 -> {
                    Toast.makeText(baseContext, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Si las validaciones son correctas, se intenta iniciar sesión
                    signIn(email, password)
                }
            }
        }
    }

    private fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                Toast.makeText(baseContext, "Bienvenido", Toast.LENGTH_SHORT).show()
                // Aquí puedes redirigir a la segunda activity
                val i = Intent(this, registrarse::class.java)
                startActivity(i)
            }
            else {
                Toast.makeText(baseContext, "Error: email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}