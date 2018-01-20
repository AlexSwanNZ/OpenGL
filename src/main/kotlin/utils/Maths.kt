package utils

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

}