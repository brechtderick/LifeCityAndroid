package com.example.lifecityandroid.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.lifecityandroid.R
import com.example.lifecityandroid.databinding.FragmentRegisterBinding
import com.example.lifecityandroid.network.LoginApi
import com.example.lifecityandroid.network.RegisterProperty
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater,
            R.layout.fragment_register,container,false)
        binding.registerLoginInsteadBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigation_register_to_navigation_login))
        binding.registerConfirmButton.setOnClickListener{
            val currentRegister = RegisterProperty(
                registerEmail_editText.text.toString(),
                registerPassword_editText.text.toString(),
                registerNaam_editText.text.toString(),
                registerAchternaam_editText.text.toString(),
                registerPasswordConfirm_editText.text.toString()

            )

            val requestCall: Call<String> = LoginApi.retrofitService.Register(currentRegister)

            requestCall.enqueue(object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable){
                    Toast.makeText(context, "no response", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ){
                    if(response.isSuccessful){
                        var registeredUserProperty: String? = response.body()
                        Toast.makeText(context, "Geregistreerd", Toast.LENGTH_SHORT).show()
                        view!!.findNavController().navigate(R.id.action_navigation_register_to_navigation_logged_in)
                    } else {
                        if(registerEmail_editText.text.toString() == "" ||
                            registerPassword_editText.text.toString() == "" ||
                            registerNaam_editText.text.toString() == "" ||
                            registerAchternaam_editText.text.toString() == "" ||
                            registerPasswordConfirm_editText.text.toString() == ""
                        ){
                            Toast.makeText(context,"vul alle velden in", Toast.LENGTH_SHORT).show()
                        }else
                            if(registerPassword_editText.text.toString() != registerPasswordConfirm_editText.text.toString()){
                                Toast.makeText(context,"paswoorden zijn verschillend", Toast.LENGTH_SHORT).show()
                            }else
                                if(registerPassword_editText.text.toString().length < 8){
                                    Toast.makeText(context,"paswoord moet minstens 8 tekens bevatten", Toast.LENGTH_SHORT).show()
                                }else
                                    if(!Patterns.EMAIL_ADDRESS.matcher(registerEmail_editText.text.toString()).matches()){
                                        Toast.makeText(context,"email is niet geldig", Toast.LENGTH_SHORT).show()
                                    }else
                                        if(!passwordValidation(registerPassword_editText.text.toString())){
                                            Toast.makeText(context,"paswoord moet minstens 1 hoofdletter, 1 cijfer en 1 speciaal teken bevatten", Toast.LENGTH_SHORT).show()
                                        }else
                                            Toast.makeText(context,"kon niet registreren met dit email adres, hebt u al een account?", Toast.LENGTH_SHORT).show()


                    }
                }
            })
        }
        return binding.root


    }

    private fun passwordValidation(password : String): Boolean {
        var pattern: Pattern
        var matcher: Matcher
        val PASSWORD_PATTERN = """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!\-_?&])(?=\S+$).{8,}"""

        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)

        return matcher.matches()
    }
}