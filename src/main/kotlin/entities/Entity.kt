package entities

import models.TexturedModel
import org.joml.Vector3f

class Entity(
        val model: TexturedModel,
        var pos: Vector3f,
        var rot: Vector3f,
        var scale: Float){

    fun move(dx: Float, dy: Float, dz: Float){
        pos.x += dx; pos.y += dy; pos.z += dz
    }

    fun rotate(dx: Float, dy: Float, dz: Float){
        rot.x += dx; rot.y += dy; rot.z += dz
    }

}