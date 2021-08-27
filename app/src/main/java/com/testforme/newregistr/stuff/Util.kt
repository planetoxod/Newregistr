package com.your_teachers.trafficrules.stuff

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.DisplayMetrics
import java.io.*

object Util {

    fun pxFromDp(dp: Float, context: Context) = (dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).toInt()

    fun dpFromPx(px: Float, context: Context) = (px / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).toInt()

    fun compressBitmap(context: Context, reqWidth: Int, reqHeight: Int, uri: Uri): Bitmap? {
        var inputStream: InputStream?
        try {
            inputStream = context.contentResolver.openInputStream(uri)
            inputStream!!.let {
                val options = BitmapFactory.Options()

                options.inJustDecodeBounds = true
                BitmapFactory.decodeStream(it, null, options)
                it.close()
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

                inputStream = context.contentResolver.openInputStream(uri)
                options.inJustDecodeBounds = false
                val result = BitmapFactory.decodeStream(inputStream, null, options)
                inputStream!!.close()

                System.gc()
                return result
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } catch (e: NullPointerException) {
            e.printStackTrace()
            return null
        }
    }

    fun getFileFromBitmap(context: Context, bitmap: Bitmap): File? {
        try {
            //create a file to write bitmap data
            val f = File(context.cacheDir, "temporary_file.jpeg")
            f.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()

            return f
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    private fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {

        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }

        return inSampleSize
    }

}
