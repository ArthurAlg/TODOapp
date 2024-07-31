package com.example.apptodo.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material.Card
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.apptodo.data.Tarea
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: TareaViewModel){
    //Obtienes acceso a la lista de tareas
    //Lo puede usar ahora Jetpack Compose como state
    val todos by viewModel.TODO.collectAsState()

    //dialogOpen es el valor y setDialogOpen es la llamada
    val (dialogOpen, setDialogOpen) = remember{
        mutableStateOf(false)
    }

    //showCompletedTasks es el valor y setShowCompletedTasks es la llamada
    val (showCompletedTasks, setShowCompletedTasks) = remember { mutableStateOf(false) }

    //Logica del dialogo
    if(dialogOpen){
        //Se crea valor titulo
        val (title, setTitle) = remember{
            mutableStateOf("")
        }

        //Se crea valor descripcion
        val (description, setDescription) = remember {
            mutableStateOf("")
        }

        Dialog(onDismissRequest = {setDialogOpen(false)}) {//Si se presiona afuera se cierra
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.DarkGray),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Agregar Tarea")
                
                OutlinedTextField(
                    value = title,
                    onValueChange = {setTitle(it)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    label = { Text("Title")}
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = {setDescription(it)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    label = { Text("Description")}
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        //Se hace un seguro antes
                        if(title.isNotEmpty() && description.isNotEmpty()){
                            //Se llama a la instruccion
                            viewModel.addTODO(
                                //Se agrega el titulo y descripcion
                                Tarea(title = title, description = description)
                            )
                            setDialogOpen(false)
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enviar")
                }
            }
            
        }
    }

    Scaffold(
        topBar = { TopAppBar(
            title = {
                Text(text = if (showCompletedTasks) "Tareas Realizadas" else "Tareas Pendientes",
                    modifier = Modifier.padding(top = 8.dp))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(4.dp),
            actions = {
                Icon(
                    imageVector = if (showCompletedTasks) Icons.Default.List else Icons.Default.Check,
                    contentDescription = if (showCompletedTasks) "Show Pending Tasks" else "Show Completed Tasks",
                    modifier = Modifier
                        .padding(4.dp, top = 8.dp)
                        .clickable {
                            setShowCompletedTasks(!showCompletedTasks)
                        }
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { setDialogOpen(true) },
                containerColor = Color.Black
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
    ){pd ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(pd)
            .background(Color.Gray),
            contentAlignment = Alignment.Center
        ){

            //Lista de tareas filtradas por state
            val filteredTodos = todos.filter { it.state == showCompletedTasks }


            //En caso on halla nada en la lista
            AnimatedVisibility(
                visible = filteredTodos.isEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Text(
                    text = "No hay nada que mostrar",
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }
            //Cuando halla algo
            AnimatedVisibility(
                visible = filteredTodos.isNotEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredTodos, key = {it.id}){todo->
                        TareaItem(tarea = todo,
                            onDelete = {viewModel.deleteTODO(todo)},
                            onUpdate = { updatedTodo ->
                            viewModel.updateTODO(updatedTodo)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun TareaItem(tarea: Tarea,onDelete:()->Unit,onUpdate:(Tarea) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable {

            },
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween){
            Column(Modifier.padding(16.dp)) {
                Text(text = tarea.title, textAlign = TextAlign.Left, color = Color.Black)
                Text(text = tarea.description, textAlign = TextAlign.Left, color = Color.Black)
            }
            Column {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .clickable { onDelete() }
                        .size(30.dp)
                )
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Update",
                    modifier = Modifier
                        .clickable { onUpdate(tarea.copy(state = true)) }
                        .size(30.dp)
                )
            }
        }
    }
}
