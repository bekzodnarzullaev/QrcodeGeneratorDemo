package bekzod.narzullaev.qrcodegeneratordemo

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bekzod.narzullaev.qrcodegeneratordemo.ui.theme.QrcodeGeneratorDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QrcodeGeneratorDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QrcodeScreen()
                }
            }
        }
    }
}

@Composable
fun QrcodeScreen() {

    var text by rememberSaveable { mutableStateOf("") }
    var qrcode by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    var barcode by rememberSaveable { mutableStateOf<Bitmap?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        AppTextFiled(
            text = text,
            onChange = {
                text = it
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                barcode = text.generateBarcode()
                qrcode = text.generateQrcode()
            },
            enabled = text.isNotBlank()
        ) {
            Text(
                text = "Generate",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        barcode?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        qrcode?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(154.dp)
            )
        }
    }
}

@Composable
fun AppTextFiled(
    text: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                onChange(it)
            },
            textStyle = TextStyle(
                fontSize = 24.sp
            ),
            modifier = modifier
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (text.isBlank()) {
                    Text(
                        text = "Enter text",
                        fontSize = 24.sp,
                    )
                }
                innerTextField()
            }
        )
    }
}