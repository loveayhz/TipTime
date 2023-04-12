package art.ayhz.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import art.ayhz.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting("Android")
                    TipTimeScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipTimeTheme {
//        Greeting("Android")
        TipTimeScreen()
    }
}

@Composable
fun TipTimeScreen(){
    var amountInput by remember { mutableStateOf("") }
    var tipPercentInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull()?: 0.0
    val tipPercent = tipPercentInput.toDoubleOrNull()?: 0.0
    //val tip = calculateTip(amount)
    val tip = calculateTip(amount,tipPercent)
    Column(modifier = Modifier.padding(32.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(id = R.string.calculate_tip),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        EditNumberField(
            value = amountInput,
            label = R.string.bill_amount,
            onValueChange = {amountInput = it}
        )

        EditNumberField(
            value = tipPercentInput,
            label = R.string.tip_percent,
            onValueChange = {tipPercentInput = it}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.tip_amount,tip),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        
    }
}


@Composable
fun EditNumberField(value: String,@StringRes label: Int, onValueChange: (String)-> Unit,modifier: Modifier = Modifier) {

    TextField(
        value = value,
//        onValueChange = { amountInput = it},
        onValueChange = onValueChange,
        label = { Text(
            text = stringResource(id = label),
            modifier = Modifier
        )},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}

private fun calculateTip(amount: Double,tipPercent: Double = 15.0):String{
    var tip = amount * tipPercent/100
    return NumberFormat.getCurrencyInstance().format(tip)
}