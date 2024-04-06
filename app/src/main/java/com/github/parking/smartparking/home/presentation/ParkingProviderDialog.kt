package com.github.parking.smartparking.home.presentation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.parking.smartparking.home.domain.model.ParkingProvider
import com.github.parking.smartparking.home.domain.model.Slot
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle


object NonDismissableDialog : DestinationStyle.Dialog {
    override val properties = DialogProperties(
        dismissOnClickOutside = false,
        dismissOnBackPress = false,
    )
}

@Destination(style = NonDismissableDialog::class)
@Composable
fun ParkingProviderDialog(
    provider: ParkingProvider,
    navigator: DestinationsNavigator,

    ) {
    Dialog(
        onDismissRequest = navigator::popBackStack,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
        )
    ) {
        val percent = animateIntAsState(
            targetValue = 5, label = "card Corner Radius",
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessMedium
            ),
        )

        var selectedSlot by remember { mutableStateOf<Slot?>(null) }

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(percent.value),
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = provider.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = provider.location,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = navigator::popBackStack,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyVerticalGrid(columns = GridCells.Adaptive(120.dp)) {
                    items(provider.slots) { slot ->
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        )
                        SlotCard(
                            slot = slot,
                            modifier = Modifier.padding(8.dp),
                            onSelect = { theSelectedSlot ->
                                selectedSlot = theSelectedSlot
                            },
                            selectedSlot = selectedSlot
                        )
                    }
                }

                selectedSlot?.let {slot: Slot ->
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Book Slot ${slot.number}")
                    }
                }

            }

        }
    }

}

