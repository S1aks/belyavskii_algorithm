import kotlin.math.max
import kotlin.math.min

data class Point(
    val x: Long,
    val y: Long
)

data class Rect(
    val left: Long,
    val right: Long,
    val top: Long,
    val bottom: Long,
    val width: Long,
    val height: Long
)

data class Figure(
    val n: Int,
    val kinks: Long,
    val startPoint: Point,
    val rotatePoint: Point,
    val rect: Rect
)

fun rotate90(rotateCenter: Point, point: Point) =
    Point(rotateCenter.x - (point.y - rotateCenter.y), rotateCenter.y + (point.x - rotateCenter.x))

fun rotateAndMergeFrom(figure: Figure): Figure {
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
    val figures = mutableListOf<Figure>()
    print("Number n = ")
    when (val number = readLine()!!.toInt()) {
        in 1..100 -> {
            figures.add(
                Figure(
                    0,
                    1,
                    Point(0, 0), Point(0, 1),
                    Rect(0, 0, 1, 0, 0, 1)
                )
            )
            for (i in 1..number) {
                figures.add(rotateAndMergeFrom(figures[i - 1]))
                println("$i: (${figures[i].rect.width},${figures[i].rect.height})")
            }
        }
        else -> println("Wrong number!")
    }
}