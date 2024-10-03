package com.shadowings.renshuuwidget.main

import androidx.compose.ui.graphics.Color


data class Theme(
    val name: String,
    val backgroundColor: Color,
    val textColor: Color
)

val themes = arrayOf(
    Theme(
        name = "Boring Default",
        backgroundColor = Color.Gray,
        textColor = Color.Black
    ),
    Theme(
        name = "Nebula Glow",
        backgroundColor = Color(26, 43, 76),
        textColor = Color(165, 230, 92)
    ),
    Theme(
        name = "Crimson Frost",
        backgroundColor = Color(153, 0, 0),
        textColor = Color(224, 224, 224)
    ),
    Theme(
        name = "Twilight Mist",
        backgroundColor = Color(107, 94, 114),
        textColor = Color(248, 245, 225)
    ),
    Theme(
        name = "Solar Amber",
        backgroundColor = Color(255, 191, 0),
        textColor = Color(77, 44, 15)
    ),
    Theme(
        name = "Ocean Whisper",
        backgroundColor = Color(163, 228, 215),
        textColor = Color(32, 58, 67)
    ),
    Theme(
        name = "Velvet Midnight",
        backgroundColor = Color(11, 12, 16),
        textColor = Color(243, 202, 64)
    ),
    Theme(
        name = "Lunar Pearl",
        backgroundColor = Color(221, 221, 221),
        textColor = Color(66, 103, 178)
    ),
    Theme(
        name = "Forest Ember",
        backgroundColor = Color(11, 61, 11),
        textColor = Color(255, 120, 64)
    ),
    Theme(
        name = "Desert Mirage",
        backgroundColor = Color(225, 198, 153),
        textColor = Color(14, 47, 68)
    ),
    Theme(
        name = "Aurora Blaze",
        backgroundColor = Color(255, 106, 0),
        textColor = Color(255, 215, 0)
    ),
    Theme(
        name = "Cobalt Dream",
        backgroundColor = Color(0, 71, 171),
        textColor = Color(240, 244, 248)
    ),
    Theme(
        name = "Ivy Breeze",
        backgroundColor = Color(29, 115, 52),
        textColor = Color(193, 200, 202)
    ),
    Theme(
        name = "Eclipse Flame",
        backgroundColor = Color(28, 28, 28),
        textColor = Color(255, 78, 80)
    ),
    Theme(
        name = "Glacial Fade",
        backgroundColor = Color(192, 223, 242),
        textColor = Color(22, 44, 66)
    ),
    Theme(
        name = "Magma Surge",
        backgroundColor = Color(230, 57, 70),
        textColor = Color(40, 36, 37)
    ),
    Theme(
        name = "Cyber Neon",
        backgroundColor = Color(34, 34, 59),
        textColor = Color(0, 255, 204)
    ),
    Theme(
        name = "Autumn Rust",
        backgroundColor = Color(204, 102, 51),
        textColor = Color(92, 64, 51)
    ),
    Theme(
        name = "Frozen Dawn",
        backgroundColor = Color(173, 216, 230),
        textColor = Color(44, 62, 80)
    ),
    Theme(
        name = "Shadow Ash",
        backgroundColor = Color(55, 55, 55),
        textColor = Color(169, 169, 169)
    ),
    Theme(
        name = "Mystic Teal",
        backgroundColor = Color(54, 117, 136),
        textColor = Color(237, 247, 242)
    ),
    Theme(
        name = "Golden Dusk",
        backgroundColor = Color(255, 165, 0),
        textColor = Color(105, 105, 105)
    ),
    Theme(
        name = "Ruby Abyss",
        backgroundColor = Color(128, 0, 0),
        textColor = Color(224, 192, 192)
    ),
    Theme(
        name = "Stellar Bloom",
        backgroundColor = Color(248, 131, 121),
        textColor = Color(255, 251, 235)
    ),
    Theme(
        name = "Polar Night",
        backgroundColor = Color(36, 48, 66),
        textColor = Color(187, 225, 250)
    ),
    // Tema USA
    Theme(
        name = "Stars and Stripes",
        backgroundColor = Color(60, 59, 110),  // Blu della bandiera USA
        textColor = Color(255, 255, 255)       // Bianco delle stelle e strisce
    ),
    Theme(
        name = "American Flame",
        backgroundColor = Color(178, 34, 34),  // Rosso delle strisce
        textColor = Color(255, 255, 255)       // Bianco delle stelle
    ),

    // Tema Italia
    Theme(
        name = "Italia Verde",
        backgroundColor = Color(0, 146, 70),   // Verde della bandiera italiana
        textColor = Color(255, 255, 255)       // Bianco del tricolore
    ),
    Theme(
        name = "Roman Passion",
        backgroundColor = Color(206, 43, 55),  // Rosso del tricolore italiano
        textColor = Color(255, 255, 255)       // Bianco neutrale
    ),

    // Tema Giappone
    Theme(
        name = "Rising Sun",
        backgroundColor = Color(255, 255, 255), // Bianco della bandiera giapponese
        textColor = Color(188, 0, 45)           // Rosso del sole
    ),
    Theme(
        name = "Tokyo Night",
        backgroundColor = Color(188, 0, 45),   // Rosso del sole giapponese
        textColor = Color(255, 255, 255)       // Bianco del campo
    ),

    // Tema Germania
    Theme(
        name = "German Steel",
        backgroundColor = Color(0, 0, 0),      // Nero della bandiera tedesca
        textColor = Color(255, 204, 0)         // Giallo della bandiera
    ),
    Theme(
        name = "Berlin Flame",
        backgroundColor = Color(221, 0, 0),    // Rosso della bandiera tedesca
        textColor = Color(255, 255, 255)       // Testo bianco per contrasto
    ),

    // Tema Inghilterra
    Theme(
        name = "Union Jack Blue",
        backgroundColor = Color(0, 36, 125),   // Blu della bandiera britannica
        textColor = Color(255, 255, 255)       // Bianco della croce
    ),
    Theme(
        name = "St. George's Cross",
        backgroundColor = Color(255, 255, 255), // Bianco della bandiera inglese
        textColor = Color(200, 16, 46)          // Rosso della croce di San Giorgio
    ),
    // Tema Brasile
    Theme(
        name = "Samba Green",
        backgroundColor = Color(0, 156, 59),   // Verde della bandiera brasiliana
        textColor = Color(255, 223, 0)         // Giallo del rombo centrale
    ),
    Theme(
        name = "Carnival Gold",
        backgroundColor = Color(255, 223, 0),  // Giallo della bandiera brasiliana
        textColor = Color(0, 39, 118)          // Blu della sfera centrale
    ),

    // Tema Messico
    Theme(
        name = "Mexican Pride",
        backgroundColor = Color(0, 104, 71),   // Verde della bandiera messicana
        textColor = Color(255, 255, 255)       // Bianco del tricolore
    ),
    Theme(
        name = "Aztec Flame",
        backgroundColor = Color(206, 17, 38),  // Rosso della bandiera messicana
        textColor = Color(255, 255, 255)       // Bianco neutrale
    ),

    // Tema Sudafrica
    Theme(
        name = "Rainbow Nation",
        backgroundColor = Color(0, 127, 102),  // Verde della bandiera sudafricana
        textColor = Color(255, 215, 0)         // Giallo della Y centrale
    ),
    Theme(
        name = "Cape Red",
        backgroundColor = Color(213, 43, 30),  // Rosso della bandiera sudafricana
        textColor = Color(255, 255, 255)       // Bianco per contrasto
    ),

    // Tema Argentina
    Theme(
        name = "Argentine Sky",
        backgroundColor = Color(116, 172, 223), // Azzurro della bandiera argentina
        textColor = Color(255, 255, 255)        // Bianco delle bande
    ),
    Theme(
        name = "Sun of May",
        backgroundColor = Color(255, 204, 0),  // Giallo del sole della bandiera
        textColor = Color(0, 105, 148)         // Blu del campo superiore
    ),

    // Tema India
    Theme(
        name = "Indian Saffron",
        backgroundColor = Color(255, 153, 51),  // Arancione (safran) della bandiera indiana
        textColor = Color(255, 255, 255)        // Bianco centrale
    ),
    Theme(
        name = "Ashoka Wheel",
        backgroundColor = Color(19, 136, 8),    // Verde della bandiera indiana
        textColor = Color(0, 0, 255)            // Blu della ruota Ashoka
    )
)
