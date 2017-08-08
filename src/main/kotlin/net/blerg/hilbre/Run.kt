package net.blerg.hilbre

fun main(args: Array<String>) {

    DirectoryWatcher("/home/arthur/blerg.net/hilbre/test").scan { fileAction, s -> println("$fileAction on $s") }
}