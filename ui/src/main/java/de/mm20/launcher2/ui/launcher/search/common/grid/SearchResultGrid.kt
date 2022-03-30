package de.mm20.launcher2.ui.launcher.search.common.grid

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.mm20.launcher2.search.data.Searchable
import de.mm20.launcher2.ui.launcher.search.common.GridItem
import de.mm20.launcher2.ui.locals.LocalGridColumns
import kotlin.math.ceil

@Composable
fun SearchResultGrid(
    items: List<Searchable>,
    modifier: Modifier = Modifier,
) {

    val columns = LocalGridColumns.current
    Column(
        modifier = modifier
            .animateContentSize()
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        for (i in 0 until ceil(items.size / columns.toFloat()).toInt()) {
            Row {
                for (j in 0 until columns) {
                    val item = items.getOrNull(i * columns + j)
                    if (item != null) {
                        GridItem(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp, 8.dp), item = item
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
