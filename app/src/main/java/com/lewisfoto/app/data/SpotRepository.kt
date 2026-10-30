package com.lewisfoto.app.data

import com.lewisfoto.app.data.Season.AUTUMN
import com.lewisfoto.app.data.Season.SPRING
import com.lewisfoto.app.data.Season.SUMMER
import com.lewisfoto.app.data.Season.WINTER

object SpotRepository {

    val dataSources: List<Pair<String, String>> = listOf(
        "Hebridean Whale & Dolphin Trust (HWDT) – Sichtungsdaten & Tiumpan Head" to "https://www.hwdt.org",
        "RSPB – Vogelschutz & Reserves Äußere Hebriden" to "https://www.rspb.org.uk",
        "SNH / NatureScot – Schutzgebiete & Brutvorkommen" to "https://www.nature.scot",
        "Visit Outer Hebrides – Spot-Guides (Eagles, Deer, Hare, Seals)" to "https://www.visitouterhebrides.co.uk",
        "VisitScotland – Lewis & Harris Highlights" to "https://www.visitscotland.com",
        "Scottish Wildlife Trust – Reserves Outer Hebrides" to "https://scottishwildlifetrust.org.uk",
        "WalkHighlands – North Harris Eagle Observatory & Wanderungen" to "https://www.walkhighlands.co.uk",
        "Grinneabhat (Bragar) – Birdwatching West-Lewis" to "https://www.grinneabhat.com",
        "Western Isles Wildlife – Vogelarten & Status" to "https://western-isles-wildlife.co.uk",
        "OpenStreetMap – Kartenmaterial & Parkplätze (ODbL)" to "https://www.openstreetmap.org",
        "Wikidata / Wikipedia / British Listed Buildings – verifizierte Koordinaten" to "https://www.wikidata.org"
    )

    private fun r(season: Season, frequency: Int, probability: Int) = Rating(season, frequency, probability)
    private fun p(name: String, note: String) = ParkingInfo(name, note)
    private fun a(from: String, desc: String) = ArrivalInfo(from, desc)
    private fun t(low: Boolean, note: String) = TideInfo(low, note)

