import kotlin.math.max
import kotlin.math.min

data class Point(   // Point class Point(x,y)
    val x: Long,    // x parameter
    val y: Long     // y parameter
)

data class Rect(        // Figure border rectangle class
    val left: Long,     // Left point of rect
    val right: Long,    // Right point
    val top: Long,      // Top point
    val bottom: Long,   // Bottom point
    val width: Long,    // Width of border rectangle
    val height: Long    // Height of border rectangle
)

data class Figure(          // Figure class
    val n: Int,             // Number of action
    val kinks: Long,        // Kinks of Figure
    val startPoint: Point,  // Start point (not changes)
    val rotatePoint: Point, // Rotate point for next action
    val rect: Rect          // Figure border rectangle
)

fun rotate90(rotateCenter: Point, point: Point) = // Rotate point on 90 degrees around rotateCenter
    Point(rotateCenter.x - (point.y - rotateCenter.y), rotateCenter.y + (point.x - rotateCenter.x))

fun rotateAndMergeFrom(figure: Figure): Figure { // Rotate figure border rectangle and merge with last
    val topPoint = max(figure.rect.top, rotate90(figure.rotatePoint, Point(figure.rect.right, figure.rect.bottom)).y)
    val bottomPoint = min(figure.rect.bottom, rotate90(figure.rotatePoint, Point(figure.rect.left, figure.rect.top)).y)
    val leftPoint = min(figure.rect.left, rotate90(figure.rotatePoint, Point(figure.rect.left, figure.rect.top)).x)
    val rightPoint = max(figure.rect.right, rotate90(figure.rotatePoint, Point(figure.rect.right, figure.rect.bottom)).x)
    return Figure(
        figure.n + 1,
        figure.kinks * 2 + 1,
        figure.startPoint,
        rotate90(figure.rotatePoint, figure.startPoint),
        Rect(leftPoint, rightPoint, topPoint, bottomPoint, rightPoint - leftPoint, topPoint - bottomPoint)
    )
}

fun main(args: Array<String>) {
    val figures = mutableListOf<Figure>()       // Create figures array list
    print("Number n = ")
    when (val number = readLine()!!.toInt()) {  // Read n from console
        in 1..100 -> {                          // If n in 1..100 range...
            figures.add(                        // Add first figure with n=0
                Figure(
                    0,
                    1,
                    Point(0, 0), Point(0, 1),
                    Rect(0, 0, 1, 0, 0, 1)
                )
            )
            for (i in 1..number) {              // Cycle from 1 to n
                figures.add(rotateAndMergeFrom(figures[i - 1]))     // Add new figure to list
                println("$i: (${figures[i].rect.width},${figures[i].rect.height})")     // Print data for current iteration
            }
        }
        else -> println("Wrong number!")        // Print if n < 0 or n > 100
    }
}