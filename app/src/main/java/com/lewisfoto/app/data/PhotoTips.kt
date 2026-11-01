package com.lewisfoto.app.data

/**
 * Technische/kreative Foto-Tipps aus Profiquellen (PetaPixel, Nature TTL, Wex Photo Video,
 * Warner-Tate, imageseen, Julian Baird, Alan Ranger, fixthephoto u.a.), nach Motiv gruppiert.
 * Werden in der Detailansicht eines Spots unter den bestehenden Tipps angezeigt.
 */
object PhotoTips {

    private val LANDSCAPE = listOf(
        "Seascape/Str\u00e4nde (Luskentyre, Seilebost, Garry, Mangersta, Stac a\u2019Phris): 10-Stop-ND + Polfilter, Blende f/8\u2013f/11, ISO 64\u2013100, Basis \u00d7 1000 (2^n-Regel) f\u00fcr seidig Wasser; 3 Testframes 8/20/60 s (Warner-Tate, Alan Ranger).",
        "Wasser-Effekt w\u00e4hlen: 0,3\u20131 s = Welle/Schaum, 2\u20135 s = Spuren, >30 s = milchig; >120 s = Wolken streifen (Landscape Photo Guy).",
        "Spiegelungen: P\u00fctzen/Watt nach Regen oder bei Ebbe nutzen \u2013 Butt of Lewis nach Regen spiegelt Leuchtturm + Himmel (Warner-Tate); niedriger Winkel + Polfilter drehen.",
        "ICM (Kamera w\u00e4hrend Belichtung bewegen, 1/8\u20131/2 s, ISO niedrig, ND+Polfilter): Sandmuster an Harris-Str\u00e4nden z. B. Scarasta (Warner-Tate, Julian Baird).",
        "Sturm/Wellen (Port of Ness, Tiumpan, Hafendamm Stornoway): Tele f\u00fcr komprimierte Wellen, 1/1000 s zum Einfrieren; nie R\u00fccken zum Meer, Tide checken (Warner-Tate, Nature TTL).",
        "Grauer Tag: Schwarz-Weiß erw\u00e4gen \u2013 nasse Steine + Polfilter = tiefe T\u00f6ne (Nature TTL).",
        "Luskentyre: D\u00fcnen als erh\u00f6hte Perspektive; 1/2 s+ f\u00fcr Gras-Bewegung; Drohnen-Panorama 9 Bilder gestitcht (Nature TTL, Julian Baird).",
        "Aussichtspunkt \u00fcber Luskentyre Bay (A859): Mittlere Tide + Wellengang = beste Brandung; bei Sturm ins Auto (Nature TTL)."
    )

    private val WILDLIFE = listOf(
        "Teleobjektiv ab 300 mm; Abstand wahren \u2013 bei Ann\u00e4herung zur\u00fcckziehen (Ethik).",
        "Goldene Stunde: weiches Seitenlicht, Rim-Light am Fell; Gegenlicht +1 EV, damit Brust nicht abs\u00e4uft (NatureWalks).",
        "Otter an K\u00fcste (Huisinis, Ardroil, Amhuinnsuidhe): D\u00e4mmerung, ISO hoch, >= 1/250 s oder Stativ, ruhig bewegen, nicht anstarren.",
        "Robben (Groay, Butt of Lewis, Hafen Stornoway): Tele von Klippe/Kai; bei Ebbe mehr Tiere an Land.",
        "Wale vom Land (Tiumpan Head, Butt of Lewis): 70-200 mm + Telekonverter, 1/1000 s, Serienbild f\u00fcr Sprung.",
        "Vogel (Limikolen an Salzwiesen, Adler): Eye-AF, Serienbild 8\u201310 fps, Verschluss >= 1/1000 s bei Flug.",
        "Seev\u00f6gel an Klippen/Stacks (Mangersta, Butt of Lewis): vom Kliff mit Tele, Windrichtung beachten."
    )

    private val SEASCAPE = listOf(
        "Wellen einfrieren: 1/1000 s+; weiche Brandung: 1/4\u20131 s; seidig: ND + mehrere Sekunden.",
        "Felsen im Vordergrund + f/11\u2013f/16 = Sch\u00e4rfentiefe; Polfilter f\u00fcr nasse Steine.",
        "Hafenmauer Stornoway bei Sturm: Tele f\u00fcr komprimierte Wellen; nie allein bei Sturm."
    )

    private val ARCH = listOf(
        "St Clement\u2019s Church Rodel (Innenraum): ISO 1600\u20133200, Blende offen, Stativ; bew\u00f6lktes Au\u00dfenlicht = weiches Fensterlicht.",
        "Callanish Steinkreis: Gegenlicht/Silhouetten bei tiefstehender Sonne; Mond-Ausrichtung f\u00fcr Astro/Aurora (Warner-Tate).",
        "Dun Carloway Broch: Gegenlicht f\u00fcr Umriss; Gegenlichtblende gegen Streulicht."
    )

    fun forMotif(motif: Motif): List<String> = when (motif) {
        Motif.LANDSCAPE, Motif.NIGHT_SKY -> LANDSCAPE
        Motif.OTTER, Motif.SEALS, Motif.WADERS, Motif.DEER, Motif.WHALES, Motif.EAGLES, Motif.OTHER_ANIMALS -> WILDLIFE + SEASCAPE
        Motif.OTHER -> LANDSCAPE.take(4)
    }
}
