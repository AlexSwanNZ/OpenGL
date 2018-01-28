package entities

import org.joml.Vector3f
import org.lwjgl.input.Keyboard

class Camera {

    val pos = Vector3f(0f, 0f, 0f)
    val pitch = 0f
    val yaw = 0f
    val roll = 0f

    fun move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) pos.z -= 0.02f
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) pos.z += 0.02f
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) pos.x -= 0.02f
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) pos.x += 0.02f
    }

}