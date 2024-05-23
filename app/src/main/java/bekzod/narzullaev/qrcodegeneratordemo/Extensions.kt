package bekzod.narzullaev.qrcodegeneratordemo

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

fun String.generateBarcode(): Bitmap? {
    var bitmap: Bitmap? = null
    if (this.isNotBlank()) {
        try {
            val mwriter = MultiFormatWriter()
            val matrix = mwriter.encode(this, BarcodeFormat.CODE_128, 300, 80)

            // Creating a bitmap to represent the barcode
            bitmap = Bitmap.createBitmap(300, 80, Bitmap.Config.RGB_565)

            // Iterating through the matrix and set pixels in the bitmap
            for (i in 0 until 300) {
                for (j in 0 until 80) {
                    bitmap.setPixel(
                        i,
                        j,
                        if (matrix[i, j]) Color.BLACK else Color.WHITE
                    )
                }
            }
        } catch (e: Exception) {
            Log.d("TAG", "generateBarcode: error")
        }
    }
    return bitmap
}

fun String.generateQrcode(): Bitmap? {
    var bitmap: Bitmap? = null
    if (this.isNotBlank()) {
        try {
            val qrgEncoder = QRGEncoder(this, null, QRGContents.Type.TEXT, 500)
            qrgEncoder.colorBlack = Color.WHITE
            qrgEncoder.colorWhite = Color.BLACK
            bitmap = qrgEncoder.bitmap
        } catch (e: Exception) {
            Log.d("TAG", "generateQrcode: error")
        }
    }
    return bitmap
}