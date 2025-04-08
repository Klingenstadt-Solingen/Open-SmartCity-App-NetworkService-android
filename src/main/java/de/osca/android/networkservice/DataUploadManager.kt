package de.osca.android.networkservice

import android.graphics.Bitmap
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.SaveCallback
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class DataUploadManager {

    var uploadTasks: Int = 0
    var uploadTasksDone: Int = 0
    var files = ArrayList<ParseFile>()

    fun uploadImages(images: List<Bitmap>, whenFinished: (result: ArrayList<ParseFile>?, success: Boolean, ex: ParseException?) -> Unit ) {
        uploadTasks = images.size

        for (image in images) {
            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            val uuid = UUID.randomUUID()
            val file = ParseFile("$uuid.jpg", stream.toByteArray(), "image/jpeg")

            file.saveInBackground(SaveCallback {
                if (it == null) {
                    uploadTasksDone = uploadTasksDone.inc()
                    files.add(file)
                    if(uploadTasksDone == uploadTasks){
                        whenFinished.invoke(files, true, null)
                        stream.close()
                        return@SaveCallback
                    }
                } else {
                    whenFinished.invoke(files, false, it)
                    stream.close()
                    return@SaveCallback
                }
            })
        }

        if(uploadTasks == 0) {
            whenFinished.invoke(files, true, null)
        }
    }
}