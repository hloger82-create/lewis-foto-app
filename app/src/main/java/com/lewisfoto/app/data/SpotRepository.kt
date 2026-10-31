package com.lewisfoto.app.data

import com.lewisfoto.app.data.Season.AUTUMN
import com.lewisfoto.app.data.Season.SPRING
import com.lewisfoto.app.data.Season.SUMMER
import com.lewisfoto.app.data.Season.WINTER

object SpotRepository {

    val dataSources: List<Pair<String, String>> = listOf(
        "Hebridean Whale & Dolphin Trust (HWDT) – Sichtungsdaten & Tiumpan Head" to "https://www.hwdt.org",
        "RSPB – Vogelschutz & Reserves Äußere Hebriden (Loch na Muilne)" to "https://www.rspb.org.uk",
        "SNH / NatureScot – Schutzgebiete & Brutvorkommen" to "https://www.nature.scot",
        "Visit Outer Hebrides – Spot-Guides (Eagles, Deer, Hare, Seals, Bird of Prey Trail, Photography Trail)" to "https://www.visitouterhebrides.co.uk",
        "VisitScotland – Lewis & Harris Highlights" to "https://www.visitscotland.com",
        "Scottish Wildlife Trust – Reserves Outer Hebrides" to "https://scottishwildlifetrust.org.uk",
        "WalkHighlands – North Harris Eagle Observatory & Wanderungen" to "https://www.walkhighlands.co.uk",
        "Grinneabhat (Bragar) – Birdwatching West-Lewis" to "https://www.grinneabhat.com",
        "Western Isles Wildlife – Vogelarten & Status / Latest Sightings" to "https://western-isles-wildlife.co.uk",
        "BirdForum Opus – Isle of Lewis" to "https://www.birdforum.net/opus/Isle_of_Lewis",
        "Birdingplaces.eu / BirdGuides – Birding-Spots Outer Hebrides" to "https://birdingplaces.eu",
        "eBird (Cornell Lab) – aktuelle Vogelsichtungen (letzte 3 Tage)" to "https://ebird.org",
        "Seatrek (Miavaig) – Wildlife-Bootstouren" to "https://www.seatrek.co.uk",
        "Immerse Hebrides (Stornoway) – Wal- & Delfintouren" to "https://www.immersehebrides.com",
        "Hebridean Adventures – Minch-Kreuzfahrten" to "https://www.hebrideanadventures.co.uk",
        "Reasort Estates – Amhuinnsuidhe (Deer, Otter)" to "https://reasortestates.co.uk",
        "Richard Watson / Warner-Tate / Nature TTL / landscapelocations / imageseen – Foto-Workshops & Locations" to "https://www.richardwatsonphoto.com",
        "PetaPixel / Wex Photo Video / John MacLean Photography / highlands2hammocks – Fotospot-Blogs" to "https://www.petapixel.com",
        "OpenStreetMap – Kartenmaterial & Parkplätze (ODbL)" to "https://www.openstreetmap.org",
        "Wikidata / Wikipedia / British Listed Buildings – verifizierte Koordinaten" to "https://www.wikidata.org",
        "Open-Meteo – Wettervorhersage (CC BY 4.0)" to "https://open-meteo.com",
        "RainViewer – Niederschlagsradar" to "https://www.rainviewer.com"
    )

    private fun r(season: Season, probability: Int) = Rating(season, probability)
    private fun p(name: String, note: String, dist: String = "") = ParkingInfo(name, note, dist)
    private fun t(low: Boolean, note: String) = TideInfo(low, note)

