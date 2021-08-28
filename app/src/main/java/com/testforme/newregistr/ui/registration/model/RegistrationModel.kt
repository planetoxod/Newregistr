package com.testforme.newregistr.ui.bottomSheets.Registration.model

import android.icu.text.Transliterator
import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.testforme.newregistr.objects.ZygoteUser
import com.testforme.newregistr.retrofit.ResponseBody
import com.testforme.newregistr.retrofit.RetrofitApi
import com.testforme.newregistr.ui.registration.stuff.RegistrationApi
import com.testforme.newregistr.ui.registration.stuff.RegistrationContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationModel : RegistrationContract.Model {

    override fun getUserFacebookInfo(
        accessToken: AccessToken,
        onFinishedListener: RegistrationContract.Model.OnFinishedListener
    ) {
        val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
            try {
                var firstName = `object`?.getString("first_name")
                var lastName = `object`?.getString("last_name")
                val id = `object`?.getString("id")
                var email = if (!`object`?.isNull("email")!!) `object`.getString("email")
                else "$firstName@$lastName.com"
               if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                   val CYRILLIC_TO_LATIN = "Russian-Latin/BGN"
                   val toLatinTrans: Transliterator = Transliterator.getInstance(CYRILLIC_TO_LATIN)
                   email = toLatinTrans.transliterate(email)
               } else {
                   val r= (id?.toLong() ?: 22) +13122401
                   email = "mmn$r@yauido.com"
                   firstName += '_'
                   lastName += '_'
               }
//                val imageUrl = "https://graph.facebook.com/$id/picture?type=normal"
                val zygoteUser= ZygoteUser(firstName, lastName, email)
                zygoteUser.password=id

                onFinishedListener.onFinished(zygoteUser)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "first_name, last_name, email, id")
        request.parameters = parameters
        request.executeAsync()
    }

    override fun register(
        user: ZygoteUser,
        onFinishedListener: RegistrationContract.Model.OnFinishedListener
    ) {
        val regApi = RetrofitApi.getInstance().create(RegistrationApi::class.java)

        val regQuery = regApi.singUp(user)
        regQuery.enqueue(object : Callback<List<ResponseBody>> {
            override fun onResponse(
                call: Call<List<ResponseBody>>,
                response: Response<List<ResponseBody>>
            ) {
                if (response.body() != null)
                    onFinishedListener.onFinished(response.body()!!)
            }

            override fun onFailure(call: Call<List<ResponseBody>>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

}