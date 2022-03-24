@file:Suppress("ClassName", "ClassName", "ClassName", "ClassName", "ClassName", "ClassName",
    "ClassName", "ClassName", "ClassName", "ClassName", "ClassName"
)

package com.plvaloyi.rugbyapp.ui.competitions

class Competitions(val id: String, val image: String, val name: String) {
    constructor() : this(
        "",
        "",
        ""
    )

}

data class standingsTeam(
    val id: String,
    val name: String,
    val played: String,
    val won: String,
    val Draw: String,
    val Lost: String,
    val point: Int
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        0
        )
}

class upcomingMatchesResults(
    val id: String,
    val homeTeam: String,
    val homeScore: String,
    val awayTeam: String,
    val awayScore: String,
    val homeTeamImage: String,
    val awayTeamImage: String,
    val fixtureDate: String,
    val fixtureTime: String,
    val competitionName: String
) {

    constructor() : this("", "", "", "", "", "", "", "", "", "")
}

