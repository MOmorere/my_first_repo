package com.example.moivesbox.Ui.Splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.moivesbox.R
import com.example.moivesbox.Utils.UserDataStore
import com.example.moivesbox.databinding.FragmentSplashBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
@AndroidEntryPoint
class Splash : Fragment() {

    //Binding
    private lateinit var binding: FragmentSplashBinding
    //Data_Store
    @Inject
   lateinit var Data_Store:UserDataStore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentSplashBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //User-Check
        lifecycle.coroutineScope.launchWhenCreated {
            delay(3000)
            Data_Store.get_user_token().collect(){
                if (it.isEmpty()){
                    findNavController().navigate(R.id.action_splash_to_register)
                }else{
                    findNavController().navigate(R.id.action_splash_to_home)
                }
            }
        }
    }
}