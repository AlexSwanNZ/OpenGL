package render

import entities.Camera
import entities.Entity
import models.TexturedModel
import org.joml.Vector3f
import org.lwjgl.glfw.*
import org.lwjgl.opengl.*
import org.lwjgl.glfw.Callbacks.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.*
import org.lwjgl.system.MemoryUtil.*
import shaders.StaticShader
import textures.ModelTexture

/**
 * OpenGL program that renders a texture to the screen
 *
 * @author Alex Swan
 * @version 2018.01.15
 * @since 2018.01.15
 */
class Main : Thread(){

    /** Window reference */
    private var window: Long = 0L
    /** Window width */
    private val width = 800
    /** Window height */
    private val height = 600
    /** Window title */
    private val title = "Swan LWJGL"

    /** Loader utility object reference */
    private val loader = Loader()
    /** Static Shader reference */
    private lateinit var shader: StaticShader
    /** Render utility object reference */
    private lateinit var renderer: Renderer
    /** Camera */
    private lateinit var camera: Camera

    /**
     * Executes the main Thread
     */
    override fun run() {
        init()

        //A basic rectangle made of two triangles
        val model = loader.loadToVAO(
                floatArrayOf(
                        -0.5f, 0.5f, 0f,
                        -0.5f, -0.5f, 0f,
                        0.5f, -0.5f, 0f,
                        0.5f, 0.5f, 0f),
                intArrayOf(
                        0,1,3,
                        3,1,2),
                floatArrayOf(
                        0f,0f,
                        0f,1f,
                        1f,1f,
                        1f,0f
                ))
        val tex = ModelTexture(loader.loadTexture("wolf.png"))
        val texturedModel = TexturedModel(model, tex)
        val entity = Entity(texturedModel, Vector3f(-1f,0f,-1f),
                0f, 0f, 0f, 1f)

        //The main rendering loop
        while (!glfwWindowShouldClose(window)) {

            entity.move(0.002f, 0f, -0.004f)
            entity.rotate(0f, 1f, 0.1f)
            camera.move()

            renderer.prepare()
            shader.start()
            shader.loadViewMatrix(camera)
            renderer.render(entity, shader)
            shader.stop()

            glfwSwapBuffers(window) //Swaps the frame to the prepared one
            glfwPollEvents() //Checks for user input

        }

        //Free graphics memory
        loader.free()
        shader.free()
        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)
        glfwTerminate()
        glfwSetErrorCallback(null).free()
    }

    /**
     * Initialises GLFW, OpenGL and prepares a window for rendering
     * @throws IllegalStateException if GLFW is unable to initialise
     * @throws RuntimeException is GLFW cannot create a new window
     */
    private fun init() {
        GLFWErrorCallback.createPrint(System.err).set()
        if (!glfwInit()) throw IllegalStateException("Unable to initialize GLFW")

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        window = glfwCreateWindow(width, height, title, NULL, NULL)
        if (window == NULL) throw RuntimeException("Failed to create the GLFW window")

        //Bind the key 'ESCAPE' to quit the program
        glfwSetKeyCallback(window) { window, key, _, action, _ ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true)
        }

        stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)
            glfwGetWindowSize(window, pWidth, pHeight)
            val videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
            glfwSetWindowPos(window,
                    (videoMode.width() - pWidth.get(0)) / 2,
                    (videoMode.height() - pHeight.get(0)) / 2)
        }

        glfwMakeContextCurrent(window)
        GL.createCapabilities()
        glfwSwapInterval(1) //Enable V Sync
        glfwShowWindow(window)
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_TEXTURE_2D)
        println(glGetString(GL_VERSION))

        shader = StaticShader()
        renderer = Renderer(width, height, shader)
        camera = Camera(window)

    }

    /**
     * Object for launching the program
     */
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            Main().start()

        }
    }

}