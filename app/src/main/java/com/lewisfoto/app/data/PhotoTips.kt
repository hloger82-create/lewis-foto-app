package com.lewisfoto.app.data

/**
 * Technische/kreative Foto-Tipps aus Profiquellen (PetaPixel, Nature TTL, Wex Photo Video,
 * Warner-Tate, imageseen, Julian Baird, Alan Ranger, fixthephoto u.a.), nach Motiv gruppiert.
 * Werden in der Detailansicht eines Spots unter den bestehenden Tipps angezeigt.
 */
object PhotoTips {

    private val LANDSCAPE = listOf(
        "Filter-Grundausstattung: Polfilter (sättigt Himmel/Türkis, entfernt Spiegelungen), ND (6- & 10-Stop) + ND-Grad 2-3 Stop (Himmel abdunkeln) \u2013 square/Holder-System erlaubt Stapeln (PetaPixel, Wex, imageseen).",
        "Langzeitbelichtung Seascape: 10-Stop-ND + Polfilter, Blende f/8\u2013f/11, ISO 64\u2013100, Basis-Belichtung \u00d7 1000 (2^n-Regel), Bulb + Rechner-App; 3 Testframes 8s/20s/60s (Alan Ranger, School of Photography).",
        "Wasser-Effekt wählen: 0,3\u20131 s = dynamische Welle/Schaum; 2\u20135 s = weiche Spuren; >30 s = milchig-glatt; >120 s = Wolken streifen (Anton Gorlin, Landscape Photo Guy).",
        "Stativ + Fernauslöser Pflicht bei Langzeit; bei Sturm: Stativ evtl. nicht standfest \u2013 niedriger Standpunkt, Gewicht an Haken (Nature TTL).",
        "Belichtung: RAW + Bracketing 0/\u2212/+ (2\u20133 EV) statt GND \u2013 bei DSLR Sucher abdecken (Alan Ranger); D-Range/Optimizer der Kamera für Highlights (Wex).",
        "Gegenlicht/Sonnenstern: Blende f/16\u2013f/22 erzeugt Sunstar; Streulicht vermeiden: Gegenlichtblende, Linse sauber (Nature TTL).",
        "Spiegelungen: Pfützen/Watt nach Regen oder Ebbe nutzen, niedriger Winkel + Polfilter drehen, bis Spiegelung sitzt (Warner-Tate).",
        "ICM (Intentional Camera Movement): 1/8\u20131/2 s, ISO niedrig, ND + Polfilter, Kamera während Belichtung bewegen \u2013 z. B. Sandmuster an Stränden (Warner-Tate, Wikipedia ICM).",
        "Graue/wolkige Tage: Schwarz-Weiß erwägen (Nature TTL); nasse Steine + Polfilter = tiefe Farben.",
        "Tide immer checken: Ebbe legt Stacks/Watt frei, Flut bringt Wellen-Dynamik; nie Rücken zum Meer (PhotographyGoals, Warner-Tate).",
        "Objektive: 14\u201330 mm UWW + 24\u201370/105 mm Standard + Tele fürs Wildlife (Nature TTL); Drohne wo erlaubt (Nature TTL).",
        "Drohnen-Panorama: 9 Bilder stitchen (Julian Baird, Luskentyre)."
    )

    private val WILDLIFE = listOf(
        "Teleobjektiv ab 300 mm; Abstand wahren \u2013 bei Annäherung lieber zurückziehen (Ethik).",
        "Goldene Stunde: weiches Seitenlicht, Rim-Light am Fell; Gegenlicht +1 EV Belichtungskorrektur, damit Brust nicht absäuft (NatureWalks).",
        "Blende offen (f/2.8\u2013f/4) für Bokeh-Highlights (rund bei runden Blenden), Auge scharf stellen, AF-C/AI Servo + Serienbild (Nature TTL).",
        "Dämmerung (Otter): ISO hoch, Verschlusszeit >= 1/250 s oder Stativ; ruhige Bewegung, nicht direkt anstarren.",
        "Vogel: Verschluss >= 1/1000 s für Flug,Eye-AF, Serienbild 8-10 fps; bei Gegenlicht Silhouette belichten.",
        "Seehund/Robbe: Tele von Klippe/Hafenmauer, leise Annäherung; bei Ebbe mehr Tiere an Land.",
        "Wale: vom Land (Tiumpan Head) mit 70-200 mm + Telekonverter; Boot: 1/1000 s, Serienbild für Sprung."
    )

    private val SEASCAPE = listOf(
        "Wellen einfrieren: 1/1000 s+; weiche Brandung: 1/4\u20131 s; seidig: ND + mehrere Sekunden.",
        "Felsen im Vordergrund + f/11\u2013f/16 = Schärfentiefe; Polfilter für nasse Steine.",
        "Hafenmauer (Stornoway) bei Sturm: Tele für komprimierte Wellen, nie allein bei Sturm."
    )

    private val ARCH = listOf(
        "Innenräume (St Clement's Rodel): ISO 1600\u20133200, Blende offen, Stativ; bewölktes Licht von außen = weiches Fensterlicht.",
        "Steinkreis (Callanish): Gegenlicht/Silhouetten bei tiefstehender Sonne; Mond ausrichten für astro.",
        "Broch (Dun Carloway): Gegenlicht für Umriss, Gegenlichtblende gegen Streulicht."
    )

    fun forMotif(motif: Motif): List<String> = when (motif) {
        Motif.LANDSCAPE, Motif.NIGHT_SKY -> LANDSCAPE
        Motif.OTTER, Motif.SEALS, Motif.WADERS, Motif.DEER, Motif.WHALES, Motif.EAGLES, Motif.OTHER_ANIMALS -> WILDLIFE + SEASCAPE
        Motif.OTHER -> LANDSCAPE.take(4)
    }
}
