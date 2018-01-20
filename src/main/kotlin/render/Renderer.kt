package render

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*
import shaders.StaticShader
import entities.Entity
import utils.Maths

/**
 * Render utility class for preparing a frame and rendering a model
 */
class Renderer {

    /** Maths utility object */
    val maths = Maths()

    /**
     * Prepares a new frame by clearing it to a solid colour
     */
    fun prepare(){
        glClearColor(0.0f,0.0f,0.0f,0.0f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    /**
     * Renders a model to the display
     * @param model to draw
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

}