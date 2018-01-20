package render

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*
import shaders.StaticShader
import entities.Entity
import org.lwjgl.util.vector.Matrix4f
import shaders.Shader
import utils.Maths

/**
 * Render utility class for preparing a frame and rendering a model
 */
class Renderer(
        /** Screen width */
        private val width: Int,
        /** Screen height */
        private val height: Int,
        /** Shader */
        private val shader: StaticShader) {

    /** Maths utility object */
    private val maths = Maths()
    /** Field of View */
    private val fov = 70
    /** Near plane render limit */
    private val nearPlane = 0.1f
    /** Far plane render limit */
    private val farPlane = 1000f
    /** Main projection matrix */
    private val projMatrix = createProjectionMatrix()

    init{
        shader.start()
        shader.loadProjMatrix(projMatrix)
        shader.stop()
    }

    /**
     * Prepares a new frame by clearing it to a solid colour
     */
    fun prepare(){
        glClearColor(0.0f,0.0f,0.0f,0.0f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    /**
     * Renders a model to the display
     * @param entity to draw
     */
    fun render(entity: Entity, shader: StaticShader){
        val texModel = entity.model
        val rawModel = texModel.model
        glBindVertexArray(rawModel.vaoID)
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        shader.loadTransMatrix(maths.createTransMatrix(entity))
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, texModel.tex.texID)
        glDrawElements(GL_TRIANGLES, rawModel.vertexCount, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glBindVertexArray(0)
    }

    /**
     * Creates a projection matrix to be loaded to a vertex shader
     * @return The projection matrix
     */
    private fun createProjectionMatrix() : Matrix4f{

        val aspectRatio = width.toFloat() / height.toFloat()
        val yScale = ((1f / Math.tan(Math.toRadians((fov / 2f).toDouble())))
                * aspectRatio).toFloat()
        val xScale = yScale / aspectRatio
        val frustrumLength = farPlane - nearPlane

        val pm = Matrix4f()
        pm.m00 = xScale
        pm.m11 = yScale
        pm.m22 = -((farPlane + nearPlane) / frustrumLength)
        pm.m33 = 0f
        pm.m23 = -1f
        pm.m32 = -((2f * farPlane * nearPlane) / frustrumLength)

        return pm
    }

}