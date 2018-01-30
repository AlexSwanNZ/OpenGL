package utils

import entities.Camera
import entities.Entity
import org.joml.Matrix4f
import org.joml.Vector3f

class Maths {

    /**
     * Creates a transformation matrix
     * @param e Entity to create matrix for
     * @return Transformation matrix
     */
    fun createTransMatrix(e: Entity): Matrix4f {

        val mat = Matrix4f()
        mat.apply{
            identity()
            translate(e.pos)
            rotateXYZ(rad(e.rot))
            scale(e.scale)
        }
        return mat

    }

    /**
     * Creates a view matrix for the camera
     * @param cam Camera
     * @return View matrix
     */
    fun createViewMatrix(cam: Camera): Matrix4f{
        val mat = Matrix4f()
        mat.apply{
            identity()
            rotateX(rad(cam.pitch))
            rotateY(rad(cam.yaw))
            translate(-cam.pos.x, -cam.pos.y, -cam.pos.z)
        }
        return mat
    }

    /**
     * Converts a float to radians
     * @param f Float to convert to radians
     * @return radians
     */
    private fun rad(f: Float): Float{
        return Math.toRadians(f.toDouble()).toFloat()
    }

    /**
     * Converts a float vector to radians
     * @param v Vector3f to convert to radians
     * @return Vector3f in radians
     */
    private fun rad(v: Vector3f): Vector3f{
        return Vector3f(rad(v.x), rad(v.y), rad(v.z))
    }

}