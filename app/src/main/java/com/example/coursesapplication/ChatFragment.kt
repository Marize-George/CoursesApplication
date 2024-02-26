
package com.example.coursesapplication

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class ChatFragment : Fragment() {

    private lateinit var messageContainer: LinearLayout
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var firestore: FirebaseFirestore

    var receiverRoom: String? = null
    var senderRoom: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_chat, container, false)
        val intent = Intent()
        val name = intent.getStringExtra("name")
//        val receiverUid = intent.getStringExtra("uid")
        val receiverUid = "receiver_user_id" // Replace this with the actual receiver's UID
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        messageContainer = view.findViewById(R.id.messageContainer)
        messageBox = view.findViewById(R.id.message)
        sendButton = view.findViewById(R.id.sentButton)

//        firestore.collection("chats").document(senderRoom!!).collection("messages")
        firestore.collection("messages")

            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    // Handle the error
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    for (documentChange in snapshot.documentChanges) {
                        if (documentChange.type == DocumentChange.Type.ADDED) {
                            val message = documentChange.document.toObject(Message::class.java)
                            if (message.senderId == senderUid) {
                                displaySenderMessage(message)
                            } else {
                                displayReceiverMessage(message)
                            }
//                            displayMessage(message)
                        }
                    }
                }
            }
        var isFirstValue = true

        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderUid)

            firestore.collection("chats").document(senderRoom!!).collection("messages").add(messageObject)
                .addOnSuccessListener { senderDocRef ->
                    firestore.collection("messages").document(receiverRoom!!).collection("chats").add(messageObject)
                        .addOnSuccessListener { receiverDocRef ->
                            if (isFirstValue) {
                                displaySenderMessage(messageObject)
                            } else {
                                displayReceiverMessage(messageObject)
                            }

                            showNotification(message)

                            isFirstValue = !isFirstValue // Toggle the value for the next message

                            messageBox.setText("")
                        }
                        .addOnFailureListener { e ->
                            // Handle the failure
                        }
                }
                .addOnFailureListener { e ->
                    // Handle the failure
                }
        }

        val dotImage: ImageButton = view.findViewById(R.id.dots)
        dotImage.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Log out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Log out") { _, _ ->
                    // Perform logout actions (e.g., clear user data, revoke tokens)
                    // ...

                    // Navigate to MainActivity after logout
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
        return view
    }

    private fun showNotification(message: String) {
        val channelId = "message_channel"
        val channelName = "Message Channel"

        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel for devices running Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Create the notification
        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("New Message")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Show the notification
        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun displaySenderMessage(message: Message) {
        val textView = TextView(requireContext())
        textView.text = message.message
        textView.setPadding(10, 10, 10, 10)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.END

        textView.layoutParams = layoutParams
        messageContainer.addView(textView)
    }
    private fun displayReceiverMessage(message: Message) {
        val textView = TextView(requireContext())
        textView.text = message.message
        textView.setPadding(10, 10, 10, 10)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.START

        textView.layoutParams = layoutParams
        messageContainer.addView(textView)
    }


}








//class ChatFragment : Fragment() {
//
//    private lateinit var messageContainer: LinearLayout
//    private lateinit var messageBox: EditText
//    private lateinit var sendButton: ImageView
//    private lateinit var firestore: FirebaseFirestore
//
//    var receiverRoom: String? = null
//    var senderRoom: String? = null
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view: View = inflater.inflate(R.layout.fragment_chat, container, false)
//        val intent = Intent()
//        val name = intent.getStringExtra("name")
//        val receiverUid = intent.getStringExtra("uid")
//
//        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
//        firestore = FirebaseFirestore.getInstance()
//
//        senderRoom = receiverUid + senderUid
//        receiverRoom = senderUid + receiverUid
//
//        messageContainer = view.findViewById(R.id.messageContainer)
//        messageBox = view.findViewById(R.id.message)
//        sendButton = view.findViewById(R.id.sentButton)
//
////        firestore.collection("chats").document(senderRoom!!).collection("messages")
//        firestore.collection("messages")
//
//            .addSnapshotListener { snapshot, e ->
//                if (e != null) {
//                    // Handle the error
//                    return@addSnapshotListener
//                }
//
//                if (snapshot != null) {
//                    for (documentChange in snapshot.documentChanges) {
//                        if (documentChange.type == DocumentChange.Type.ADDED) {
//                            val message = documentChange.document.toObject(Message::class.java)
//                            displayMessage(message)
//                        }
//                    }
//                }
//            }
//
//        sendButton.setOnClickListener {
//            val message = messageBox.text.toString()
//            val messageObject = Message(message, senderUid)
//
//            firestore.collection("chats").document(senderRoom!!).collection("messages").add(messageObject)
//
//                .addOnSuccessListener { senderDocRef ->
//                    firestore.collection("messages").document(receiverRoom!!).collection("chats").add(messageObject)
//                        .addOnSuccessListener { receiverDocRef ->
//                            val newMessage = Message(message, senderUid)
//                            displayMessage(newMessage)
//                            showNotification(message)
//                        }
//                        .addOnFailureListener { e ->
//                            // Handle the failure
//                        }
//                }
//                .addOnFailureListener { e ->
//                    // Handle the failure
//                }
//
//            messageBox.setText("")
//        }
//
//        val dotImage: ImageButton = view.findViewById(R.id.dots)
//        dotImage.setOnClickListener {
//            MaterialAlertDialogBuilder(requireContext())
//                .setTitle("Log out")
//                .setMessage("Are you sure you want to log out?")
//                .setPositiveButton("Log out") { _, _ ->
//                    // Perform logout actions (e.g., clear user data, revoke tokens)
//                    // ...
//
//                    // Navigate to MainActivity after logout
//                    val intent = Intent(requireActivity(), MainActivity::class.java)
//                    startActivity(intent)
//                }
//                .setNegativeButton("Cancel", null)
//                .show()
//        }
//        return view
//    }
//
//    private fun showNotification(message: String) {
//        val channelId = "message_channel"
//        val channelName = "Message Channel"
//
//        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Create a notification channel for devices running Android Oreo and above
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        // Create the notification
//        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
//            .setSmallIcon(R.drawable.notification_icon)
//            .setContentTitle("New Message")
//            .setContentText(message)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//        // Show the notification
//        notificationManager.notify(0, notificationBuilder.build())
//    }
//    private fun displayMessage(message: Message) {
//        val textView = TextView(requireContext())
//        textView.text = message.message
//        textView.setPadding(10, 10, 10, 10)
//
//        val layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//
//        if (message.senderId == FirebaseAuth.getInstance().currentUser?.uid) {
//            layoutParams.gravity = Gravity.END
//        } else {
//            layoutParams.gravity = Gravity.START
//        }
//
//        textView.layoutParams = layoutParams
//        messageContainer.addView(textView)
//    }
////    private fun displayMessage(message: Message) {
////        val textView = TextView(requireContext())
////        textView.text = message.message
////        textView.setPadding(10, 10, 10, 10)
////
////        val layoutParams = LinearLayout.LayoutParams(
////            LinearLayout.LayoutParams.WRAP_CONTENT,
////            LinearLayout.LayoutParams.WRAP_CONTENT
////        )
////        layoutParams.gravity = if (message.senderId == FirebaseAuth.getInstance().currentUser?.uid) {
////            Gravity.END
////        } else {
////            Gravity.START
////        }
////
////        textView.layoutParams = layoutParams
////        messageContainer.addView(textView)
////    }
//
//}
