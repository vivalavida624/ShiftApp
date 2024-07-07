package com.map08.shiftapp.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.util.*

fun uploadImageToFirebase(context: Context, uri: Uri, onSuccess: (String) -> Unit) {
    val storageRef = FirebaseStorage.getInstance().reference
    val imageRef = storageRef.child("profile_images/${UUID.randomUUID()}.jpg")
    val uploadTask = imageRef.putFile(uri)

    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let {
                throw it
            }
        }
        imageRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val downloadUri = task.result
            onSuccess(downloadUri.toString())
        } else {
            Toast.makeText(context, "Upload failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
