package com.example.lifecityandroid.login

import android.content.Intent
import android.util.Patterns
import android.os.Bundle
import retrofit2.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.lifecityandroid.R
import com.example.lifecityandroid.databinding.FragmentLoginBinding
import com.example.lifecityandroid.network.*
import com.example.lifecityandroid.network.LoginProperty
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,
            R.layout.fragment_login,container,false)
        binding.loginRegisterBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigation_login_to_navigation_register))
        binding.loginConfirmBtn.setOnClickListener{


            val currentLogin = LoginProperty(username.text.toString(), password.text.toString())

            val requestCall: Call<String> = LoginApi.retrofitService.logIn(currentLogin)


            requestCall.enqueue(object: Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable){

                    Toast.makeText(context,"test no response" + t, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ){
                    if(response.isSuccessful){
                        val loginResponse = response.body()
                        var loggedInUserProperty : String? = response.body()
                        Toast.makeText(context, "ingelogd", Toast.LENGTH_SHORT).show()
                        view!!.findNavController().navigate(R.id.action_navigation_login_to_navigation_logged_in)


                    }else{
                        if(username.text.toString() == "") {
                            Toast.makeText(context, "Geef een email in", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(context, "Kon niet inloggen", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })


        }
        return binding.root
    }
}