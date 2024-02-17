package com.silverstone.jsonfikra

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PagerIndicator(
    currentPage: Int,
    pageCount: Int,
    onPageSelected: (Int) -> Unit,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit
) {
    val visiblePageCount = 5
    val startPage = maxOf(0, currentPage - visiblePageCount / 2)
    val endPage = minOf(startPage + visiblePageCount, pageCount)

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        IconButton(
            onClick = { onPreviousPage() },
            enabled = currentPage > 0,
            modifier = Modifier
                .padding(2.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Cyan)
                .border(width = 2.dp, color = Color.Gray, shape = CircleShape)

        ) {
            Icon(painter = painterResource(id = R.drawable.back),"")
        }
        Row(modifier = Modifier.size(250.dp),
              horizontalArrangement = Arrangement.Center  ) {
            for (pageIndex in startPage until endPage) {
                val isSelected = pageIndex == currentPage
                val textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = if (isSelected) 30.sp else 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )

                val indicatorText = "${pageIndex + 1}"
                Text(
                    text = indicatorText,
                    style = textStyle,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onPageSelected(pageIndex)
                        }
                )
            }
        }

        IconButton(
            onClick = { onNextPage() },
            enabled = currentPage < pageCount - 1,
            modifier = Modifier
                .padding(2.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Cyan)
                .border(width = 2.dp, color = Color.Gray, shape = CircleShape)

        ) {
            Icon(painter = painterResource(id = R.drawable.next),"")
        }
    }
}