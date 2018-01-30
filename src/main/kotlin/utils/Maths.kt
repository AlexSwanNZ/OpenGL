package utils

import entities.Camera
import entities.Entity
import org.joml.Matrix4f
import org.joml.Vector3f

class Maths {

    /**
     * Creates a transformation matrix
     * @param trans The translation vector
     * @param rx X rotation
     * @param ry Y rotation
     * @param rz Z rotation
     * @param scale Scale
     * @return Transformation matrix
     */
    private fun createTransMatrix(trans: Vector3f,
                                  rx: Float, ry: Float, rz: Float,
                                  scale: Float): Matrix4f {

        val mat = Matrix4f()
        mat.apply{
            identity()
            translate(trans)
            rotateX(rad(rx))
            rotateY(rad(ry))
            rotateZ(rad(rz))
            scale(scale)
        }
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

}