package shaders

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.*
import java.io.File
import java.io.IOException

/**
 * Shader class defining functionality for implemented shaders.
 * Each shader object has a program ID 'ID' bound to a
 * vertex shader and a fragment shader
 *
 * @param vFile name of vertex shader
 * @param fFile name of fragment shader
 */
abstract class Shader(vFile: String, fFile: String){

    /** Shader directory */
    private var dir = "src/main/kotlin/shaders/"
    /** ID of the vertex shader */
    private var vID = loadShader(dir + vFile, GL_VERTEX_SHADER)
    /** ID of the fragment shader */
    private var fID = loadShader(dir + fFile, GL_FRAGMENT_SHADER)
    /** Shader program ID */
    private var id = glCreateProgram()

    init{
        glAttachShader(id, vID)
        glAttachShader(id, fID)
        bindAttributes()
        glLinkProgram(id)
        glValidateProgram(id)
    }

    /**
     * Start using this shader
     */
    fun start(){
        glUseProgram(id)
    }

    /**
     * Stop the shader program
     */
    fun stop(){
        glUseProgram(0)
    }

    /**
     * Remove shaders from memory
     */
    fun free(){
        stop()
        glDetachShader(id, vID)
        glDetachShader(id, fID)
        glDeleteShader(vID)
        glDeleteShader(fID)
        glDeleteProgram(id)
    }

    /**
     * Creates and compiles a shader based on a source file and shader type
     * @param file path to shader file
     * @param type type of shader as defined by GL_VERTEX_SHADER, GL_FRAGMENT_SHADER etc
     * @return shader ID
     */
    private fun loadShader(file: String, type: Int): Int{
        val src = StringBuilder()
        try{
            src.append(File(file).readText()) //Read shader file
        }catch(e: IOException){
            e.printStackTrace()
            error("Error reading file")
        }
        val sID = glCreateShader(type)
        glShaderSource(sID, src)
        glCompileShader(sID)
        if(glGetShaderi(sID, GL_COMPILE_STATUS) == GL_FALSE){
            println(glGetShaderInfoLog(sID, 500))
            error("Error compiling $file")
        }
        return sID //Successful compilation
    }

    /**
     * Inheriting classes must bind attributes to their respective shaders
     */
    abstract fun bindAttributes()

    /**
     * Binds an attribute to the current shader
     * @param attrib attribute ID
     * @param name name of attribute in shader file
     */
    open fun bindAttribute(attrib: Int, name: String){
        glBindAttribLocation(id, attrib, name)
    }

    /**
     * Prints an error to System.err and exits the program
     * @param msg error description
     */
    private fun error(msg: String){
        System.err.println(msg)
        System.exit(-1)
    }

}