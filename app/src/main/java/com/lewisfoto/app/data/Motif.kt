package com.lewisfoto.app.data

import androidx.annotation.DrawableRes
import com.lewisfoto.app.R

enum class Motif(
    val labelDe: String,
    @DrawableRes val icon: Int,
    val colorHex: Long
) {
    OTTER("Otter", R.drawable.ic_motif_otter, 0xFFB45309),
    SEALS("Robben & Seehunde", R.drawable.ic_motif_seal, 0xFF0E7490),
    WADERS("Limikolen", R.drawable.ic_motif_wader, 0xFF7C3AED),
    DEER("Hirsche", R.drawable.ic_motif_deer, 0xFF92600E),
    WHALES("Wale & Delfine", R.drawable.ic_motif_whale, 0xFF2563EB),
    EAGLES("Adler", R.drawable.ic_motif_eagle, 0xFFDC2626),
    OTHER_ANIMALS("Sonstige Tiere", R.drawable.ic_motif_other, 0xFF16A34A),
    LANDSCAPE("Landschaft & Strände", R.drawable.ic_motif_landscape, 0xFF3E6B35),
    NIGHT_SKY("Nordlichter & Nachthimmel", R.drawable.ic_motif_night, 0xFF1E2A78),
    OTHER("Sonstiges", R.drawable.ic_motif_other, 0xFF6B7280);

    companion object {
        fun fromName(name: String?): Motif? = entries.firstOrNull { it.name.equals(name, ignoreCase = true) }
    }
}
