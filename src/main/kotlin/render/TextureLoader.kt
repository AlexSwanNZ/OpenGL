package render

import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL12
import org.lwjgl.opengl.GL11.*
import java.io.File

/**
 * TextureLoader class adapted from Krythic@StackOverflow
 */
object TextureLoader {
    private val BYTES_PER_PIXEL = 4//3 for RGB, 4 for RGBA
    fun loadTexture(image: BufferedImage): Int {

        val pixels = IntArray(image.width * image.height)
        image.getRGB(
                0, 0, image.width, image.height,
                pixels, 0, image.width)

        val buffer = BufferUtils.createByteBuffer(
                image.width * image.height * BYTES_PER_PIXEL) //4 for RGBA, 3 for RGB

        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                val pixel = pixels[y * image.width + x]
                buffer.put((pixel shr 16 and 0xFF).toByte())     // Red component
                buffer.put((pixel shr 8 and 0xFF).toByte())      // Green component
                buffer.put((pixel and 0xFF).toByte())               // Blue component
                buffer.put((pixel shr 24 and 0xFF).toByte())    // Alpha component. Only for RGBA
            }
        }

        buffer.flip() //FOR THE LOVE OF GOD DO NOT FORGET THIS

        // You now have a ByteBuffer filled with the color data of each pixel.
        // Now just create a texture ID and bind it. Then you can load it using
        // whatever OpenGL method you want, for example:

        val textureID = glGenTextures() //Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID) //Bind texture ID

        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE)

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

        //Send texel data to OpenGL
        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGBA8, image.width,
                image.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)

        //Return the texture ID so we can bind it later again
        return textureID
    }

    fun loadImage(loc: String): BufferedImage? {
        try {
            return ImageIO.read(File(loc))
        } catch (e: IOException) {
            //Error Handling Here
        }

        return null
    }
}