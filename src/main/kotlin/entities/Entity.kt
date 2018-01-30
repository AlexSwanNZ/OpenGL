package entities

import models.TexturedModel
import org.joml.Vector3f

class Entity(
        val model: TexturedModel,
        var pos: Vector3f,
        var rx: Float,
        var ry: Float,
        var rz: Float,
        var scale: Float){

    fun move(dx: Float, dy: Float, dz: Float){
        pos.x += dx; pos.y += dy; pos.z += dz
    }

    fun rotate(dx: Float, dy: Float, dz: Float){
        rx += dx; ry += dy; rz += dz
    }

}