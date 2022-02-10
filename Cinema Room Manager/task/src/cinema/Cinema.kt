package cinema

import java.util.*

const val TICKET_PRICE_FULL = 10
    const val TICKET_PRICE_DISC = 8

fun main() {

    println("Enter the number of rows:")
    val numOfRows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val numOfSeats = readLine()!!.toInt()
    val cinemaLayout = MutableList(numOfRows) { MutableList(numOfSeats) { 'S' } }

    //Stats
    var ticketsBought = 0
    var currentIncome = 0
    val totalSeats = numOfRows * numOfSeats
    val totalIncome = if (totalSeats <= 60) totalSeats * TICKET_PRICE_FULL else
        numOfSeats * ((numOfRows / 2) * TICKET_PRICE_FULL  + (numOfRows - numOfRows / 2) * TICKET_PRICE_DISC )

    while (true) {
        println()
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")

        when (readLine()!!.toInt()) {
            0 -> break
            1 -> printLayout(cinemaLayout)
            2 -> {
                if (ticketsBought < totalSeats) {
                    currentIncome += buyTicket(cinemaLayout)
                    ticketsBought++
                } else {
                    println("All tickets are sold!")
                }
            }
            3 -> {
                val percentage = "%.2f".format(Locale.US, 100.0 * ticketsBought / totalSeats)
                println()
                println("Number of purchased tickets: $ticketsBought")
                println("Percentage: $percentage%")
                println("Current income: \$$currentIncome")
                println("Total income: \$$totalIncome")
            }
            else -> continue
        }
    }
}

fun buyTicket(layout: MutableList<MutableList<Char>>): Int {
    while (true) {
        val row: Int
        val seat: Int
        try {
            println("Enter a row number:")
            row = readLine()!!.toInt()
            println("Enter a seat number in that row:")
            seat = readLine()!!.toInt()
        } catch (e: NumberFormatException) {
            println("\nWrong input!\n")
            continue
        }

        if (row !in 1..layout.size || seat !in 1..layout.first().size) {
            println("\nWrong input!\n")
            continue
        }

        if (layout[row - 1][seat - 1] != 'B') {
            val ticketPrice = getTicketPrice(layout.size, layout.first().size, row)
            layout[row - 1][seat - 1] = 'B'
            println("\nTicket price: \$$ticketPrice")
            return ticketPrice
        } else {
            println("\nThat ticket has already been purchased!\n")
            continue
        }
    }
}

fun printLayout(layout: MutableList<MutableList<Char>>) {
    println()
    println("Cinema:")
    print("  ")
    for (i in 1..layout[0].size) {
        print("$i ")
    }
    print("\n")
    for (i in layout.indices) {
        val line = layout[i].joinToString(" ")
        println("${i + 1} $line")
    }
}

fun getTicketPrice(rows: Int, seats: Int, row: Int): Int {
    return if (rows * seats <= 60) {
        TICKET_PRICE_FULL
    } else {
        if (row <= rows / 2) TICKET_PRICE_FULL else TICKET_PRICE_DISC
    }
}