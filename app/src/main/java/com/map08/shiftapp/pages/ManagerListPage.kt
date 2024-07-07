package com.map08.shiftapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.map08.shiftapp.LocalManagerViewModel
import com.map08.shiftapp.R
import com.map08.shiftapp.models.Manager

@Composable
fun ManagerListPage() {
    val managerViewModel = LocalManagerViewModel.current
    val managerList by managerViewModel.managerList.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF1976D2)),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(managerList) { manager ->
            ManagerCard(manager)
        }
    }
}

@Composable
fun ManagerCard(manager: Manager) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val painter = rememberAsyncImagePainter(
                model = manager.profileImageUrl,
                placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                error = painterResource(id = R.drawable.ic_profile_placeholder)
            )

            Image(
                painter = painter,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(32.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = manager.name, fontSize = 20.sp, color = Color.Black)
                Text(text = manager.phone, fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}
