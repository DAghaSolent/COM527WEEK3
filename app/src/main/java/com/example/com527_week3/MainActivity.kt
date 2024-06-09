package com.example.com527_week3

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
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
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.com527_week3.ui.theme.COM527_WEEK3Theme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            COM527_WEEK3Theme {
                val navController = rememberNavController()
                val viewModel : ShoppingListViewModel by viewModels()
                var list by remember { mutableStateOf(listOf<String>()) }

                viewModel.liveShoppingList.observe(this){
                    list = viewModel.shoppingList
                }

                // Notes below to complete Week 7 Exercise 2 exercises
                // State variable containing the shopping list (Done)
                // observe the live data in the view model, so when it changes, you update the state variable(Done)
                // pass the state variable down to the shopping list composable(Done)
                // Try and follow what you did for Week 7 Exercise 1 with the call backs that you did

                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {navController.navigate("AddItemScreen")},
                            content = {Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Item Page")}
                        )
                    }
                )
                {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(navController = navController, startDestination = "ShoppingList"){
                            composable("ShoppingList"){
                                ShoppingList(AddItemScreenCallBack = {navController.navigate("AddItemScreen")}, list)
                            }
                            composable("AddItemScreen"){
                                // don't pass in the nav controller or view model here
                                // instead, pass in a callback
                                // the callback should add the item to the view model (Done)
                                AddItemScreen(ShoppingListCallBack = {listInput ->
                                    viewModel.addItem(listInput)
                                    navController.navigate("ShoppingList")
                                })
                            }
                        }
                    }
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
fun ShoppingList(AddItemScreenCallBack: () -> Unit, list: List<String>){

    Column {
        // Lazy Column created to showcase the Shopping List out to the users.
        LazyColumn {
            items(list){
                item -> Text(item)
            }
        }
        
        Row {
            Button(onClick = { AddItemScreenCallBack() }) {
                Text("Add Item Screen")
            }
        }
    }
}

@Composable
fun AddItemScreen(ShoppingListCallBack: (String) -> Unit){
    var listInput by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column {

        Row {
            TextField(value = listInput, onValueChange = {listInput = it}, modifier = Modifier.fillMaxWidth())
        }

        Row {
            
            Button(onClick = { if(listInput.isNotBlank()){
                ShoppingListCallBack(listInput)
            } else {
                Toast.makeText(context, "Enter Item in Text Field", Toast.LENGTH_LONG).show()
            }
            }) {
                Text("Add Item to Shopping List")
            }

            Button(onClick = { listInput = "" }, modifier = Modifier.weight(1.0f)) {
                Text("Clear Item")
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