package render

import models.RawModel
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack
import java.nio.FloatBuffer
import java.nio.IntBuffer
import org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER

/**
 * Utility class for binding models into OpenGL Vertex Array Objects
 */
class Loader {

    /** A list tracking Vertex Array Objects on the GPU and their respective ID's */
    private val vaos = ArrayList<Int>(1)
    /** A list tracking Vertex Buffer Objects on the GPU and their respective ID's */
    private val vbos = ArrayList<Int>(1)
    /** A list tracking Textures on the GPU and their respective ID's */
    private val textures = ArrayList<Int>(1)
    /** Resource directory */
    private val resPath = "src/main/resources/"

    /**
     * Loads a model into a Vertex Array Object
     *
     * @param positions list of vertex positions
     * @param indices triangle indexes
     * @return model reference
     */
    fun loadToVAO(positions: FloatArray, indices: IntArray,
                  texCoords: FloatArray): RawModel {
        val vaoID = createVAO()
        bindIndicesBuffer(indices)
        storeInAttribList(0, positions, 3)
        storeInAttribList(1, texCoords, 2)
        glBindVertexArray(0)
        return RawModel(vaoID, indices.size)
    }

    /**
     * Loads a texture file
     * @param file name to image
     * @return texture ID on GPU
     */
    fun loadTexture(file: String): Int{

        val texID = glGenTextures()
        val path = resPath + file

        MemoryStack.stackPush().use { stack ->

            val w = stack.mallocInt(1)
            val h = stack.mallocInt(1)
            val comp = stack.mallocInt(1)

            stbi_set_flip_vertically_on_load(true)
            val image = stbi_load(path, w, h, comp, 4)
                    ?: throw RuntimeException("Failed to load a texture file!"
                    + System.lineSeparator() + stbi_failure_reason())

            glBindTexture(GL_TEXTURE_2D, texID)

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)

            val srcPixelFormat = //if (hasAlpha) GL_RGBA else GL_RGB
                    GL_RGBA

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, w.get(), h.get(),
                    0, srcPixelFormat, GL_UNSIGNED_BYTE, image)

        }

        textures.add(texID)
        return texID

    }

    /**
     * Creates a Vertex Array Object and adds it to the list
     * @return VAO Index
     */
    private fun createVAO(): Int{
        val vaoID = glGenVertexArrays()
        vaos.add(vaoID)
        glBindVertexArray(vaoID)
        return vaoID
    }

    /**
     * Stores float data in an attribute list
     * @param attribNum attribute number
     * @param data vertex data
     */
    private fun storeInAttribList(attribNum: Int,
                                  data: FloatArray, coordSize: Int){
        val vboID = glGenBuffers()
        vbos.add(vboID)
        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        val buffer = storeInFloatBuffer(data)
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        glVertexAttribPointer(  attribNum, coordSize, GL_FLOAT,
                                false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    /**
     * Stores an array of floats into a FloatBuffer
     * @param data The float data
     * @return The FloatBuffer
     */
    private fun storeInFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer: FloatBuffer = BufferUtils.createFloatBuffer(data.size)
        buffer.apply{   put(data)
                        flip()      }
        return buffer
    }

    /**
     * Stores vertex indices into an OpenGL Vertex Buffer Object
     * @param indices Vertex indices
     */
    private fun bindIndicesBuffer(indices: IntArray){
        val vboID = glGenBuffers()
        vbos.add(vboID)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID)
        val buffer = storeInIntBuffer(indices)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
    }

    /**
     * Creates an IntBuffer from vertex indices
     * @param indices vertex indices
     * @return IntBuffer
     */
    private fun storeInIntBuffer(indices: IntArray): IntBuffer{
        val buffer = BufferUtils.createIntBuffer(indices.size)
        buffer.put(indices)
        buffer.flip()
        return buffer
    }

    /**
     * Releases the models from memory
     */
    fun free(){
        for(vao in vaos) glDeleteVertexArrays(vao)
        for(vbo in vbos) glDeleteBuffers(vbo)
        for(tex in textures) glDeleteTextures(tex)
    }

}