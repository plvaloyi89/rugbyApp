package com.phillVa.rugbyapp.ui.home

class News(val uuid: String, val title: String, val url: String, val source: String, val image_url: String)

class UsersArticles(
    var id: String,
    var image: String,
    var title: String,
    var link: String,
    var author: String
) {
    constructor() : this("", "", "", "", "")
}