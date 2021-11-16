import kotlin.math.max
import kotlin.math.min

data class Point(
    val x: Long,
    val y: Long
)

data class Rect(
    val pointTopLeft: Point,
    val pointBottomRight: Point,
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
    val topPoint = max(figure.rect.pointTopLeft.y, rotate90(figure.rotatePoint, figure.rect.pointBottomRight).y)
    val bottomPoint = min(figure.rect.pointBottomRight.y, rotate90(figure.rotatePoint, figure.rect.pointTopLeft).y)
    val leftPoint = min(figure.rect.pointTopLeft.x, rotate90(figure.rotatePoint, figure.rect.pointTopLeft).x)
    val rightPoint = max(figure.rect.pointBottomRight.x, rotate90(figure.rotatePoint, figure.rect.pointBottomRight).x)
    return Figure(
        figure.n + 1,
        figure.kinks * 2 + 1,
        figure.startPoint,
        rotate90(figure.rotatePoint, figure.startPoint),
        Rect(Point(leftPoint, topPoint), Point(rightPoint, bottomPoint), rightPoint - leftPoint, topPoint - bottomPoint)
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
                    Rect(Point(0, 1), Point(0, 0), 0, 1)
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