    val allSpots: List<FotoSpot> = listOf(
        FotoSpot(
            id = "huisinis_otter",
            name = "Huisinis & Loch Resort (West-Harris)",
            motif = Motif.OTTER,
            lat = 58.0080, lon = -7.0850,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 85), r(AUTUMN, 75), r(WINTER, 65)),
            bestTimeOfDay = "Dämmerung (früh morgens / abends)",
            tide = t(low = true, note = "Bei Ebbe/Flut am aktivsten; Küstenwanderung am Wasser"),
            tips = listOf(
                "Klassischer Otter-Spot an der weißen Sandbucht von Huisinis.",
                "Ruhig am Ufer entlanggehen, Fernglas bereithalten.",
                "Alternative: Geführte Otter-Bootstour ab Miavaig (Seatrek)."
            ),
            safety = listOf("Einschusslöcher/Schlamm am Ufer: rutschig – festes Schuhwerk."),
            parking = p("Parkplatz Huisinis Beach", "Kleiner Parkplatz an der B887, gebührenfrei", "ca. 300 m Fußweg zum Ufer"),
            sources = listOf("Visit Outer Hebrides", "Seatrek / HWDT")
        ),
        FotoSpot(
            id = "uig_sands_otter",
            name = "Uig Sands / Ardroil (Loch Roag, West-Lewis)",
            motif = Motif.OTTER,
            lat = 58.1860, lon = -7.0200,
            what3words = "///await.denser.bagpipes",
            ratings = listOf(r(SPRING, 75), r(SUMMER, 80), r(AUTUMN, 70), r(WINTER, 65)),
            bestTimeOfDay = "Bei Ebbe, morgens oder abends",
            tide = t(low = true, note = "Otter jagen bei Ebbe auf dem Riff vor Ardroil"),
            tips = listOf(
                "Otter sitzen oft auf den Felsen im Watt (Ardroil).",
                "Bootstouren ab Miavaig fahren gezielt Otter-Spots an."
            ),
            safety = listOf("Ebbe beachten: Rückkehrweg vor Flut sichern, Watt kann schnell zufluten."),
            parking = p("Parkplatz Uig Sands (Ardroil)", "Parkplatz am Ende der Single-Track-Road, gebührenfrei; what3words ///await.denser.bagpipes", "ca. 200 m Fußweg über die Dünen zum Strand"),
            sources = listOf("Tripadvisor-Reviews Uig Sands", "Seatrek", "WildBounds", "scotlandlessexplored.com (what3words)")
        ),
        FotoSpot(
            id = "seatrek_boat_otter",
            name = "Seatrek Bootstour ab Miavaig (Otter, Robben, Seeadler)",
            motif = Motif.OTTER,
            lat = 58.2030, lon = -6.9470,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 95), r(AUTUMN, 85), r(WINTER, 40)),
            bestTimeOfDay = "Saison April–Oktober, morgens oder bei ruhiger See",
            tide = t(low = true, note = "Bei Ebbe mehr Otter an der Küste sichtbar"),
            tips = listOf(
                "Wildlife-Bootstouren ab Miavaig Jetty: Otter, Seehunde, Seeadler, Delfine.",
                "Saison April–Oktober; Buchung empfohlen.",
                "Alternative: Pabbay/Shiants-Touren mit Seatrek."
            ),
            safety = listOf("Seekrankheit: Tabletten vor der Fahrt; Rettungsweste wird gestellt."),
            parking = p("Parkplatz Seatrek Miavaig Jetty", "Direkt am Hafen, gebührenfrei", "direkt am Anleger"),
            sources = listOf("Seatrek (seatrek.co.uk)", "CalMac – Seatrek Hebrides", "Visit Outer Hebrides")
        ),
        FotoSpot(
            id = "amhuinnsuidhe_bay_otter",
            name = "Amhuinnsuidhe Bay (West-Harris) – Otter & Robben",
            motif = Motif.OTTER,
            lat = 57.9614, lon = -6.9904,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 80), r(AUTUMN, 75), r(WINTER, 65)),
            bestTimeOfDay = "Dämmerung",
            tide = t(low = true, note = "Bei Ebbe Otter an der felsigen Küste"),
            tips = listOf("Bucht unter Amhuinnsuidhe Castle: Otter und Robben laut Estate-Quelle regelmäßig."),
            safety = listOf("Privatgelände des Estates: öffentliche Wege beachten."),
            parking = p("Parkplatz Amhuinnsuidhe Castle", "Parkplatz am Castle, gebührenfrei", "ca. 200 m Fußweg zur Bucht"),
            sources = listOf("Reasort Estates (reasortestates.co.uk)", "sobt.co.uk")
        ),
        FotoSpot(
            id = "pairc_seaforth_otter",
            name = "Pairc / Ufer des Loch Seaforth (Süd-Lewis)",
            motif = Motif.OTTER,
            lat = 57.9550, lon = -6.5150,
            ratings = listOf(r(SPRING, 70), r(SUMMER, 75), r(AUTUMN, 80), r(WINTER, 70)),
            bestTimeOfDay = "Dämmerung",
            tide = null,
            tips = listOf(
                "Starkburg von Seeadlern UND Fischottern (Bird-of-Prey-Trail).",
                "Nebenstraßen ab A859 Richtung Ardveg/Loch Seaforth nutzen."
            ),
            safety = listOf("Single-Track-Roads im Pairc: Ausweichstellen nutzen, langsam fahren."),
            parking = p("Parkbucht an der Nebenstraße zum Loch Seaforth", "Unbefestigte Ausweichbucht, gebührenfrei", "ca. 500 m Fußweg zum Ufer"),
            sources = listOf("Visit Outer Hebrides – Bird of Prey Trail")
        ),
        FotoSpot(
            id = "tolsta_garry_otter",
            name = "Tolsta Head / Garry Beach (Nordost-Lewis)",
            motif = Motif.OTTER,
            lat = 58.3470, lon = -6.2130,
            ratings = listOf(r(SPRING, 65), r(SUMMER, 70), r(AUTUMN, 65), r(WINTER, 55)),
            bestTimeOfDay = "Bei Ebbe entlang der Kelp-Riffe",
            tide = t(low = true, note = "Kelp-Riffe bei Ebbe: Otter jagen dort häufig"),
            tips = listOf(
                "Küstenwanderung ab Garry Beach Richtung Tolsta Head.",
                "Teleobjektiv für Ufertiere."
            ),
            safety = listOf("Klippen und nasse Felsen: Trittsicherheit beachten."),
            parking = p("Parkplatz Garry Beach", "Parkplatz am Strand, gebührenfrei", "direkt am Strand"),
            sources = listOf("Visit Outer Hebrides", "WildBounds")
        ),
        FotoSpot(
            id = "sound_of_harris_seals",
            name = "Insel Groay im Sound of Harris – Kegelrobben-Kolonie (Fähre Leverburgh–Berneray)",
            motif = Motif.SEALS,
            lat = 57.7380, lon = -7.1150,
            ratings = listOf(r(SPRING, 80), r(SUMMER, 85), r(AUTUMN, 95), r(WINTER, 90)),
            bestTimeOfDay = "Bei der Fährüberfahrt, gesamte Strecke beobachten",
            tide = t(low = true, note = "Bei Ebbe sitzen Robben auf den Felsen (Groay)"),
            tips = listOf(
                "Okt–Dez: Welpen der Kegelrobben – spektakulär, aber Abstand!",
                "Von der Fähre aus mit Teleobjektiv fotografieren."
            ),
            safety = listOf("Nie an Robben-Welpen annähern – Muttertiere verteidigen sie aggressiv."),
            parking = p("Parkplatz Fährhafen Leverburgh", "Großer Hafenparkplatz, gebührenfrei (Fähre gebührenpflichtig)", "Fähre, von Bord fotografieren"),
            sources = listOf("Visit Outer Hebrides – Grey Seal Sound of Harris", "HWDT", "ports.marinelink.com (Koordinaten Leverburgh)")
        ),
        FotoSpot(
            id = "luskentyre_seals",
            name = "Luskentyre / Horgabost (Harris)",
            motif = Motif.SEALS,
            lat = 57.8910, lon = -6.9610,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 90), r(AUTUMN, 85), r(WINTER, 75)),
            bestTimeOfDay = "Bei Ebbe am Strand und im Sound of Taransay",
            tide = t(low = true, note = "Bei Ebbe mehr Tiere am Strand sichtbar"),
            tips = listOf(
                "Kegel- und Seehunde schwimmen oft nahe am Strand – Kayaker berichten regelmäßig Sichtungen.",
                "Türkisblaues Wasser: Polfilter für Unterwasser-/Strandaufnahmen."
            ),
            safety = listOf("Hunde an der Leine – Robben können beunruhigt werden."),
            parking = p("Parkplatz Luskentyre Beach", "Großer Schotterparkplatz am Ende der Single-Track-Road, gebührenfrei", "direkt am Strand"),
            sources = listOf("VisitScotland", "Bluefin SUP – Seal Spotting UK")
        ),
        FotoSpot(
            id = "shawbost_loch_roag_seals",
            name = "Äußerer Loch Roag – Robben-Inseln Bearasaigh / Flodaigh / Vacsay (ab Shawbost)",
            motif = Motif.SEALS,
            lat = 58.2268, lon = -6.9177,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 90), r(AUTUMN, 85), r(WINTER, 80)),
            bestTimeOfDay = "Bei Ebbe",
            tide = t(low = true, note = "Seehunde ruhen bei Ebbe auf den Felsen der Inseln im äußeren Loch Roag"),
            tips = listOf(
                "Loch Roag: Kayak-Touren ab Shawbost zu den Robben-Inseln (Bearasaigh, Flodaigh, Vacsay).",
                "Vom Ufer bei Shawbost aus mit Teleobjektiv auf die Inseln fotografieren."
            ),
            safety = listOf("Bei Kayak: Schwimmweste, Tide beachten."),
            parking = p("Parkplatz Shawbost", "Kleiner Parkplatz an der Küste, gebührenfrei", "ca. 300 m Fußweg"),
            sources = listOf("Visit Outer Hebrides", "Virtual Hebrides – Seals")
        ),
        FotoSpot(
            id = "butt_of_lewis_seals",
            name = "Butt of Lewis – Klippen (Kegelrobben)",
            motif = Motif.SEALS,
            lat = 58.5149, lon = -6.2628,
            ratings = listOf(r(SPRING, 80), r(SUMMER, 85), r(AUTUMN, 90), r(WINTER, 85)),
            bestTimeOfDay = "Bei Ebbe, 2 h vor/nach Niedrigwasser",
            tide = t(low = true, note = "Robben sonnen sich bei Ebbe an den Klippen"),
            tips = listOf("Kegelrobben an den Klippen unterhalb des Leuchtturms (Visit Outer Hebrides)."),
            safety = listOf("Klippenrand meiden; Abstand > 30 m zu Robben."),
            parking = p("Parkplatz Butt of Lewis", "Großer Schotterparkplatz, gebührenfrei", "ca. 200 m Fußweg"),
            sources = listOf("Visit Outer Hebrides – Grey Seal Butt of Lewis", "Virtual Hebrides")
        ),
        FotoSpot(
            id = "stornoway_harbour_seals",
            name = "Hafen Stornoway / Arnish Point",
            motif = Motif.SEALS,
            lat = 58.2094, lon = -6.3849,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 90), r(AUTUMN, 90), r(WINTER, 85)),
            bestTimeOfDay = "Bei Ebbe am Kai / Arnish Point",
            tide = t(low = true, note = "Seehunde ruhen bei Ebbe auf den Steinen am Hafen"),
            tips = listOf(
                "Standort-treue Seehunde am Hafen, oft von Fischern gesehen.",
                "Kurzer Spaziergang vom Zentrum Stornoway."
            ),
            safety = listOf("Hafenbereich: nicht auf Kais klettern."),
            parking = p("Parkplatz Hafen Stornoway", "Öffentlicher Hafenparkplatz, gebührenpflichtig tagsüber", "direkt am Kai"),
            sources = listOf("Scotsman – beste Seal-Spots Schottlands", "Virtual Hebrides", "latlong.net (Koordinaten Stornoway 58.209435, -6.384869)")
        ),
        FotoSpot(
            id = "aird_mhor_bragar_seals",
            name = "Aird Mhòr Bragar – Küsten-Point (Nordwest-Lewis, zw. Labost und Bragar)",
            motif = Motif.SEALS,
            lat = 58.3450, lon = -6.6550,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 90), r(AUTUMN, 85), r(WINTER, 80)),
            bestTimeOfDay = "2 Stunden vor/nach Niedrigwasser",
            tide = t(low = true, note = "Robben auf den Felsen bei Ebbe am Point"),
            tips = listOf(
                "Küsten-Headland Aird Mhòr an der NW-Küste bei Bragar – Robben auf den Felsen.",
                "Gute Kombination mit Loch Ordais (Limikolen) in der Nähe."
            ),
            safety = listOf("Felsiger Point: Trittsicherheit, nicht bei Sturm hinausgehen."),
            parking = p("Parkbucht an der A858 bei Labost (südl. Bragar)", "Kleine Parkbucht an der Küstenstraße, gebührenfrei", "ca. 500 m Fußweg zum Point"),
            sources = listOf("Gazetteer for Scotland – Aird More Bragar (Headland an der NW-Küste)", "Visit Outer Hebrides – West Side Coastal Path", "Virtual Hebrides")
        ),
        FotoSpot(
            id = "northton_waders",
            name = "Northton Machair & Ästuar (Taobh Tuath, Harris)",
            motif = Motif.WADERS,
            lat = 57.8005, lon = -7.0762,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 70), r(AUTUMN, 95), r(WINTER, 95)),
            bestTimeOfDay = "Bei Ebbe und um Niedrigwasser",
            tide = t(low = true, note = "Wattflächen bei Ebbe: beste Limikolen-Beobachtung"),
            tips = listOf(
                "Feuchte Machair-Flächen und Strand: Steinwälzer, Ringel-/Sandregenpfeifer, Lummen, Knutt.",
                "Winter: zusätzlich Alpenstrandläufer und Sanderlinge."
            ),
            safety = listOf("Machair nicht mit dem Auto befahren – empfindliches Grasland."),
            parking = p("Parkplatz Northton Beach", "Parkplatz am Strand, gebührenfrei", "direkt am Strand"),
            sources = listOf("Western Isles Wildlife", "Visit Outer Hebrides – Birdwatching")
        ),
        FotoSpot(
            id = "loch_na_muilne_waders",
            name = "RSPB Loch na Muilne (Arnol, West-Lewis) – Limikolen & Schnepfen",
            motif = Motif.WADERS,
            lat = 58.3958, lon = -6.8165,
            ratings = listOf(r(SPRING, 95), r(SUMMER, 95), r(AUTUMN, 85), r(WINTER, 90)),
            bestTimeOfDay = "Frühling/Sommer Brutzeit; Winter: Pfeifschwäne & Gänse",
            tide = null,
            tips = listOf(
                "RSPB-Reservat: seltenes Thorshühnchen (red-necked phalarope), Rotschenkel, Kiebitz, Schnepfen.",
                "Aussichtspunkt (cairn) mit Blick über die zwei Seen.",
                "Hunde an der Leine (Brutzeit April–Mitte August)."
            ),
            safety = listOf("Nur RSPB-Parkplatz nutzen; Wege nicht verlassen."),
            parking = p("RSPB-Parkplatz Loch na Muilne", "Nur dieser Parkplatz nutzen, gebührenfrei", "ca. 400 m Fußweg zum Aussichtspunkt"),
            sources = listOf("RSPB Loch na Muilne Reserve", "Grinneabhat – Birdwatching", "birdingplaces.eu")
        ),
        FotoSpot(
            id = "loch_barvas_waders",
            name = "Loch Barvas (Barvas, Nord-Lewis) – Watvögel",
            motif = Motif.WADERS,
            lat = 58.3588, lon = -6.5486,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 80), r(AUTUMN, 95), r(WINTER, 90)),
            bestTimeOfDay = "Herbstzug (Sept–Okt) und Winter",
            tide = null,
            tips = listOf("Großer Süßwassersee: Schnatterenten, Blässgänse, Tauchenten; Sichtungen von Großem Brachvogel und Eismöwe im Winter."),
            safety = listOf("Uferweg schlammig nach Regen."),
            parking = p("Parkbucht am Loch Barvas", "Straßenrand, gebührenfrei", "direkt am Ufer"),
            sources = listOf("Western Isles Wildlife – Latest Sightings", "BirdForum Opus Isle of Lewis")
        ),
        FotoSpot(
            id = "tong_gress_waders",
            name = "Tong Saltings & Broad Bay (bei Stornoway)",
            motif = Motif.WADERS,
            lat = 58.2400, lon = -6.3450,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 80), r(AUTUMN, 95), r(WINTER, 95)),
            bestTimeOfDay = "Bei Hochwasser (Flut) am Salzwiesen-Rand",
            tide = t(low = false, note = "Bei Flut ziehen sich Watvögel auf die Salzwiesen zurück – Hochwasserrand ideal"),
            tips = listOf(
                "Salzwiesen bei Tong/Gress: Austernfischer, Rotschenkel, Brachvögel, evtl. Zwergschnepfen.",
                "Bis zu 800 Eistaucher in der Broad Bay im Winter – Spektiv mitnehmen."
            ),
            safety = listOf("Salzwiesen nicht betreten – Brut- und Rastgebiet."),
            parking = p("Parkplatz am Gress Beach / Tong", "Parkplatz an der Küstenstraße, gebührenfrei", "ca. 200 m Fußweg zu den Salzwiesen"),
            sources = listOf("Grinneabhat", "Western Isles Wildlife")
        ),
        FotoSpot(
            id = "loch_ordais_waders",
            name = "Loch Ordais (Bragar, West-Lewis)",
            motif = Motif.WADERS,
            lat = 58.3450, lon = -6.6600,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 80), r(AUTUMN, 95), r(WINTER, 90)),
            bestTimeOfDay = "Bei Ebbe am Seeufer",
            tide = t(low = true, note = "Ebbe legt Schlammflächen am Loch Ordais frei"),
            tips = listOf(
                "Dokumentiert: Kiebitz, Austernfischer, Sandregenpfeifer, Sanderling, Steinwälzer, Rotschenkel, Dunkler Wasserläufer.",
                "Gemeinde-Projekt Grinneabhat pflegt das Gebiet – Infotafeln vor Ort."
            ),
            safety = listOf("Uferweg kann schlammig sein."),
            parking = p("Parkplatz Loch Ordais (Bragar)", "Parkplatz am See, gebührenfrei", "direkt am Seeufer"),
            sources = listOf("Grinneabhat – Birdwatching", "Western Isles Wildlife")
        ),
        FotoSpot(
            id = "clisham_deer",
            name = "An Cliseam (Clisham) & A859-Pass (Harris)",
            motif = Motif.DEER,
            lat = 57.9600, lon = -6.8000,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 70), r(AUTUMN, 90), r(WINTER, 80)),
            bestTimeOfDay = "Früher Morgen / Abenddämmerung",
            tide = null,
            tips = listOf(
                "Hirsche grasen an den Hängen direkt oberhalb der A859 – vom Auto aus beobachtbar.",
                "Brunft (Sept–Okt): Hirsche röhren – dann besonders unberechenbar."
            ),
            safety = listOf("Brunftzeit: niemals zwischen Hirsch und Hindernis stellen, Abstand > 50 m."),
            parking = p("Parkbucht am Clisham-Pass (A859)", "Ausweichbucht an der A859, gebührenfrei", "direkt an der Straße (A859)"),
            sources = listOf("Visit Outer Hebrides – Clisham Horseshoe", "WalkHighlands", "Wild Harris")
        ),
        FotoSpot(
            id = "glen_langadail_deer",
            name = "Glen Langadail (Süd-Harris) – Rothirsche (Brunft)",
            motif = Motif.DEER,
            lat = 57.8670, lon = -6.9600,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 70), r(AUTUMN, 95), r(WINTER, 85)),
            bestTimeOfDay = "Brunft Sept–Okt: Hirsche röhren; sonst Dämmerung",
            tide = null,
            tips = listOf("Dokumentiertes Hirschrevier (Visit Outer Hebrides): Hirsche oft gegen den Heidehang sichtbar."),
            safety = listOf("Brunft: Abstand > 50 m, nie zwischen Hirsch und Hindernis stellen."),
            parking = p("Parkbucht Glen Langadail", "Ausweichbucht am Glen, gebührenfrei", "ca. 500 m Fußweg in den Glen"),
            sources = listOf("Visit Outer Hebrides – Red Deer Glen Langadail")
        ),
        FotoSpot(
            id = "glen_cravadale_deer",
            name = "Glen Cravadale / Loch Crabhadal (Nord-Harris) – Hirsche & Landschaft",
            motif = Motif.DEER,
            lat = 58.0580, lon = -7.0500,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 75), r(AUTUMN, 95), r(WINTER, 90)),
            bestTimeOfDay = "Bei Schlechtwetter ziehen Hirsche in tiefere Bereiche – oft von der Straße sichtbar",
            tide = null,
            tips = listOf("Über 1000 Rothirsche in Nord-Harris; Glen Cravadale wild und abgelegen."),
            safety = listOf("Single-Track-Road, Ausweichstellen; Moorwanderung: Karte/Kompass."),
            parking = p("Parkplatz Loch Crabhadal", "Am See, gebührenfrei", "ca. 1 km Fußweg ins Glen"),
            sources = listOf("Visit Outer Hebrides – Red Deer Glen Cravadale", "Reasort Estates")
        ),
        FotoSpot(
            id = "glen_vigidale_deer_eagles",
            name = "Glen Vigidale / Bogha Glas (Harris)",
            motif = Motif.DEER,
            lat = 57.9580, lon = -6.6080,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 75), r(AUTUMN, 90), r(WINTER, 85)),
            bestTimeOfDay = "Morgens und spätnachmittags",
            tide = null,
            tips = listOf(
                "Hirsche auf den Bergrücken des Glen Vigidale, oft in Gruppen.",
                "Gleichzeitig Adler-Revier (siehe Adler-Spots) – Kombination möglich."
            ),
            safety = listOf("Abseits der Straße: Moorboden weich, Trittsicherheit."),
            parking = p("Parkplatz Bogha Glas (A859)", "Parkplatz an der A859, gebührenfrei", "ca. 1 km Fußweg ins Glen"),
            sources = listOf("Visit Outer Hebrides", "WalkHighlands", "Wild Harris")
        ),
        FotoSpot(
            id = "uig_hills_deer",
            name = "Uig Hills (West-Lewis)",
            motif = Motif.DEER,
            lat = 58.1700, lon = -6.9800,
            ratings = listOf(r(SPRING, 70), r(SUMMER, 65), r(AUTUMN, 85), r(WINTER, 75)),
            bestTimeOfDay = "Dämmerung",
            tide = null,
            tips = listOf(
                "Dokumentiert: Rothirsche und Brutpaare des Steinadlers in den Uig Hills.",
                "Wanderung ab Uig Sands in die Hügel."
            ),
            safety = listOf("Moorland: Wetter kann schnell umschlagen – Karte/Kompass mitnehmen."),
            parking = p("Parkplatz Uig Sands", "Gemeinsam mit Otter-Spot Ardroil", "ca. 1,5 km Fußweg in die Hügel"),
            sources = listOf("Komoot – South Lewis Naturdenkmäler")
        ),
        FotoSpot(
            id = "pairc_deer_forest",
            name = "Pairc Deer Forest / South Lochs (Süd-Lewis)",
            motif = Motif.DEER,
            lat = 58.0350, lon = -6.4200,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 70), r(AUTUMN, 90), r(WINTER, 85)),
            bestTimeOfDay = "Früher Morgen / Abend",
            tide = null,
            tips = listOf(
                "Klassischer Deer Forest im Süden von Lewis – Chancen an den Nebenstraßen des Pairc.",
                "Langsam fahren: Hirsche überqueren oft die Single-Track-Roads."
            ),
            safety = listOf("Single-Track-Roads: Ausweichstellen, nie auf der Fahrbahn parken."),
            parking = p("Ausweichbucht an Pairc-Nebenstraße", "Unbefestigt, gebührenfrei", "entlang der Nebenstraße"),
            sources = listOf("Visit Outer Hebrides", "VisitScotland")
        ),
        FotoSpot(
            id = "tiumpan_head_whales",
            name = "Tiumpan Head (östlich von Stornoway) – beste Landbeobachtung",
            motif = Motif.WHALES,
            lat = 58.2610, lon = -6.1388,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 90), r(AUTUMN, 70), r(WINTER, 55)),
            bestTimeOfDay = "Bei ruhiger See, ganztägig beobachten",
            tide = null,
            tips = listOf(
                "Bestes landbasiertes Whale-Watching des UK: Schweinswale, Große Tümmler, Zwergwale, gelegentlich Buckelwale.",
                "Im Winter seltener: Schweinswale und Delfine bleiben realistisch, Minke unwahrscheinlich.",
                "Spektiv/Teleobjektiv mitnehmen, Leuchtturm-Parkplatz nutzen."
            ),
            safety = listOf("Klippenrand: Abstand halten, nie allein bei Sturm hinausgehen."),
            parking = p("Parkplatz Tiumpan Head Lighthouse", "Parkplatz am Leuchtturm, gebührenfrei", "ca. 300 m Fußweg zum Leuchtturm/Klippenrand"),
            sources = listOf("HWDT – Tales from the Trail Tiumpanhead", "Visit Outer Hebrides", "BirdForum", "britishlistedbuildings.co.uk (58.261, -6.1388)")
        ),
        FotoSpot(
            id = "immerse_hebrides_whales",
            name = "Immerse Hebrides – Bootstour ab Hafen Stornoway (Wale & Delfine)",
            motif = Motif.WHALES,
            lat = 58.2094, lon = -6.3849,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 90), r(AUTUMN, 85), r(WINTER, 50)),
            bestTimeOfDay = "Saison meist Mai–Sept; ruhige See bevorzugen",
            tide = null,
            tips = listOf(
                "Geführte Touren ab Stornoway: Schweinswale, Delfine, Zwergwale; Shiants-/Papageitaucher-Touren im Sommer.",
                "Buchung online empfohlen."
            ),
            safety = listOf("Seekrankheit beachten; Rettungswesten an Bord."),
            parking = p("Parkplatz Hafen Stornoway", "Hafenparkplatz, gebührenpflichtig tagsüber", "direkt am Anleger"),
            sources = listOf("Immerse Hebrides (immersehebrides.com)", "Visit Outer Hebrides – Boat Tours", "Tripadvisor")
        ),
        FotoSpot(
            id = "hebridean_adventures_minch",
            name = "Hebridean Adventures – Minch-Kreuzfahrt ab Ullapool/Stornoway",
            motif = Motif.WHALES,
            lat = 58.2094, lon = -6.3849,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 95), r(AUTUMN, 85), r(WINTER, 40)),
            bestTimeOfDay = "Saisonale Mehrtages-Kreuzfahrten (Sommer)",
            tide = null,
            tips = listOf("Mit Guide Chris Gomersall: Minke, Schweinswale, Delfine, Orcas möglich; auch Riesenhai."),
            safety = listOf("Mehrtagestour: Seekrankheit, warme Kleidung."),
            parking = p("Hafen Stornoway / Ullapool", "Je nach Abfahrtshafen", "direkt am Anleger"),
            sources = listOf("Hebridean Adventures (hebrideanadventures.co.uk)", "HWDT")
        ),
        FotoSpot(
            id = "butt_of_lewis_whales",
            name = "Butt of Lewis – Leuchtturm",
            motif = Motif.WHALES,
            lat = 58.5149, lon = -6.2628,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 85), r(AUTUMN, 70), r(WINTER, 50)),
            bestTimeOfDay = "Bei ruhiger See vom Leuchtturm aus",
            tide = null,
            tips = listOf(
                "Schweinswale und Delfine vom nördlichsten Punkt; Orcas eher Frühjahr/Sommer.",
                "Kombinierbar mit Landschaftsfotos (Leuchtturm + Klippen)."
            ),
            safety = listOf("Starker Westwind häufig – warme Kleidung, Klippenrand meiden."),
            parking = p("Parkplatz Butt of Lewis", "Großer Schotterparkplatz am Leuchtturm, gebührenfrei", "ca. 200 m Fußweg zum Leuchtturm"),
            sources = listOf("HWDT", "Islandeering – beste Wal-Spots UK", "Wikidata Q13630879 (58.5149, -6.2628)")
        ),
        FotoSpot(
            id = "tolsta_head_whales",
            name = "Tolsta Head (Nordost-Lewis)",
            motif = Motif.WHALES,
            lat = 58.3431, lon = -6.1648,
            ratings = listOf(r(SPRING, 70), r(SUMMER, 85), r(AUTUMN, 70), r(WINTER, 55)),
            bestTimeOfDay = "Bei ruhiger See",
            tide = null,
            tips = listOf("Seawatching vom Headland: Schweinswale, Delfine offshore."),
            safety = listOf("Felsige Küste: Trittsicherheit."),
            parking = p("Parkplatz Tolsta/Garry Beach", "Gemeinsam mit Garry Beach", "ca. 300 m Fußweg zum Headland"),
            sources = listOf("HWDT", "Visit Outer Hebrides")
        ),
        FotoSpot(
            id = "gallan_head_whales",
            name = "Gallan Head / Aird Uig (Westspitze Lewis)",
            motif = Motif.WHALES,
            lat = 58.2500, lon = -7.0450,
            ratings = listOf(r(SPRING, 70), r(SUMMER, 85), r(AUTUMN, 70), r(WINTER, 55)),
            bestTimeOfDay = "Bei ruhiger See, nach Westen hinaus schauen",
            tide = null,
            tips = listOf("Westliches Headland: Schweinswale und Delfine im Atlantik vor der Küste."),
            safety = listOf("Exponierte Westspitze: Sturm meiden."),
            parking = p("Parkplatz Gallan Head", "Parkbucht am Ende der Straße, gebührenfrei", "ca. 400 m Fußweg zur Westspitze"),
            sources = listOf("HWDT", "WalkHighlands", "birdguides.com (58.24008, -7.02731)")
        ),
        FotoSpot(
            id = "eagle_observatory",
            name = "North Harris Eagle Observatory, Glen Mhiabhaig",
            motif = Motif.EAGLES,
            lat = 57.9820, lon = -6.9500,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 90), r(AUTUMN, 80), r(WINTER, 70)),
            bestTimeOfDay = "Vormittag bei gutem Wetter und Thermik",
            tide = null,
            tips = listOf(
                "Zweckgebundene Beobachtungsstelle im Steinadler-Revier – auf Harris brüten ca. 20 Paare.",
                "Ca. 20–40 Min. Fußweg vom Parkplatz durch das Glen.",
                "Auch Seeadler kommen vor."
            ),
            safety = listOf("Weg durch enges Tal: bei Starkregen Wasserstand im Bach beachten."),
            parking = p("Parkplatz Eagle Observatory (Glen Mhiabhaig)", "Parkplatz an der B887, OS-Grid NB 101 063, gebührenfrei", "ca. 2 km Fußweg (30 Min.) zum Observatorium"),
            sources = listOf("WalkHighlands – Harris Eagle Observatory", "myhighlands.de", "visitouterhebrides.co.uk (NB 101 063)")
        ),
        FotoSpot(
            id = "bogha_glas_eagles",
            name = "Bogha Glas / Glen Vigidale (Harris) – Adler-Revier",
            motif = Motif.EAGLES,
            lat = 57.9580, lon = -6.6080,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 95), r(AUTUMN, 90), r(WINTER, 80)),
            bestTimeOfDay = "Vormittag bis früher Nachmittag",
            tide = null,
            tips = listOf(
                "An manchen Tagen bis zu 5–10 Adler (Stein- und Seeadler) über dem Glen.",
                "Vom Parkplatz Bogha Glas (A859) aus beobachtbar."
            ),
            safety = listOf("Nicht ins Glen abseits der Wege laufen – Adlerhorste sind geschützt."),
            parking = p("Parkplatz Bogha Glas (A859)", "Parkplatz direkt an der A859, gebührenfrei", "direkt an der A859 beobachtbar"),
            sources = listOf("Visit Outer Hebrides – Golden Eagles & Bird of Prey Trail")
        ),
        FotoSpot(
            id = "rhenigidale_eagles",
            name = "Rhenigidale (Süd-Harris) – Seeadler an der Küste",
            motif = Motif.EAGLES,
            lat = 57.9200, lon = -6.6820,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 90), r(AUTUMN, 80), r(WINTER, 80)),
            bestTimeOfDay = "Vormittag bis Nachmittag",
            tide = null,
            tips = listOf("Dokumentierter Seeadler-Spot an der Westküste Süd-Harris' (Visit Outer Hebrides – White-tailed Sea Eagle Rhenigidale)."),
            safety = listOf("Küstenwanderung: Trittsicherheit, Wetter beachten."),
            parking = p("Parkplatz Rhenigidale", "Am Dorfende, gebührenfrei", "ca. 800 m Fußweg zur Küste"),
            sources = listOf("Visit Outer Hebrides – Bird of Prey Trail / Rhenigidale")
        ),
        FotoSpot(
            id = "aline_woodland_eagles",
            name = "Aline Community Woodland (Loch Erisort, Südost-Lewis) – Adler",
            motif = Motif.EAGLES,
            lat = 58.0900, lon = -6.4700,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 90), r(AUTUMN, 80), r(WINTER, 70)),
            bestTimeOfDay = "Morgens und nachmittags",
            tide = null,
            tips = listOf("Bird-of-Prey-Trail: Aline Woodland als Seeadler-Standort genannt (Visit Outer Hebrides)."),
            safety = listOf("Waldweg: Mücken im Sommer."),
            parking = p("Parkplatz Aline Woodland", "Am Waldrand, gebührenfrei", "ca. 400 m Fußweg"),
            sources = listOf("Visit Outer Hebrides – Bird of Prey Trail")
        ),
        FotoSpot(
            id = "uig_hills_eagles",
            name = "Uig Hills / Mangersta – Klippenadler",
            motif = Motif.EAGLES,
            lat = 58.1550, lon = -7.0300,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 90), r(AUTUMN, 75), r(WINTER, 65)),
            bestTimeOfDay = "Vormittag",
            tide = null,
            tips = listOf("Steinadler brüten in den Uig Hills, Seeadler über den Meeresklippen."),
            safety = listOf("Moorwanderung: Wetter kann umschlagen – Kompass/Karte dabei."),
            parking = p("Parkplatz Mangersta Bay", "Parkbucht am Strand, gebührenfrei", "ca. 1 km Fußweg"),
            sources = listOf("Komoot", "Western Isles Wildlife")
        ),
        FotoSpot(
            id = "loch_seaforth_eagles",
            name = "Ufer des Loch Seaforth (Süd-Lewis)",
            motif = Motif.EAGLES,
            lat = 57.9550, lon = -6.5150,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 90), r(AUTUMN, 80), r(WINTER, 70)),
            bestTimeOfDay = "Morgens und nachmittags",
            tide = null,
            tips = listOf("Seeadler-Starkburg entlang des Loch Seaforth (Bird-of-Prey-Trail)."),
            safety = listOf("Nebenstraße eng – Ausweichstellen nutzen."),
            parking = p("Parkbucht Loch Seaforth (Ardveg)", "Unbefestigte Bucht, gebührenfrei", "ca. 300 m Fußweg zum Ufer"),
            sources = listOf("Visit Outer Hebrides – Bird of Prey Trail")
        ),
        FotoSpot(
            id = "loch_erisort_eagles",
            name = "Loch Erisort / Keose (Südost-Lewis) – Seeadler",
            motif = Motif.EAGLES,
            lat = 58.0954, lon = -6.5090,
            ratings = listOf(r(SPRING, 80), r(SUMMER, 85), r(AUTUMN, 75), r(WINTER, 65)),
            bestTimeOfDay = "Bei Ebbe am Seeufer",
            tide = t(low = true, note = "Bei Ebbe jagen Seeadler entlang des Ufers"),
            tips = listOf("Seeadler besuchen regelmäßig den langen Meeresarm Loch Erisort."),
            safety = listOf("Ebbe/Flut beachten bei Uferwanderung."),
            parking = p("Parkplatz am Kopf des Loch Erisort (Balallan)", "Kleine Parkbucht, gebührenfrei", "ca. 200 m Fußweg zum Ufer"),
            sources = listOf("Visit Outer Hebrides", "Western Isles Wildlife")
        ),
        FotoSpot(
            id = "clisham_hare",
            name = "An Cliseam / Tiorga Mòr – Berghasen",
            motif = Motif.OTHER_ANIMALS,
            lat = 57.9600, lon = -6.8000,
            ratings = listOf(r(SPRING, 65), r(SUMMER, 65), r(AUTUMN, 85), r(WINTER, 90)),
            bestTimeOfDay = "Nov–März bei Schneedecke (weißes Winterfell)",
            tide = null,
            tips = listOf(
                "Berghasen (Lepus timidus scoticus) auf Harris verbreitet – im Winter weiß.",
                "Vom Parkplatz am Clisham-Pass die Hänge absuchen."
            ),
            safety = listOf("Berggelände: Trittsicherheit, Wetter beachten."),
            parking = p("Parkbucht am Clisham-Pass (A859)", "Ausweichbucht, gebührenfrei", "ca. 500 m Fußweg zu den Hängen"),
            sources = listOf("Visit Outer Hebrides – Mountain Hare An Cliseam/Toddun")
        ),
        FotoSpot(
            id = "broad_bay_divers",
            name = "Broad Bay – Eistaucher & Pfeifschwäne",
            motif = Motif.OTHER_ANIMALS,
            lat = 58.2450, lon = -6.3350,
            ratings = listOf(r(SPRING, 75), r(SUMMER, 50), r(AUTUMN, 85), r(WINTER, 95)),
            bestTimeOfDay = "Winter: ganztägig auf dem Wasser",
            tide = null,
            tips = listOf(
                "Im Spätwinter bis zu 800 Eistaucher (Great Northern Divers) in der Broad Bay.",
                "Pfeifschwäne auf den Salzwiesen bei Tong."
            ),
            safety = listOf("Am Ufer bleiben, Vögel nicht aufscheuchen."),
            parking = p("Parkplatz Gress/Tong Beach", "Gebührenfrei", "direkt am Ufer"),
            sources = listOf("Grinneabhat", "Western Isles Wildlife")
        ),
        FotoSpot(
            id = "stornoway_black_guillemot",
            name = "Hafen Stornoway – Trottellummen (Black Guillemots)",
            motif = Motif.OTHER_ANIMALS,
            lat = 58.2094, lon = -6.3849,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 90), r(AUTUMN, 90), r(WINTER, 90)),
            bestTimeOfDay = "Ganzjährig am Kai",
            tide = null,
            tips = listOf("Trottellummen (Tysties) leben ganzjährig im Hafen – leicht zu fotografieren."),
            safety = listOf("Hafenbereich: Kai-Kanten meiden."),
            parking = p("Parkplatz Hafen Stornoway", "Gebührenpflichtig tagsüber", "direkt am Kai"),
            sources = listOf("Western Isles Wildlife", "RSPB")
        ),
        FotoSpot(
            id = "butt_of_lewis_landscape",
            name = "Butt of Lewis – Leuchtturm & Klippen",
            motif = Motif.LANDSCAPE,
            lat = 58.5149, lon = -6.2628,
            ratings = listOf(r(SPRING, 95), r(SUMMER, 98), r(AUTUMN, 95), r(WINTER, 85)),
            bestTimeOfDay = "Sonnenuntergang (Nordwesten)",
            tide = null,
            tips = listOf(
                "Nördlichster Punkt Großbritanniens – Leuchtturm und Klippen bei Westwind.",
                "Weitwinkel für Leuchtturm + Klippen, Stativ für Langzeitbelichtung."
            ),
            safety = listOf("Klippenrand meiden, besonders bei Wind."),
            parking = p("Parkplatz Butt of Lewis", "Großer Schotterparkplatz am Leuchtturm, gebührenfrei", "ca. 200 m Fußweg zum Leuchtturm"),
            sources = listOf("VisitScotland", "WalkHighlands", "Wikidata Q13630879 (58.5149, -6.2628)")
        ),
        FotoSpot(
            id = "callanish_landscape",
            name = "Callanish Steinkreis",
            motif = Motif.LANDSCAPE,
            lat = 58.1975, lon = -6.7450,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 95), r(AUTUMN, 90), r(WINTER, 80)),
            bestTimeOfDay = "Später Nachmittag / Gegenlicht",
            tide = null,
            tips = listOf("Jungsteinzeitlicher Steinkreis, überraschend wenig besucht."),
            safety = listOf("Steine nicht besteigen – geschütztes Denkmal."),
            parking = p("Parkplatz Callanish Stones", "Parkplatz am Besucherzentrum, gebührenfrei", "ca. 300 m Fußweg zu den Steinen"),
            sources = listOf("Historic Environment Scotland", "VisitScotland", "Wikipedia/OS-Grid NB213330 (58.1975, -6.7450)")
        ),
        FotoSpot(
            id = "lestal_landscape",
            name = "Traigh Luskentyre (Luskentyre Beach)",
            motif = Motif.LANDSCAPE,
            lat = 57.8910, lon = -6.9610,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 98), r(AUTUMN, 85), r(WINTER, 70)),
            bestTimeOfDay = "Morgens oder bei Ebbe für Spiegelungen",
            tide = t(low = true, note = "Bei Ebbe Wattflächen und Spiegelungen im Vordergrund"),
            tips = listOf(
                "Türkisblaues Wasser, weißer Sand – einer der schönsten Strände Europas.",
                "Hochlandrinder und Schafe auf den Dünen als Motiv im Vordergrund."
            ),
            safety = listOf("Ebbe/Flut beachten – langer Rückweg über Watt möglich."),
            parking = p("Parkplatz Luskentyre Beach", "Großer Parkplatz am Ende der Single-Track-Road, gebührenfrei", "direkt am Strand"),
            sources = listOf("VisitScotland", "OpenStreetMap", "Warner-Tate Photography", "Nature TTL")
        ),
        FotoSpot(
            id = "luskentyre_hill_viewpoint",
            name = "Aussichtspunkt über Luskentyre Bay (A859, Süd-Harris)",
            motif = Motif.LANDSCAPE,
            lat = 57.8930, lon = -6.9540,
            ratings = listOf(r(SPRING, 95), r(SUMMER, 98), r(AUTUMN, 98), r(WINTER, 90)),
            bestTimeOfDay = "Jede Tageszeit – Panorama über Bucht, Taransay, Nord-Harris-Berge",
            tide = null,
            tips = listOf("„Größter Straßenrand-Ausblick Schottlands“ (Nature TTL): ca. 6 km nach der Luskentyre-Abbiegung, Hügel oberhalb der Straße."),
            safety = listOf("Parkbucht nutzen, nicht auf der Fahrbahn halten."),
            parking = p("Parkbucht am Hügel (A859)", "Ausweichbucht, gebührenfrei", "direkt am Aussichtspunkt"),
            sources = listOf("Nature TTL – Photography Trip Lewis & Harris", "Warner-Tate Photography")
        ),
        FotoSpot(
            id = "seilebost_beach",
            name = "Seilebost Beach (Süd-Harris)",
            motif = Motif.LANDSCAPE,
            lat = 57.8790, lon = -6.9580,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 98), r(AUTUMN, 90), r(WINTER, 80)),
            bestTimeOfDay = "Abendlicht; bei Ebbe flache Buchten und Spiegelungen",
            tide = t(low = true, note = "Bei Ebbe weite flache Flächen – ideal für Spiegelungen"),
            tips = listOf("Fotografen-Favorit: weißer Sand, türkises Wasser, Blick zu den Bergen Nord-Harris' und Taransay."),
            safety = listOf("Bei Ebbe Watt nicht betreten (weich)."),
            parking = p("Parkplatz Seilebost Beach", "Großer Parkplatz, gebührenfrei", "direkt am Strand"),
            sources = listOf("PetaPixel – Landscape Photography Outer Hebrides", "Wex Photo Video – Photographer's Guide", "highlands2hammocks.co.uk")
        ),
        FotoSpot(
            id = "horgabost_beach",
            name = "Horgabost Beach (Süd-Harris)",
            motif = Motif.LANDSCAPE,
            lat = 57.8645, lon = -6.9790,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 98), r(AUTUMN, 90), r(WINTER, 80)),
            bestTimeOfDay = "Abendsonne, Blick auf Taransay",
            tide = t(low = true, note = "Bei Ebbe Sandbänke und Felsen im Vordergrund"),
            tips = listOf("Kompakt, fotogen: Blick auf Taransay; weniger besucht als Luskentyre."),
            safety = listOf("Dünen nicht betreten."),
            parking = p("Parkplatz Horgabost Beach", "Parkplatz an der Küstenstraße, gebührenfrei", "direkt am Strand"),
            sources = listOf("PetaPixel", "margaretsoraya.com – Harris Beaches", "highlands2hammocks.co.uk")
        ),
        FotoSpot(
            id = "scarista_beach",
            name = "Scarista Beach (West-Tarbert, Harris)",
            motif = Motif.LANDSCAPE,
            lat = 57.8270, lon = -7.0440,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 95), r(AUTUMN, 90), r(WINTER, 80)),
            bestTimeOfDay = "Sonnenuntergang über dem Atlantik",
            tide = t(low = true, note = "Bei Niedrigwasser weite Wattflächen im Vordergrund"),
            tips = listOf("Breiter Sandstrand nahe West Tarbert, beliebt bei Kitesurfern."),
            safety = listOf("Dünen nicht betreten – Erosionsschutz."),
            parking = p("Parkplatz Scarista Beach", "Parkplatz hinter dem Dünenwall, gebührenfrei", "direkt am Strand"),
            sources = listOf("VisitScotland", "WalkHighlands", "Apple Maps (57.8250188, -7.0511059)")
        ),
        FotoSpot(
            id = "eoropie_beach",
            name = "Eoropie Beach (Nordspitze Lewis)",
            motif = Motif.LANDSCAPE,
            lat = 58.4983, lon = -6.2727,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 95), r(AUTUMN, 90), r(WINTER, 80)),
            bestTimeOfDay = "Abendlicht Richtung Westen",
            tide = t(low = true, note = "Bei Ebbe riesige Wattflächen"),
            tips = listOf("Weitläufiger Sandstrand am Nordende; Spielplatz am Parkplatz; weniger überlaufen als Ness Beach."),
            safety = listOf("Starke Strömung möglich – nicht weit hinaus schwimmen."),
            parking = p("Parkplatz Eoropie Beach", "Großer Parkplatz mit Spielplatz, gebührenfrei", "direkt am Strand"),
            sources = listOf("highlands2hammocks.co.uk – Best Beaches Lewis & Harris", "Visit Outer Hebrides")
        ),
        FotoSpot(
            id = "ness_beach",
            name = "Ness Beach (Port Ness, Nord-Lewis)",
            motif = Motif.LANDSCAPE,
            lat = 58.4890, lon = -6.2270,
            ratings = listOf(r(SPRING, 85), r(SUMMER, 95), r(AUTUMN, 85), r(WINTER, 70)),
            bestTimeOfDay = "Abendsonne",
            tide = t(low = true, note = "Bei Ebbe Dünen und Watt im Vordergrund"),
            tips = listOf("Sandstrand am Nordende von Lewis nahe dem Hafen von Ness."),
            safety = listOf("Dünen schützen – markierte Wege nutzen."),
            parking = p("Parkplatz Port Ness", "Parkplatz am Hafen/Strandzugang, gebührenfrei", "ca. 300 m Fußweg zum Strand"),
            sources = listOf("VisitScotland", "OpenStreetMap", "latitude.to (Port of Ness 58.4892, -6.2227)")
        ),
        FotoSpot(
            id = "traigh_mor_ness",
            name = "Traigh Mòr (Port of Ness, Nord-Lewis) – längster Strand",
            motif = Motif.LANDSCAPE,
            lat = 58.4960, lon = -6.2330,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 98), r(AUTUMN, 95), r(WINTER, 85)),
            bestTimeOfDay = "Abendsonne",
            tide = t(low = true, note = "Bei Ebbe kilometerlange Wattfläche"),
            tips = listOf("Einer der längsten Strände von Lewis (Visit Outer Hebrides Photography Trail)."),
            safety = listOf("Dünen nicht betreten."),
            parking = p("Parkplatz Traigh Mòr / Port Ness", "Parkplatz am Strand, gebührenfrei", "direkt am Strand"),
            sources = listOf("Visit Outer Hebrides – Photography Trail", "Warner-Tate Photography")
        ),
        FotoSpot(
            id = "whalebone_arch_bragar",
            name = "Whalebone Arch (Bragar, West-Lewis) – Küstenfelsen",
            motif = Motif.LANDSCAPE,
            lat = 58.3341, lon = -6.6290,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 95), r(AUTUMN, 90), r(WINTER, 85)),
            bestTimeOfDay = "Sonnenuntergang (Westen)",
            tide = t(low = true, note = "Bei Ebbe Felsen und Watt zugänglich"),
            tips = listOf("Felsbogen (Wal-Knochen-Arch) bei Bragar; gut für Langzeitbelichtung bei Ebbe."),
            safety = listOf("Felsen rutschig; Klippenrand meiden."),
            parking = p("Parkbucht am Whalebone Arch", "Straßenrand, gebührenfrei", "ca. 300 m Fußweg"),
            sources = listOf("johnmacleanphotography.com – Lewis photo library", "Visit Outer Hebrides")
        ),
        FotoSpot(
            id = "st_clements_rodel",
            name = "St Clement's Church, Rodel (Südost-Harris) – Architektur",
            motif = Motif.LANDSCAPE,
            lat = 57.7406, lon = -6.9640,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 95), r(AUTUMN, 90), r(WINTER, 85)),
            bestTimeOfDay = "Nachmittags; Innenraum: bewölktes Licht von außen",
            tide = null,
            tips = listOf("15. Jh. Kirche mit berühmten mittelalterlichen Schnitzereien – Top-Fotomotiv (Innenraum)."),
            safety = listOf("Kirche respektvoll betreten; kein Stativ ohne Erlaubnis."),
            parking = p("Parkplatz St Clement's Church", "Kirchenparkplatz, gebührenfrei", "direkt an der Kirche"),
            sources = listOf("johnmacleanphotography.com", "Historic Environment Scotland", "Visit Outer Hebrides", "Nature Talks – Fototour")
        ),
        FotoSpot(
            id = "mangersta_sea_stacks",
            name = "Mangersta Sea Stacks (Stac Dhomhnuill Chaim)",
            motif = Motif.LANDSCAPE,
            lat = 58.1390, lon = -7.0520,
            ratings = listOf(r(SPRING, 95), r(SUMMER, 98), r(AUTUMN, 95), r(WINTER, 90)),
            bestTimeOfDay = "Abendlicht von Westen auf die Stacks",
            tide = t(low = true, note = "Bei Ebbe Stacks und Watt zugänglich"),
            tips = listOf("Spektakuläre Atlantik-Felsnadeln – Top-Motiv der Fotografen (imageseen, landscapelocations)."),
            safety = listOf("Felsen rutschig; Klippenrand meiden."),
            parking = p("Parkplatz Mangersta", "Parkbucht am Strand, gebührenfrei", "ca. 400 m Fußweg zu den Stacks"),
            sources = listOf("imageseen.co.uk – Harris & Lewis Workshop", "landscapelocations.com", "Visit Outer Hebrides – Photography Trail")
        ),
        FotoSpot(
            id = "garry_sands_stacks",
            name = "Garry Sands & Felsnadeln (Nordost-Lewis)",
            motif = Motif.LANDSCAPE,
            lat = 58.3480, lon = -6.2140,
            ratings = listOf(r(SPRING, 95), r(SUMMER, 98), r(AUTUMN, 95), r(WINTER, 90)),
            bestTimeOfDay = "Morgenlicht auf den Stacks",
            tide = t(low = true, note = "Bei Ebbe Stacks aus dem Sand ragend"),
            tips = listOf("„Enigmatische Felsnadeln im Sand“ (Visit Outer Hebrides Photography Trail); Garry Beach ideal für ICM/Langzeit."),
            safety = listOf("Watt weich bei Ebbe."),
            parking = p("Parkplatz Garry Beach", "Parkplatz am Strand, gebührenfrei", "direkt am Strand"),
            sources = listOf("Visit Outer Hebrides – Photography Trail", "Warner-Tate Photography – Top 10 Locations")
        ),
        FotoSpot(
            id = "dun_carloway_broch",
            name = "Dun Carloway Broch (West-Lewis)",
            motif = Motif.LANDSCAPE,
            lat = 58.2750, lon = -6.7760,
            ratings = listOf(r(SPRING, 95), r(SUMMER, 98), r(AUTUMN, 95), r(WINTER, 90)),
            bestTimeOfDay = "Später Nachmittag / Gegenlicht",
            tide = null,
            tips = listOf("Besterhaltener Broch Schottlands (eisenzeitlich) – Fotografen-Favorit (Warner-Tate, Heatherlea)."),
            safety = listOf("Innenaufstieg eng; Turm nicht besteigen."),
            parking = p("Parkplatz Dun Carloway", "Parkplatz am Besucherzentrum, gebührenfrei", "ca. 200 m Fußweg"),
            sources = listOf("Warner-Tate Photography", "Historic Environment Scotland", "heatherlea.co.uk")
        ),
        FotoSpot(
            id = "scalpay_lighthouse",
            name = "Scalpay / Eilean Glas Lighthouse (Südost-Harris)",
            motif = Motif.LANDSCAPE,
            lat = 57.8580, lon = -6.7300,
            ratings = listOf(r(SPRING, 95), r(SUMMER, 98), r(AUTUMN, 95), r(WINTER, 90)),
            bestTimeOfDay = "Sonnenuntergang über dem Sound",
            tide = t(low = true, note = "Bei Ebbe Felsen zur Insel begehbar (Vorsicht Tide!)"),
            tips = listOf("Leuchtturm auf Scalpay – klassisches Motiv von Land und vom Wasser (imageseen, John MacLean)."),
            safety = listOf("Tide beachten – Scalpay nur bei Ebbe zu Fuß erreichbar."),
            parking = p("Parkplatz am Scalpay-Anleger (Tarbert)", "Fähre/Boot ab Tarbert; Parkplatz an der Pier, gebührenfrei", "Fähre zur Insel"),
            sources = listOf("imageseen.co.uk", "johnmacleanphotography.com", "Visit Outer Hebrides")
        ),
        FotoSpot(
            id = "gearrannan_blackhouses",
            name = "Gearrannan Blackhouse Village (Uig, West-Lewis)",
            motif = Motif.LANDSCAPE,
            lat = 58.1350, lon = -6.7750,
            ratings = listOf(r(SPRING, 90), r(SUMMER, 95), r(AUTUMN, 90), r(WINTER, 85)),
            bestTimeOfDay = "Blaue Stunde / Abendlicht",
            tide = null,
            tips = listOf("Ruinierte Blackhouses an der Küste – stimmungsvolles Kulturlandschafts-Motiv (landscapelocations)."),
            safety = listOf("Ruinen einsturzgefährdet – nicht betreten."),
            parking = p("Parkplatz Gearrannan", "Kleiner Parkplatz, gebührenfrei", "direkt an den Ruinen"),
            sources = listOf("landscapelocations.com – Harris & Lewis Tour", "Visit Outer Hebrides")
        )
    )

    fun spotsForMotif(motif: Motif): List<FotoSpot> = allSpots.filter { it.motif == motif }

    fun spotById(id: String): FotoSpot? = allSpots.firstOrNull { it.id == id }
}
