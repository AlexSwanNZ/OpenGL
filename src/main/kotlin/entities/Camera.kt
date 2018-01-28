package entities

import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*


class Camera(private val window: Long) {

    val pos = Vector3f(0f, 0f, 0f)
    val pitch = 0f
    val yaw = 0f
    //val roll = 0f

    fun move(){
        if(keyPressed(GLFW_KEY_W)) pos.z -= 0.02f
        if(keyPressed(GLFW_KEY_S)) pos.z += 0.02f
        if(keyPressed(GLFW_KEY_A)) pos.x -= 0.02f
        if(keyPressed(GLFW_KEY_D)) pos.x += 0.02f
    }

    private fun keyPressed(keyCode: Int): Boolean {
        return glfwGetKey(window, keyCode) == GLFW_PRESS
    }



}