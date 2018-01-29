package utils

import entities.Camera
import entities.Entity
import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f

class Maths {

    fun createTransMatrix(trans: Vector3f,
                          rx: Float, ry: Float, rz: Float,
                          scale: Float): Matrix4f {

        val mat = Matrix4f()
        mat.setIdentity()
        Matrix4f.translate(trans, mat, mat)
        Matrix4f.rotate(Math.toRadians(rx.toDouble()).toFloat(),
                Vector3f(1f,0f,0f), mat, mat)
        Matrix4f.rotate(Math.toRadians(ry.toDouble()).toFloat(),
                Vector3f(0f,1f,0f), mat, mat)
        Matrix4f.rotate(Math.toRadians(rz.toDouble()).toFloat(),
                Vector3f(0f,0f,1f), mat, mat)
        Matrix4f.scale(Vector3f(scale, scale, scale), mat, mat)
        return mat

    }

    fun createTransMatrix(e: Entity): Matrix4f{
        return createTransMatrix(e.pos, e.rx, e.ry, e.rz, e.scale)
    }

    /**
     * Creates a view matrix for the camera
     * @param cam Camera
     * @return View matrix
     */
    fun createViewMatrix(cam: Camera): Matrix4f{
        val mat = Matrix4f()

        mat.setIdentity()
        Matrix4f.rotate(Math.toRadians(cam.pitch.toDouble()).toFloat(),
                Vector3f(1f,0f,0f), mat, mat)
        Matrix4f.rotate(Math.toRadians(cam.yaw.toDouble()).toFloat(),
                Vector3f(0f,1f,0f), mat, mat)
        Matrix4f.translate(Vector3f(-cam.pos.x, -cam.pos.y, -cam.pos.z), mat, mat)

        return mat
    }

}