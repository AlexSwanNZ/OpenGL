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

    /** Location of transformation matrix */
    private var transMatrixLoc = super.getUniformLocation("transMatrix")
    /** Location of projection matrix */
    private var projMatrixLoc = super.getUniformLocation("projMatrix")

    /**
     * Binds the fragment shader input vectors
     */
    override fun bindAttributes(){
        super.bindAttribute(0, "position")
        super.bindAttribute(1, "texCoords")
    }

    /** Gets the locations of all uniform variables */
    override fun getAllUniformLocations() {
        transMatrixLoc = super.getUniformLocation("transMatrix")
        projMatrixLoc = super.getUniformLocation("projMatrix")
    }

    /**
     * Loads a transformation matrix to OpenGL
     * @param mat The transformation matrix to load to OpenGL
     */
     fun loadTransMatrix(mat: Matrix4f){
        super.loadMatrix(transMatrixLoc, mat)
    }

    /**
     * Loads a projection matrix to OpenGL
     * @param mat The projection matrix to load to OpenGL
     */
    fun loadProjMatrix(mat: Matrix4f){
        super.loadMatrix(projMatrixLoc, mat)
    }

}