    val allSpots: List<FotoSpot> = listOf(
        FotoSpot(
            id = "huisinis_otter",
            name = "Huisinis & Loch Resort (West-Harris)",
            motif = Motif.OTTER,
            lat = 58.0300, lon = -7.1200,
            ratings = listOf(r(SPRING, 7, 75), r(SUMMER, 8, 85), r(AUTUMN, 7, 75), r(WINTER, 6, 65)),
            bestTimeOfDay = "Dämmerung (früh morgens / abends)",
            tide = t(low = true, note = "Bei Ebbe/Flut am aktivsten; Küstenwanderung am Wasser"),
            tips = listOf(
                "Klassischer Otter-Spot an der weißen Sandbucht von Huisinis.",
                "Ruhig am Ufer entlanggehen, Fernglas bereithalten.",
                "Alternative: Geführte Otter-Bootstour ab Miavaig (Seatrek)."
            ),
            safety = listOf("Einschusslöcher/Schlamm am Ufer: rutschig – festes Schuhwerk."),
            parking = p("Parkplatz Huisinis Beach", "Kleiner Parkplatz an der B887, gebührenfrei"),
            arrival = a("ca. 45 Min. / 40 km ab Tarbert", "B887 Single-Track-Road ab Tarbert, Passing Places beachten"),
            sources = listOf("Visit Outer Hebrides", "Seatrek / HWDT")
        ),
        FotoSpot(
            id = "uig_sands_otter",
            name = "Uig Sands / Ardroil (Loch Roag, West-Lewis)",
            motif = Motif.OTTER,
            lat = 58.1800, lon = -7.0000,
            what3words = "///await.denser.bagpipes",
            ratings = listOf(r(SPRING, 7, 75), r(SUMMER, 8, 80), r(AUTUMN, 7, 70), r(WINTER, 6, 65)),
            bestTimeOfDay = "Bei Ebbe, morgens oder abends",
            tide = t(low = true, note = "Otter jagen bei Ebbe auf dem Riff vor Ardroil"),
            tips = listOf(
                "Otter sitzen oft auf den Felsen im Watt (Ardroil).",
                "Bootstouren ab Miavaig fahren gezielt Otter-Spots an."
            ),
            safety = listOf("Ebbe beachten: Rückkehrweg vor Flut sichern, Watt kann schnell zufluten."),
            parking = p("Parkplatz Uig Sands (Ardroil)", "Parkplatz am Ende der Single-Track-Road, gebührenfrei; what3words ///await.denser.bagpipes"),
            arrival = a("ca. 55 Min. / 45 km ab Stornoway", "A866 Richtung Barvas, dann Single-Track nach Uig"),
            sources = listOf("Tripadvisor-Reviews Uig Sands", "Seatrek", "WildBounds", "scotlandlessexplored.com (what3words)")
        ),
        FotoSpot(
            id = "pairc_seaforth_otter",
            name = "Pairc / Ufer des Loch Seaforth (Süd-Lewis)",
            motif = Motif.OTTER,
            lat = 57.9500, lon = -6.5200,
            ratings = listOf(r(SPRING, 7, 70), r(SUMMER, 7, 75), r(AUTUMN, 8, 80), r(WINTER, 7, 70)),
            bestTimeOfDay = "Dämmerung",
            tide = null,
            tips = listOf(
                "Starkburg von Seeadlern UND Fischottern (Bird-of-Prey-Trail).",
                "Nebenstraßen ab A859 Richtung Ardveg/Loch Seaforth nutzen."
            ),
            safety = listOf("Single-Track-Roads im Pairc: Ausweichstellen nutzen, langsam fahren."),
            parking = p("Parkbucht an der Nebenstraße zum Loch Seaforth", "Unbefestigte Ausweichbucht, gebührenfrei"),
            arrival = a("ca. 35 Min. / 30 km ab Stornoway", "A866 Richtung Tarbert, Abzweig auf Nebenstraße Richtung Loch Seaforth"),
            sources = listOf("Visit Outer Hebrides – Bird of Prey Trail")
        ),
        FotoSpot(
            id = "tolsta_garry_otter",
            name = "Tolsta Head / Garry Beach (Nordost-Lewis)",
            motif = Motif.OTTER,
            lat = 58.3700, lon = -6.2200,
            ratings = listOf(r(SPRING, 6, 65), r(SUMMER, 7, 70), r(AUTUMN, 6, 65), r(WINTER, 5, 55)),
            bestTimeOfDay = "Bei Ebbe entlang der Kelp-Riffe",
            tide = t(low = true, note = "Kelp-Riffe bei Ebbe: Otter jagen dort häufig"),
            tips = listOf(
                "Küstenwanderung ab Garry Beach Richtung Tolsta Head.",
                "Teleobjektiv für Ufertiere."
            ),
            safety = listOf("Klippen und nasse Felsen: Trittsicherheit beachten."),
            parking = p("Parkplatz Garry Beach", "Parkplatz am Strand, gebührenfrei"),
            arrival = a("ca. 20 Min. / 15 km ab Stornoway", "A857 Richtung Tolsta/Port of Ness"),
            sources = listOf("Visit Outer Hebrides", "WildBounds")
        ),
        FotoSpot(
            id = "sound_of_harris_seals",
            name = "Sound of Harris – Kegelrobben-Kolonie (Fähre Leverburgh–Berneray)",
            motif = Motif.SEALS,
            lat = 57.7678, lon = -7.0170,
            ratings = listOf(r(SPRING, 7, 80), r(SUMMER, 8, 85), r(AUTUMN, 10, 95), r(WINTER, 9, 90)),
            bestTimeOfDay = "Bei der Fährüberfahrt, gesamte Strecke beobachten",
            tide = t(low = true, note = "Bei Ebbe sitzen Robben auf den Felsen (Groay)"),
            tips = listOf(
                "Okt–Dez: Welpen der Kegelrobben – spektakulär, aber Abstand!",
                "Von der Fähre aus mit Teleobjektiv fotografieren."
            ),
            safety = listOf("Nie an Robben-Welpen annähern – Muttertiere verteidigen sie aggressiv."),
            parking = p("Parkplatz Fährhafen Leverburgh", "Großer Hafenparkplatz, gebührenfrei (Fähre gebührenpflichtig)"),
            arrival = a("ca. 40 Min. / 35 km ab Tarbert", "A859 Richtung Leverburgh, dann CalMac-Fähre nach Berneray"),
            sources = listOf("Visit Outer Hebrides – Grey Seal Sound of Harris", "HWDT", "ports.marinelink.com (Koordinaten Leverburgh)")
        ),
        FotoSpot(
            id = "luskentyre_seals",
            name = "Luskentyre / Horgabost (Harris)",
            motif = Motif.SEALS,
            lat = 57.9320, lon = -7.0590,
            ratings = listOf(r(SPRING, 8, 85), r(SUMMER, 9, 90), r(AUTUMN, 8, 85), r(WINTER, 7, 75)),
            bestTimeOfDay = "Bei Ebbe am Strand und im Sound of Taransay",
            tide = t(low = true, note = "Bei Ebbe mehr Tiere am Strand sichtbar"),
            tips = listOf(
                "Kegel- und Seehunde schwimmen oft nahe am Strand – Kayaker berichten regelmäßig Sichtungen.",
                "Türkisblaues Wasser: Polfilter für Unterwasser-/Strandaufnahmen."
            ),
            safety = listOf("Hunde an der Leine – Robben können beunruhigt werden."),
            parking = p("Parkplatz Luskentyre Beach", "Großer Schotterparkplatz am Ende der Single-Track-Road, gebührenfrei"),
            arrival = a("ca. 30 Min. / 25 km ab Tarbert", "A859 Richtung Leverburgh, Abzweig nach Luskentyre/Horgabost"),
            sources = listOf("VisitScotland", "Bluefin SUP – Seal Spotting UK")
        ),
        FotoSpot(
            id = "stornoway_harbour_seals",
            name = "Hafen Stornoway / Arnish Point",
            motif = Motif.SEALS,
            lat = 58.2094, lon = -6.3849,
            ratings = listOf(r(SPRING, 8, 85), r(SUMMER, 9, 90), r(AUTUMN, 9, 90), r(WINTER, 8, 85)),
            bestTimeOfDay = "Bei Ebbe am Kai / Arnish Point",
            tide = t(low = true, note = "Seehunde ruhen bei Ebbe auf den Steinen am Hafen"),
            tips = listOf(
                "Standort-treue Seehunde am Hafen, oft von Fischern gesehen.",
                "Kurzer Spaziergang vom Zentrum Stornoway."
            ),
            safety = listOf("Hafenbereich: nicht auf Kais klettern."),
            parking = p("Parkplatz Hafen Stornoway", "Öffentlicher Hafenparkplatz, gebührenpflichtig tagsüber"),
            arrival = a("im Zentrum Stornoway", "Fußläufig oder kurze Fahrt ab Hafen"),
            sources = listOf("Scotsman – beste Seal-Spots Schottlands", "Virtual Hebrides", "latlong.net (Koordinaten Stornoway 58.209435, -6.384869)")
        ),
        FotoSpot(
            id = "aird_mhuile_seals",
            name = "Aird a' Mhuile / Bragar (Nordwest-Lewis)",
            motif = Motif.SEALS,
            lat = 58.4750, lon = -6.6800,
            ratings = listOf(r(SPRING, 8, 85), r(SUMMER, 9, 90), r(AUTUMN, 8, 85), r(WINTER, 7, 80)),
            bestTimeOfDay = "2 Stunden vor/nach Niedrigwasser",
            tide = t(low = true, note = "Robben auf den Felsen bei Ebbe am Point"),
            tips = listOf(
                "Dokumentierter Hotspot für Robben am Aird a' Mhuile.",
                "Gute Kombination mit Loch Ordais (Limikolen) in der Nähe."
            ),
            safety = listOf("Felsiger Point: Trittsicherheit, nicht bei Sturm hinausgehen."),
            parking = p("Parkplatz an der Straße nach Aird a' Mhuile", "Kleine Parkbucht, gebührenfrei"),
            arrival = a("ca. 40 Min. / 35 km ab Stornoway", "A866 Richtung Barvas, Abzweig Richtung Bragar/Aird a' Mhuile"),
            sources = listOf("Virtual Hebrides", "Visit Outer Hebrides")
        ),
        FotoSpot(
            id = "northton_waders",
            name = "Northton Machair & Ästuar (Taobh Tuath, Harris)",
            motif = Motif.WADERS,
            lat = 57.8100, lon = -7.0500,
            ratings = listOf(r(SPRING, 9, 90), r(SUMMER, 6, 70), r(AUTUMN, 10, 95), r(WINTER, 10, 95)),
            bestTimeOfDay = "Bei Ebbe und um Niedrigwasser",
            tide = t(low = true, note = "Wattflächen bei Ebbe: beste Limikolen-Beobachtung"),
            tips = listOf(
                "Feuchte Machair-Flächen und Strand: Steinwälzer, Ringel-/Sandregenpfeifer, Lummertölpel, Knutts.",
                "Winter: zusätzlich Alpenstrandläufer und Sanderlinge."
            ),
            safety = listOf("Machair nicht mit dem Auto befahren – empfindliches Grasland."),
            parking = p("Parkplatz Northton Beach", "Parkplatz am Strand, gebührenfrei"),
            arrival = a("ca. 35 Min. / 30 km ab Tarbert", "A859 Richtung Leverburgh, Abzweig nach Northton"),
            sources = listOf("Western Isles Wildlife", "Visit Outer Hebrides – Birdwatching")
        ),
        FotoSpot(
            id = "tong_gress_waders",
            name = "Tong Saltings & Broad Bay (bei Stornoway)",
            motif = Motif.WADERS,
            lat = 58.2800, lon = -6.2800,
            ratings = listOf(r(SPRING, 9, 90), r(SUMMER, 7, 80), r(AUTUMN, 10, 95), r(WINTER, 10, 95)),
            bestTimeOfDay = "Bei Hochwasser (Flut) am Salzwiesen-Rand",
            tide = t(low = false, note = "Bei Flut ziehen sich Watvögel auf die Salzwiesen zurück – Hochwasserrand ideal"),
            tips = listOf(
                "Salzwiesen bei Tong/Gress: Austernfischer, Rotschenkel, Brachvögel, evtl. Zwergschnepfen.",
                "Bis zu 800 Trauerenten in der Broad Bay im Winter – Spektiv mitnehmen."
            ),
            safety = listOf("Salzwiesen nicht betreten – Brut- und Rastgebiet."),
            parking = p("Parkplatz am Gress Beach / Tong", "Parkplatz an der Küstenstraße, gebührenfrei"),
            arrival = a("ca. 15 Min. / 12 km ab Stornoway", "A857 Richtung Tong/Gress, Küstenstraße entlang"),
            sources = listOf("Grinneabhat", "Western Isles Wildlife")
        ),
        FotoSpot(
            id = "loch_ordais_waders",
            name = "Loch Ordais (Bragar, West-Lewis)",
            motif = Motif.WADERS,
            lat = 58.3400, lon = -6.6700,
            ratings = listOf(r(SPRING, 9, 90), r(SUMMER, 7, 80), r(AUTUMN, 10, 95), r(WINTER, 9, 90)),
            bestTimeOfDay = "Bei Ebbe am Seeufer",
            tide = t(low = true, note = "Ebbe legt Schlammflächen am Loch Ordais frei"),
            tips = listOf(
                "Dokumentiert: Kiebitz, Austernfischer, Sandregenpfeifer, Sanderling, Steinwälzer, Rotschenkel, Dunkler Wasserläufer.",
                "Gemeinde-Projekt Grinneabhat pflegt das Gebiet – Infotafeln vor Ort."
            ),
            safety = listOf("Uferweg kann schlammig sein."),
            parking = p("Parkplatz Loch Ordais (Bragar)", "Parkplatz am See, gebührenfrei"),
            arrival = a("ca. 40 Min. / 35 km ab Stornoway", "A866 Richtung Barvas/Bragar"),
            sources = listOf("Grinneabhat – Birdwatching", "Western Isles Wildlife")
        ),
        FotoSpot(
            id = "clisham_deer",
            name = "An Cliseam (Clisham) & A859-Pass (Harris)",
            motif = Motif.DEER,
            lat = 57.9025, lon = -6.6917,
            ratings = listOf(r(SPRING, 7, 75), r(SUMMER, 6, 70), r(AUTUMN, 9, 90), r(WINTER, 8, 80)),
            bestTimeOfDay = "Früher Morgen / Abenddämmerung",
            tide = null,
            tips = listOf(
                "Hirsche grasen an den Hängen direkt oberhalb der A859 – vom Auto aus beobachtbar.",
                "Brunft (Sept–Okt): Hirsche röhren – dann besonders unberechenbar."
            ),
            safety = listOf("Brunftzeit: niemals zwischen Hirsch und Hindernis stellen, Abstand > 50 m."),
            parking = p("Parkbucht am Clisham-Pass (A859)", "Ausweichbucht an der A859, gebührenfrei"),
            arrival = a("ca. 20 Min. / 15 km ab Tarbert", "A859 Richtung Leverburgh, Passhöhe Clisham"),
            sources = listOf("Visit Outer Hebrides – Clisham Horseshoe", "WalkHighlands", "Wild Harris")
        ),
        FotoSpot(
            id = "glen_vigidale_deer_eagles",
            name = "Glen Vigidale / Bogha Glas (Harris)",
            motif = Motif.DEER,
            lat = 57.9500, lon = -6.6000,
            ratings = listOf(r(SPRING, 7, 75), r(SUMMER, 7, 75), r(AUTUMN, 9, 90), r(WINTER, 8, 85)),
            bestTimeOfDay = "Morgens und spätnachmittags",
            tide = null,
            tips = listOf(
                "Hirsche auf den Bergrücken des Glen Vigidale, oft in Gruppen.",
                "Gleichzeitig Adler-Revier (siehe Adler-Spots) – Kombination möglich."
            ),
            safety = listOf("Abseits der Straße: Moorboden weich, Trittsicherheit."),
            parking = p("Parkplatz Bogha Glas (A859)", "Parkplatz an der A859, gebührenfrei"),
            arrival = a("ca. 12 Min. / 10 km ab Tarbert", "A859 Richtung Leverburgh, Parkplatz Bogha Glas"),
            sources = listOf("Visit Outer Hebrides", "WalkHighlands", "Wild Harris")
        ),
        FotoSpot(
            id = "uig_hills_deer",
            name = "Uig Hills (West-Lewis)",
            motif = Motif.DEER,
            lat = 58.1900, lon = -6.9500,
            ratings = listOf(r(SPRING, 6, 70), r(SUMMER, 6, 65), r(AUTUMN, 8, 85), r(WINTER, 7, 75)),
            bestTimeOfDay = "Dämmerung",
            tide = null,
            tips = listOf(
                "Dokumentiert: Rothirsche und Brutpaare des Steinadlers in den Uig Hills.",
                "Wanderung ab Uig Sands in die Hügel."
            ),
            safety = listOf("Moorland: Wetter kann schnell umschlagen – Karte/Kompass mitnehmen."),
            parking = p("Parkplatz Uig Sands", "Gemeinsam mit Otter-Spot Ardroil"),
            arrival = a("ca. 55 Min. / 45 km ab Stornoway", "Wie Uig Sands (Ardroil)"),
            sources = listOf("Komoot – South Lewis Naturdenkmäler")
        ),
        FotoSpot(
            id = "pairc_deer_forest",
            name = "Pairc Deer Forest / South Lochs (Süd-Lewis)",
            motif = Motif.DEER,
            lat = 58.0500, lon = -6.4500,
            ratings = listOf(r(SPRING, 7, 75), r(SUMMER, 6, 70), r(AUTUMN, 9, 90), r(WINTER, 8, 85)),
            bestTimeOfDay = "Früher Morgen / Abend",
            tide = null,
            tips = listOf(
                "Klassischer Deer Forest im Süden von Lewis – Chancen an den Nebenstraßen des Pairc.",
                "Langsam fahren: Hirsche überqueren oft die Single-Track-Roads."
            ),
            safety = listOf("Single-Track-Roads: Ausweichstellen, nie auf der Fahrbahn parken."),
            parking = p("Ausweichbucht an Pairc-Nebenstraße", "Unbefestigt, gebührenfrei"),
            arrival = a("ca. 30 Min. / 25 km ab Stornoway", "A866 Richtung Tarbert, dann Nebenstraßen ins Pairc-Gebiet"),
            sources = listOf("Visit Outer Hebrides", "VisitScotland")
        ),
        FotoSpot(
            id = "tiumpan_head_whales",
            name = "Tiumpan Head (östlich von Stornoway) – beste Landbeobachtung",
            motif = Motif.WHALES,
            lat = 58.2610, lon = -6.1388,
            ratings = listOf(r(SPRING, 7, 75), r(SUMMER, 9, 90), r(AUTUMN, 7, 70), r(WINTER, 5, 55)),
            bestTimeOfDay = "Bei ruhiger See, ganztägig beobachten",
            tide = null,
            tips = listOf(
                "Bestes landbasiertes Whale-Watching des UK: Schweinswale, Große Tümmler, Zwergwale, gelegentlich Buckelwale.",
                "Im Winter seltener: Schweinswale und Delfine bleiben realistisch, Minke unwahrscheinlich.",
                "Spektiv/Teleobjektiv mitnehmen, Leuchtturm-Parkplatz nutzen."
            ),
            safety = listOf("Klippenrand: Abstand halten, nie allein bei Sturm hinausgehen."),
            parking = p("Parkplatz Tiumpan Head Lighthouse", "Parkplatz am Leuchtturm, gebührenfrei"),
            arrival = a("ca. 15 Min. / 12 km ab Stornoway", "A857 Richtung Point, Küstenstraße bis Tiumpan Head"),
            sources = listOf("HWDT – Tales from the Trail Tiumpanhead", "Visit Outer Hebrides", "BirdForum", "britishlistedbuildings.co.uk (58.261, -6.1388)")
        ),
        FotoSpot(
            id = "butt_of_lewis_whales",
            name = "Butt of Lewis – Leuchtturm",
            motif = Motif.WHALES,
            lat = 58.5149, lon = -6.2628,
            ratings = listOf(r(SPRING, 7, 75), r(SUMMER, 8, 85), r(AUTUMN, 6, 70), r(WINTER, 4, 50)),
            bestTimeOfDay = "Bei ruhiger See vom Leuchtturm aus",
            tide = null,
            tips = listOf(
                "Schweinswale und Delfine vom nördlichsten Punkt; Orcas eher Frühjahr/Sommer.",
                "Kombinierbar mit Landschaftsfotos (Leuchtturm + Klippen)."
            ),
            safety = listOf("Starker Westwind häufig – warme Kleidung, Klippenrand meiden."),
            parking = p("Parkplatz Butt of Lewis", "Großer Schotterparkplatz am Leuchtturm, gebührenfrei"),
            arrival = a("ca. 35 Min. / 30 km ab Stornoway", "A857 bis Tolsta, weiter Richtung Ness/Butt of Lewis"),
            sources = listOf("HWDT", "Islandeering – beste Wal-Spots UK", "Wikidata Q13630879 (58.5149, -6.2628)")
        ),
        FotoSpot(
            id = "tolsta_head_whales",
            name = "Tolsta Head (Nordost-Lewis)",
            motif = Motif.WHALES,
            lat = 58.3700, lon = -6.2000,
            ratings = listOf(r(SPRING, 6, 70), r(SUMMER, 8, 85), r(AUTUMN, 6, 70), r(WINTER, 4, 55)),
            bestTimeOfDay = "Bei ruhiger See",
            tide = null,
            tips = listOf("Seawatching vom Headland: Schweinswale, Delfine offshore."),
            safety = listOf("Felsige Küste: Trittsicherheit."),
            parking = p("Parkplatz Tolsta/Garry Beach", "Gemeinsam mit Garry Beach"),
            arrival = a("ca. 20 Min. / 15 km ab Stornoway", "A857 Richtung Tolsta"),
            sources = listOf("HWDT", "Visit Outer Hebrides")
        ),
        FotoSpot(
            id = "gallan_head_whales",
            name = "Gallan Head / Aird Uig (Westspitze Lewis)",
            motif = Motif.WHALES,
            lat = 58.2401, lon = -7.0273,
            ratings = listOf(r(SPRING, 6, 70), r(SUMMER, 8, 85), r(AUTUMN, 6, 70), r(WINTER, 4, 55)),
            bestTimeOfDay = "Bei ruhiger See, nach Westen hinaus schauen",
            tide = null,
            tips = listOf("Westliches Headland: Schweinswale und Delfine im Atlantik vor der Küste."),
            safety = listOf("Exponierte Westspitze: Sturm meiden."),
            parking = p("Parkplatz Gallan Head", "Parkbucht am Ende der Straße, gebührenfrei"),
            arrival = a("ca. 60 Min. / 50 km ab Stornoway", "A866 Richtung Barvas, Single-Track nach Uig/Gallan Head"),
            sources = listOf("HWDT", "WalkHighlands", "birdguides.com (58.24008, -7.02731)")
        ),
        FotoSpot(
            id = "eagle_observatory",
            name = "North Harris Eagle Observatory, Glen Mhiabhaig",
            motif = Motif.EAGLES,
            lat = 57.9900, lon = -7.0200,
            ratings = listOf(r(SPRING, 8, 85), r(SUMMER, 9, 90), r(AUTUMN, 8, 80), r(WINTER, 7, 70)),
            bestTimeOfDay = "Vormittag bei gutem Wetter und Thermik",
            tide = null,
            tips = listOf(
                "Zweckgebundene Beobachtungsstelle im Steinadler-Revier – auf Harris brüten ca. 20 Paare.",
                "Ca. 20–40 Min. Fußweg vom Parkplatz durch das Glen.",
                "Auch Seeadler kommen vor."
            ),
            safety = listOf("Weg durch enges Tal: bei Starkregen Wasserstand im Bach beachten."),
            parking = p("Parkplatz Eagle Observatory (Glen Mhiabhaig)", "Parkplatz an der B887, OS-Grid NB 101 063, gebührenfrei"),
            arrival = a("ca. 40 Min. / 35 km ab Tarbert", "B887 ab Tarbert Richtung Huisinis, Abzweig Glen Mhiabhaig"),
            sources = listOf("WalkHighlands – Harris Eagle Observatory", "myhighlands.de", "visitouterhebrides.co.uk (NB 101 063)")
        ),
        FotoSpot(
            id = "bogha_glas_eagles",
            name = "Bogha Glas / Glen Vigidale (Harris) – Adler-Revier",
            motif = Motif.EAGLES,
            lat = 57.9500, lon = -6.6000,
            ratings = listOf(r(SPRING, 9, 90), r(SUMMER, 10, 95), r(AUTUMN, 9, 90), r(WINTER, 8, 80)),
            bestTimeOfDay = "Vormittag bis früher Nachmittag",
            tide = null,
            tips = listOf(
                "An manchen Tagen bis zu 5–10 Adler (Stein- und Seeadler) über dem Glen.",
                "Vom Parkplatz Bogha Glas (A859) aus beobachtbar."
            ),
            safety = listOf("Nicht ins Glen abseits der Wege laufen – Adlerhorste sind geschützt."),
            parking = p("Parkplatz Bogha Glas (A859)", "Parkplatz direkt an der A859, gebührenfrei"),
            arrival = a("ca. 12 Min. / 10 km ab Tarbert", "A859 ab Tarbert Richtung Leverburgh"),
            sources = listOf("Visit Outer Hebrides – Golden Eagles & Bird of Prey Trail")
        ),
        FotoSpot(
            id = "uig_hills_eagles",
            name = "Uig Hills / Mangersta – Klippenadler",
            motif = Motif.EAGLES,
            lat = 58.1400, lon = -7.0500,
            ratings = listOf(r(SPRING, 8, 85), r(SUMMER, 9, 90), r(AUTUMN, 7, 75), r(WINTER, 6, 65)),
            bestTimeOfDay = "Vormittag",
            tide = null,
            tips = listOf("Steinadler brüten in den Uig Hills, Seeadler über den Meeresklippen."),
            safety = listOf("Moorwanderung: Wetter kann umschlagen – Kompass/Karte dabei."),
            parking = p("Parkplatz Mangersta Bay", "Parkbucht am Strand, gebührenfrei"),
            arrival = a("ca. 60 Min. / 50 km ab Stornoway", "A866 Richtung Barvas, Single-Track nach Uig/Mangersta"),
            sources = listOf("Komoot", "Western Isles Wildlife")
        ),
        FotoSpot(
            id = "loch_seaforth_eagles",
            name = "Ufer des Loch Seaforth (Süd-Lewis)",
            motif = Motif.EAGLES,
            lat = 57.9500, lon = -6.5200,
            ratings = listOf(r(SPRING, 8, 85), r(SUMMER, 9, 90), r(AUTUMN, 8, 80), r(WINTER, 7, 70)),
            bestTimeOfDay = "Morgens und nachmittags",
            tide = null,
            tips = listOf("Seeadler-Starkburg entlang des Loch Seaforth (Bird-of-Prey-Trail)."),
            safety = listOf("Nebenstraße eng – Ausweichstellen nutzen."),
            parking = p("Parkbucht Loch Seaforth", "Unbefestigte Bucht, gebührenfrei"),
            arrival = a("ca. 35 Min. / 30 km ab Stornoway", "A866 Richtung Tarbert, Abzweig zum Loch Seaforth"),
            sources = listOf("Visit Outer Hebrides – Bird of Prey Trail")
        ),
        FotoSpot(
            id = "loch_erisort_eagles",
            name = "Loch Erisort / Keose (Südost-Lewis) – Seeadler",
            motif = Motif.EAGLES,
            lat = 58.0800, lon = -6.3200,
            ratings = listOf(r(SPRING, 7, 80), r(SUMMER, 8, 85), r(AUTUMN, 7, 75), r(WINTER, 6, 65)),
            bestTimeOfDay = "Bei Ebbe am Seeufer",
            tide = t(low = true, note = "Bei Ebbe jagen Seeadler entlang des Ufers"),
            tips = listOf("Seeadler besuchen regelmäßig den langen Meeresarm Loch Erisort."),
            safety = listOf("Ebbe/Flut beachten bei Uferwanderung."),
            parking = p("Parkplatz am Kopf des Loch Erisort (Balallan)", "Kleine Parkbucht, gebührenfrei"),
            arrival = a("ca. 25 Min. / 20 km ab Stornoway", "A866 Richtung Tarbert, Abzweig Richtung Loch Erisort"),
            sources = listOf("Visit Outer Hebrides", "Western Isles Wildlife")
        ),
        FotoSpot(
            id = "clisham_hare",
            name = "An Cliseam / Tiorga Mòr – Berghasen",
            motif = Motif.OTHER_ANIMALS,
            lat = 57.9025, lon = -6.6917,
            ratings = listOf(r(SPRING, 6, 65), r(SUMMER, 6, 65), r(AUTUMN, 8, 85), r(WINTER, 9, 90)),
            bestTimeOfDay = "Nov–März bei Schneedecke (weißes Winterfell)",
            tide = null,
            tips = listOf(
                "Berghasen (Lepus timidus scoticus) auf Harris verbreitet – im Winter weiß.",
                "Vom Parkplatz am Clisham-Pass die Hänge absuchen."
            ),
            safety = listOf("Berggelände: Trittsicherheit, Wetter beachten."),
            parking = p("Parkbucht am Clisham-Pass (A859)", "Ausweichbucht, gebührenfrei"),
            arrival = a("ca. 20 Min. / 15 km ab Tarbert", "A859 Richtung Leverburgh"),
            sources = listOf("Visit Outer Hebrides – Mountain Hare An Cliseam/Toddun")
        ),
        FotoSpot(
            id = "broad_bay_divers",
            name = "Broad Bay – Trauerenten & Pfeifschwäne",
            motif = Motif.OTHER_ANIMALS,
            lat = 58.2500, lon = -6.3000,
            ratings = listOf(r(SPRING, 7, 75), r(SUMMER, 4, 50), r(AUTUMN, 8, 85), r(WINTER, 10, 95)),
            bestTimeOfDay = "Winter: ganztägig auf dem Wasser",
            tide = null,
            tips = listOf(
                "Im Spätwinter bis zu 800 Trauerenten (Great Northern Divers) in der Broad Bay.",
                "Pfeifschwäne auf den Salzwiesen bei Tong."
            ),
            safety = listOf("Am Ufer bleiben, Vögel nicht aufscheuchen."),
            parking = p("Parkplatz Gress/Tong Beach", "Gebührenfrei"),
            arrival = a("ca. 15 Min. / 12 km ab Stornoway", "A857 Richtung Tong/Gress"),
            sources = listOf("Grinneabhat", "Western Isles Wildlife")
        ),
        FotoSpot(
            id = "stornoway_black_guillemot",
            name = "Hafen Stornoway – Tordalken (Black Guillemots)",
            motif = Motif.OTHER_ANIMALS,
            lat = 58.2094, lon = -6.3849,
            ratings = listOf(r(SPRING, 9, 90), r(SUMMER, 9, 90), r(AUTUMN, 9, 90), r(WINTER, 9, 90)),
            bestTimeOfDay = "Ganzjährig am Kai",
            tide = null,
            tips = listOf("Tordalken (Tysties) leben ganzjährig im Hafen – leicht zu fotografieren."),
            safety = listOf("Hafenbereich: Kai-Kanten meiden."),
            parking = p("Parkplatz Hafen Stornoway", "Gebührenpflichtig tagsüber"),
            arrival = a("im Zentrum Stornoway", "Fußläufig"),
            sources = listOf("Western Isles Wildlife", "RSPB")
        ),
        FotoSpot(
            id = "butt_of_lewis_landscape",
            name = "Butt of Lewis – Leuchtturm & Klippen",
            motif = Motif.LANDSCAPE,
            lat = 58.5149, lon = -6.2628,
            ratings = listOf(r(SPRING, 9, 95), r(SUMMER, 10, 98), r(AUTUMN, 9, 95), r(WINTER, 7, 85)),
            bestTimeOfDay = "Sonnenuntergang (Nordwesten)",
            tide = null,
            tips = listOf(
                "Nördlichster Punkt Großbritanniens – Leuchtturm und Klippen bei Westwind.",
                "Weitwinkel für Leuchtturm + Klippen, Stativ für Langzeitbelichtung."
            ),
            safety = listOf("Klippenrand meiden, besonders bei Wind."),
            parking = p("Parkplatz Butt of Lewis", "Großer Schotterparkplatz am Leuchtturm, gebührenfrei"),
            arrival = a("ca. 35 Min. / 30 km ab Stornoway", "A857 bis Tolsta, weiter Richtung Butt of Lewis"),
            sources = listOf("VisitScotland", "WalkHighlands", "Wikidata Q13630879 (58.5149, -6.2628)")
        ),
        FotoSpot(
            id = "callanish_landscape",
            name = "Callanish Steinkreis",
            motif = Motif.LANDSCAPE,
            lat = 58.1975, lon = -6.7450,
            ratings = listOf(r(SPRING, 8, 90), r(SUMMER, 9, 95), r(AUTUMN, 8, 90), r(WINTER, 6, 80)),
            bestTimeOfDay = "Später Nachmittag / Gegenlicht",
            tide = null,
            tips = listOf("Jungsteinzeitlicher Steinkreis, überraschend wenig besucht."),
            safety = listOf("Steine nicht besteigen – geschütztes Denkmal."),
            parking = p("Parkplatz Callanish Stones", "Parkplatz am Besucherzentrum, gebührenfrei"),
            arrival = a("ca. 30 Min. / 25 km ab Stornoway", "A858 Richtung Barvas/Harris, Abzweig Callanish"),
            sources = listOf("Historic Environment Scotland", "VisitScotland", "Wikipedia/OS-Grid NB213330 (58.1975, -6.7450)")
        ),
        FotoSpot(
            id = "lestal_landscape",
            name = "Traigh Luskentyre (Luskentyre Beach)",
            motif = Motif.LANDSCAPE,
            lat = 57.9320, lon = -7.0590,
            ratings = listOf(r(SPRING, 7, 85), r(SUMMER, 10, 98), r(AUTUMN, 7, 85), r(WINTER, 5, 70)),
            bestTimeOfDay = "Morgens oder bei Ebbe für Spiegelungen",
            tide = t(low = true, note = "Bei Ebbe Wattflächen und Spiegelungen im Vordergrund"),
            tips = listOf(
                "Türkisblaues Wasser, weißer Sand – einer der schönsten Strände Europas.",
                "Hochlandrinder und Schafe auf den Dünen als Motiv im Vordergrund."
            ),
            safety = listOf("Ebbe/Flut beachten – langer Rückweg über Watt möglich."),
            parking = p("Parkplatz Luskentyre Beach", "Großer Parkplatz am Ende der Single-Track-Road, gebührenfrei"),
            arrival = a("ca. 30 Min. / 25 km ab Tarbert", "A859 Richtung Leverburgh, Abzweig Luskentyre"),
            sources = listOf("VisitScotland", "OpenStreetMap")
        ),
        FotoSpot(
            id = "scarista_beach",
            name = "Scarista Beach (West-Tarbert, Harris)",
            motif = Motif.BEACHES,
            lat = 57.8250, lon = -7.0511,
            ratings = listOf(r(SPRING, 7, 85), r(SUMMER, 9, 95), r(AUTUMN, 8, 90), r(WINTER, 6, 80)),
            bestTimeOfDay = "Sonnenuntergang über dem Atlantik",
            tide = t(low = true, note = "Bei Niedrigwasser weite Wattflächen im Vordergrund"),
            tips = listOf("Breiter Sandstrand nahe West Tarbert, beliebt bei Kitesurfern."),
            safety = listOf("Dünen nicht betreten – Erosionsschutz."),
            parking = p("Parkplatz Scarista Beach", "Parkplatz hinter dem Dünenwall, gebührenfrei"),
            arrival = a("ca. 10 Min. / 8 km ab Tarbert", "Küstenstraße von West Tarbert Richtung Scarista"),
            sources = listOf("VisitScotland", "WalkHighlands", "Apple Maps (57.8250188, -7.0511059)")
        ),
        FotoSpot(
            id = "ness_beach",
            name = "Ness Beach (Port Ness, Nord-Lewis)",
            motif = Motif.BEACHES,
            lat = 58.4895, lon = -6.2500,
            ratings = listOf(r(SPRING, 7, 85), r(SUMMER, 9, 95), r(AUTUMN, 7, 85), r(WINTER, 5, 70)),
            bestTimeOfDay = "Abendsonne",
            tide = t(low = true, note = "Bei Ebbe Dünen und Watt im Vordergrund"),
            tips = listOf("Sandstrand am Nordende von Lewis nahe dem Hafen von Ness."),
            safety = listOf("Dünen schützen – markierte Wege nutzen."),
            parking = p("Parkplatz Port Ness", "Parkplatz am Hafen/Strandzugang, gebührenfrei"),
            arrival = a("ca. 30 Min. / 25 km ab Stornoway", "A857 Richtung Tolsta/Port of Ness"),
            sources = listOf("VisitScotland", "OpenStreetMap", "latitude.to (Port of Ness 58.4892, -6.2227)")
        )
    )

    fun spotsForMotif(motif: Motif): List<FotoSpot> = allSpots.filter { it.motif == motif }

    fun spotById(id: String): FotoSpot? = allSpots.firstOrNull { it.id == id }
}
