package com.example.saludo

import android.graphics.Paint.Style
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.saludo.ui.theme.SaludoTheme
import java.util.Properties

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaludoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Saludo()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Saludo(){
    var myVal by rememberSaveable { mutableStateOf( "")}
    var show by rememberSaveable { mutableStateOf(false)}
    var saludo = "Saludar"
    var mensaje = ""
    var contadorA = 0
    var contadorC = 0
    ConstraintLayout(modifier = Modifier.fillMaxSize()){
        val (box1, box2) = createRefs()


        Box(modifier = Modifier
            .constrainAs(box1){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }){
            Button(onClick = { show = true }) {
                Text(text = saludo)
            }
        }

        Box(modifier = Modifier
            .constrainAs(box2) {
                top.linkTo(box1.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }){
            TextField(value = mensaje, onValueChange = { mensaje = it })
        }
        if(show){
            Recuadro(
                onDismissRequest = { show = false },
                myVal = myVal,
                onValueChange = { myVal = it},
                aceptar = { show = false
                contadorA ++
                mensaje = "Hola $myVal"
                saludo = "A$contadorA C$contadorC"          },
                limpiar = {myVal = ""
                          contadorA = 0
                          contadorC = 0
                          saludo = "Saludar"
                          mensaje = ""},
                cancelar = {show = false
                    myVal = ""
                    contadorC ++
                    saludo = "A$contadorA C$contadorC"
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Recuadro(
    onDismissRequest: () -> Unit,
    myVal: String,
    onValueChange: (String) -> Unit,
    aceptar: () -> Unit,
    limpiar: () -> Unit,
    cancelar: () -> Unit
){
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier.fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically)
                .background(color = Color.White)
                .padding(top = 15.dp)
                .padding(bottom = 20.dp)
        ) {
            Text(text = "Configuración", modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(
                    Alignment.TopEnd
                ), style = TextStyle(fontSize = 24.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = myVal,
                onValueChange = { onValueChange(it) },
                label = { Text(text = "Introduce tu nombre")})
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = { aceptar() },
                    modifier = Modifier.padding(3.dp)) {
                    Text(text = "Aceptar")
                }
                Button(onClick = { limpiar() },
                    modifier = Modifier.padding(3.dp)) {
                    Text(text = "Limpiar")
                }
                Button(onClick = { cancelar() },
                    modifier = Modifier.padding(3.dp)) {
                    Text(text = "Cancelar")
                }
            }
        }
    }
}



