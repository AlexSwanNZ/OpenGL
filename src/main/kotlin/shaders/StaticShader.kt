package shaders

import org.lwjgl.util.vector.Matrix4f

/**
 * Shader for rendering static objects
 */
class StaticShader: Shader(
        /** Static vertex shader file */
        "vertexShader.txt",
        /** Static fragment shader file */
        "fragmentShader.txt"){

        var transMatrixLoc = super.getUniformLocation("transMatrix")

    /**
     * Binds the fragment shader input vector
     */
    override fun bindAttributes(){
        super.bindAttribute(0, "position")
        super.bindAttribute(1, "texCoords")
    }

    override fun getAllUniformLocations() {
        transMatrixLoc = super.getUniformLocation("transMatrix")
    }

    fun loadTransMatrix(mat: Matrix4f){
        super.loadMatrix(transMatrixLoc, mat)
    }

}