package shaders

import entities.Camera
import org.joml.Matrix4f
import utils.Maths

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
    /** Location of view matrix */
    private var viewMatrixLoc = super.getUniformLocation("viewMatrix")
    /** Math utility object */
    private val maths = Maths()

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
        viewMatrixLoc = super.getUniformLocation("viewMatrix")
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

    /**
     * Loads a view matrix to OpenGL
     * @param cam The camera to use to create the view matrix
     */
    fun loadViewMatrix(cam: Camera){
        super.loadMatrix(viewMatrixLoc, maths.createViewMatrix(cam))
    }

}