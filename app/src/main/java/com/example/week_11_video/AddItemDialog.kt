package com.example.week_11_video

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.example.week_11_video.databinding.FragmentAddItemBinding
import com.example.week_11_video.model.Item
import com.example.week_11_video.model.Store

class AddItemDialog:DialogFragment() {
    companion object{
        fun create(onItemAddedListener:(Item) -> Unit):AddItemDialog{
            return AddItemDialog().apply {
                this.onItemAddedListener = onItemAddedListener
            }
        }

        private const val PICK_IMAGE = 101
    }

    var onItemAddedListener : (Item) -> Unit = {}
    var imageUri = "".toUri()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val binding = FragmentAddItemBinding.inflate(inflater)

        val items = Store.values().map { it.description }
        val adapter = ArrayAdapter(requireContext(), R.layout.store_listing, items)
        (binding.storeSelectionInputLayout .editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.addImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            //intent.addCategory(Intent.CATEGORY_OPENABLE);

            //intent.setType("image/*")
            startActivityForResult(intent, PICK_IMAGE)
        }


        return AlertDialog.Builder(requireContext())
                .setView(binding.root)
                .setPositiveButton("Add"){_,_ ->
                    binding.apply {
                        val newItem = Item(
                                name = titleEditText.text?.toString()?: "",
                                price = priceEditText.text?.toString()?:"",
                                description = descriptionEditText.text?.toString()?:"",
                                store = storeSelectionInputLayout.editText?.text?.toString()?:"",
                                image = imageUri.toString()
                        )


                        //Repository.addItem(newItem)
                        onItemAddedListener(newItem)
                    }
                }
                .setNegativeButton("Cancel",null)
                .create()



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            intent?.run{
                imageUri = data ?: "".toUri()

                // this is the special bit, here you're grabbing any flags that were previously
                // attached to the intent, then you're using a bitwise `and` and a bitwise `or`
                // (these are low level linux operations in regards to permissions) to get the
                // permissions to hold on to access to the URI forever
                val takeFlags = flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                // now you need to let Android know you want these permissions forever
                requireActivity().contentResolver.takePersistableUriPermission(imageUri, takeFlags)

                val addImage = dialog?.findViewById<ImageView>(R.id.add_image)
                addImage?.setImageURI(imageUri)
            }


        }
    }
}