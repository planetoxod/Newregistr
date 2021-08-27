package com.testforme.newregistr.configuration

import android.content.Context
import com.google.gson.Gson
import com.testforme.newregistr.User
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class SavingLoadingParameters {

    private val nameFileConfiguration = "Parameters.trs"

    fun readParameters(AppContext: Context):Parameters {
        var parameters = Parameters(
            User(0,"","","","","","")
        )
        try {
            val fis = AppContext.openFileInput(nameFileConfiguration)
            val ois = ObjectInputStream(fis)
            val ob = ois.readObject()
            if (ob.javaClass == String::class.java) {
                val jsonString = ob as String
                val gson =Gson()
                parameters = gson.fromJson(jsonString,Parameters::class.java)
            }
            ois.close()
            fis.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        if (parameters.user==null) parameters = Parameters(
            User(0,"","","","","","")
        )
        return parameters
    }

    fun writeParameters(context: Context, parameters: Parameters) {
        val gson =Gson()
        val jsonString = gson.toJson (parameters)
        try {
            val fos = context.openFileOutput(
                nameFileConfiguration,
                Context.MODE_PRIVATE
            )
            val oos = ObjectOutputStream(fos)
            oos.writeObject(jsonString)
            oos.close()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}