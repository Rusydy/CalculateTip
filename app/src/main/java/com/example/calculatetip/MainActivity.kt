package com.example.calculatetip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculatetip.ui.theme.CalculateTipTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculateTipTheme {
                TipLayout()
            }
        }
    }
}

@Composable
fun TipLayout() {
    val billAmount = remember { mutableStateOf("") }
    val tipAmount = remember { mutableDoubleStateOf(0.00) }
    val tipPercentage = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 40.dp)
                .safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.calculate_tip),
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 40.dp)
                    .align(alignment = Alignment.Start)
            )

            InputField(
                value = billAmount.value,
                onValueChange = {
                    billAmount.value = it
                },
                label = stringResource(R.string.bill_amount)
            )

            InputField(
                value = tipPercentage.value,
                onValueChange = {
                    tipPercentage.value = it
                    tipAmount.doubleValue = calculateTip(billAmount.value.toDoubleOrNull() ?: 0.0, tipPercentage.value.toIntOrNull() ?: 15)
                },
                label = "Tip Percentage"
            )

            Text(
                text = stringResource(
                    R.string.tip_amount, String.format(
                        Locale("en", "US"), "%.2f", tipAmount.doubleValue
                    )
                ), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.displaySmall
            )

            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Number
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )
}

fun calculateTip(billAmount: Double, tipPercentage: Int = 15): Double {
    return billAmount * tipPercentage / 100
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    CalculateTipTheme {
        TipLayout()
    }
}