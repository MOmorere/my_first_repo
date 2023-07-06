package com.example.moivesbox.Ui.Register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.moivesbox.Modlse.Register.Body_Users
import com.example.moivesbox.R
import com.example.moivesbox.Utils.UserDataStore
import com.example.moivesbox.databinding.FragmentRegisterBinding
import com.example.moivesbox.viewmodl.Register_Viewmodel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class Register : Fragment() {
    //Binding
    private lateinit var binding: FragmentRegisterBinding
    //ViewModel
    val ViewModel: Register_Viewmodel by viewModels()
    //UserDataStore
    @Inject
    lateinit var DataStore: UserDataStore
    //Body
    @Inject
    lateinit var Body: Body_Users

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitView
        binding.apply {
            //click_on_btn
            logInBtn.setOnClickListener { View ->
                var name = nameEdt.text.toString()
                var email = emailEdt.text.toString()
                var password = passEdt.text.toString()
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    Body.name = name
                    Body.email = email
                    Body.password = password
                } else {
                    Snackbar.make(
                        View,
                        getString(R.string.please_fill_all_the_blanks),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }


                //Response
                ViewModel.Register_User.observe(viewLifecycleOwner){ response->
                    lifecycle.coroutineScope.launchWhenCreated {
                        DataStore.Save_User_Token(response.name)
                    }
                }

                ViewModel.loading.observe(viewLifecycleOwner) { IsShown ->
                    if (IsShown) {
                        progressBar.visibility = android.view.View.VISIBLE
                        logInBtn.visibility = android.view.View.INVISIBLE
                    } else {
                        progressBar.visibility = android.view.View.INVISIBLE
                        logInBtn.visibility = android.view.View.VISIBLE
                    }
                }
                //send_data
                ViewModel.register_user(Body)
            }

        }
    }
}