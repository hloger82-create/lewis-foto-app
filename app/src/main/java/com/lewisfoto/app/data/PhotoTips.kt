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

    /** Ortspezifische Tipps, nur wenn explizit zum Spot passend (Quellen: Warner-Tate, Nature TTL, Julian Baird, Alan Ranger, HWDT). */
    private val SPOT_TIPS: Map<String, List<String>> = mapOf(
        "lestal_landscape" to listOf(
            "Sand-Rivulets am Strand als Leading Lines zu den Harris-Bergen (Warner-Tate).",
            "ICM mit 1/8\u20131/2 s f\u00fcr Sandmuster; D\u00fcnen als erh\u00f6hte Perspektive; 1/2 s+ f\u00fcr Gras-Bewegung (Nature TTL).",
            "Drohnen-Panorama 9 Bilder stitchen m\u00f6glich (Julian Baird)."
        ),
        "luskentyre_hill_viewpoint" to listOf(
            "Mittlere Tide + Wellengang = beste Brandung; bei Sturm schnell ins Auto (Nature TTL)."
        ),
        "butt_of_lewis_landscape" to listOf(
            "Nach Regen: P\u00fctzen spiegeln Leuchtturm + Himmel \u2013 niedriger Winkel + gro\u00dfe Blende f\u00fcr Sch\u00e4rfe (Warner-Tate).",
            "Klippen mit felsigem Vordergrund f\u00fcr Weitwinkel; ND-Set f\u00fcr Langzeit (Warner-Tate).",
            "Weg Richtung Eoropie f\u00fcr K\u00fcstenpanorama."
        ),
        "mangersta_sea_stacks" to listOf(
            "119 s Langzeit mit Polfilter + IRND + ND-Grad bei 52 mm (Julian Baird).",
            "Apr/Mai und Sept = bestes Licht; Hebriden-Sonnenuntergang bei Wolkenl\u00fccke (Warner-Tate).",
            "Klippen oben: Vorsicht bei Wind."
        ),
        "stac_a_phris" to listOf(
            "Bei Flut/Hochwasser Textur am Bogenfu\u00df; Sonnenuntergang im Sommer (Warner-Tate).",
            "ND-Set f\u00fcr Langzeit; von den Klippen als Vordergrund (Warner-Tate)."
        ),
        "traigh_mor_ness" to listOf(
            "Tele f\u00fcr komprimierte Wellen; 1/1000 s zum Einfrieren; nie R\u00fccken zum Meer bei Flut (Warner-Tate).",
            "Hafenmole als alternative Perspektive f\u00fcr Sturmwellen."
        ),
        "garry_sands_stacks" to listOf(
            "Stacks bei Ebbe aus dem Sand ragend \u2013 Weitwinkel + niedriger Standpunkt (Visit Outer Hebrides Photography Trail)."
        ),
        "callanish_landscape" to listOf(
            "Gegenlicht/Silhouetten bei tiefstehender Sonne; Mond-Ausrichtung f\u00fcr Astro/Aurora (Warner-Tate).",
            "D\u00e4mmerung/Dämmerung: Steine werfen lange Schatten \u00fcber das Moor."
        ),
        "st_clements_rodel" to listOf(
            "Bew\u00f6lktes Au\u00dfenlicht als weiches Fensterlicht; Stativ wegen langer Belichtung (innen)."
        ),
        "dun_carloway_broch" to listOf(
            "Sp\u00e4ter Nachmittag/Gegenlicht f\u00fcr Umriss; Gegenlichtblende gegen Streulicht."
        ),
        "huisinis_otter" to listOf(
            "Otter in der D\u00e4mmerung; >= 1/250 s oder Stativ, ruhig am Ufer entlang (kein direktes Anstarren)."
        ),
        "uig_sands_otter" to listOf(
            "Bei Ebbe auf den Felsen im Watt (Ardroil) achten; Tele f\u00fcr Ufertiere."
        ),
        "sound_of_harris_seals" to listOf(
            "Von der F\u00e4hre mit Tele (300 mm+) fotografieren; bei Ebbe sitzen Robben auf Groay."
        ),
        "tiumpan_head_whales" to listOf(
            "70-200 mm + Telekonverter; 1/1000 s + Serienbild f\u00fcr Wal-Sprung; ruhige See abwarten (HWDT)."
        ),
        "eagle_observatory" to listOf(
            "Vormittag bei Thermik; 300\u2013600 mm, Eye-AF + Serienbild; Tarnung/ruhige Bewegung."
        ),
        "bogha_glas_eagles" to listOf(
            "Bis zu 5\u201310 Adler \u00fcber dem Glen bei gutem Wetter; Weitwinkel f\u00fcr Glen-Panorama + Tele f\u00fcr Einzeladler."
        ),
        "aurora_callanish" to listOf(
            "10 s, ISO 1600, Blende offen, Weitwinkel; auch bei milchiger Wolke fotografieren \u2013 30 s Test zeigt Aurora; Kp >= 3 (auroratonight.space)."
        ),
        "aurora_butt_of_lewis" to listOf(
            "Leuchtturm + Aurora \u00fcber Nordmeer; P\u00fctzen spiegeln beides nach Regen (Warner-Tate)."
        )
    )

    fun forSpot(spot: FotoSpot): List<String> = forSpot(spot.id, spot.motif)

    fun forSpot(id: String, motif: Motif): List<String> = forMotif(motif) + (SPOT_TIPS[id] ?: emptyList())
}
