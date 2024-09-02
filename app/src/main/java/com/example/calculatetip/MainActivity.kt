package com.example.calculatetip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
    val roundUp = remember { mutableStateOf(false) }

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
                icon = ImageVector.vectorResource(id = R.drawable.baseline_money_24),
                value = billAmount.value,
                onValueChange = {
                    billAmount.value = it
                },
                label = stringResource(R.string.bill_amount)
            )

            Spacer(modifier = Modifier.height(16.dp))

            InputField(
                icon = ImageVector.vectorResource(id = R.drawable.baseline_percent_24),
                value = tipPercentage.value,
                onValueChange = {
                    tipPercentage.value = it
                    tipAmount.doubleValue = calculateTip(
                        billAmount.value.toDoubleOrNull() ?: 0.0,
                        tipPercentage.value.toIntOrNull() ?: 15
                    )
                },
                label = stringResource(id = R.string.tip_percentage)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.round_up_tip))
                Switch(checked = roundUp.value, onCheckedChange = {
                    roundUp.value = it
                    tipAmount.doubleValue = calculateTip(
                        billAmount.value.toDoubleOrNull() ?: 0.0,
                        tipPercentage.value.toIntOrNull() ?: 15,
                        roundUp.value
                    )
                })
            }

            Spacer(modifier = Modifier.height(16.dp))

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
    icon: ImageVector? = null,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Number
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Icon(
                    imageVector = icon
                        ?: ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = label)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType, imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        })
    )
}

fun calculateTip(billAmount: Double, tipPercentage: Int = 15, roundUp: Boolean = false): Double {
    val tip = billAmount * tipPercentage / 100
    return if (roundUp) kotlin.math.ceil(tip) else tip
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    CalculateTipTheme {
        TipLayout()
    }
}