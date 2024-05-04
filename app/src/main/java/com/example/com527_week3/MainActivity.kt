package com.example.com527_week3

import android.os.Bundle
import android.view.Gravity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import com.example.com527_week3.ui.theme.COM527_WEEK3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            COM527_WEEK3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MessageApp()
                }
            }
        }
    }
}

@Composable
fun FeetToMetres(){
    var feetInput by remember { mutableStateOf("") }
    var metresValue by remember { mutableStateOf(0.00) }

    Column(
        modifier = Modifier
            .border(4.dp, Color.Red)
            .padding(20.dp)
    ) {
        TextField(
            value=feetInput,
            onValueChange = { feetInput=it },
            modifier = Modifier.fillMaxWidth()
            )
        Row(){
            Button(onClick = {
                metresValue = feetInput.toDoubleOrNull()?.times(0.305) ?: 0.00
            })
            {
                Text("Convert Feet to Metres")
            }
            Button(onClick = { feetInput = " " }) {
                Text("Remove Numbers")
            }
        }

        Text("Conversion Result: ${metresValue}")
    }
}

@Composable
fun Ex1_5FtoM(){
    var feetInput by remember { mutableStateOf("") }
    var metresValue by remember { mutableStateOf(0.00) }

    Column(
        modifier = Modifier
            .border(4.dp, Color.Red)
            .padding(20.dp)
    ) {
        Row {
            TextField(value=feetInput, onValueChange = { feetInput=it }, modifier = Modifier.weight(2.0f))
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { feetInput = " " }, modifier = Modifier.weight(1.0f)) {
                Text("Clear")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            metresValue = feetInput.toDoubleOrNull()?.times(0.305) ?: 0.00
        }, modifier = Modifier.fillMaxWidth()
        )
        {
            Text("Convert Feet to Metres")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Conversion Result: ${metresValue}",
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ShoppingList(){
    var list by remember { mutableStateOf(listOf<String>()) }
    var listInput by remember { mutableStateOf("") }
    Column {
        TextField(value = listInput, onValueChange = {listInput = it}, modifier = Modifier.fillMaxWidth())

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                // Creating a temporary list to reset the list state variable to force a re render.
                var tempList = list.toMutableList()

                // Adding the item to temporary list
                tempList.add(listInput)

                // Changing the the current list state to the temp list that I setup in the button.
                list = tempList
            },
                modifier = Modifier.weight(2.0f)
                ) {
                Text("Add Item to Shopping List")
            }

            Button(onClick = { listInput = " " }, modifier = Modifier.weight(1.0f)) {
                Text("Clear Item")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Lazy Column created to showcase the Shopping List out to the users.
        LazyColumn {
            items(list) {
                    listInput -> Text(listInput)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MessageApp(){
    var messages by remember { mutableStateOf(listOf<String>()) }
    var messageInput by remember { mutableStateOf("") }

    Column {
        TextField(value = messageInput, onValueChange = {messageInput = it}, modifier = Modifier.fillMaxWidth())

        Row {
            // Button to add a message
            Button(onClick = {
                // Creating a multiple list to add to the messages list and refreshing the state so it
                // can be read on user side
                var tempMsgList = messages.toMutableList()

                // Adding the inputted message to the above temp list
                tempMsgList.add(messageInput)

                // Changing the current list state to the temp list that I setup in the button scope.
                messages = tempMsgList
            },
                modifier = Modifier.weight(1f))
            {
                Text("Add Message")
            }

            // Button to clear the message
            Button(onClick = { messageInput = " " }, modifier = Modifier.weight(1f)) {
                Text("Clear Message")
            }
        }

       LazyColumn {
           itemsIndexed(messages) {index, messageInput ->
               Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (index % 2 == 1) Arrangement.End else Arrangement.Start){
                   Surface(
                       modifier = Modifier.padding(
                           start = if(index % 2 == 1) 30.dp else 0.dp,
                           end = if(index % 2 == 1) 0.dp else 30.dp
                       ),
                       shadowElevation = 5.dp,
                       color = if(index % 2 == 1) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary,
                       shape = MaterialTheme.shapes.medium
                   ) {
                       Text(
                           messageInput,
                           modifier = Modifier.padding(8.dp),
                           if (index % 2 == 1) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary)
                   }
               }
           }
       }

    }
}