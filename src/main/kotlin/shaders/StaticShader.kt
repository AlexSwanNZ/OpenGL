package shaders

/**
 * Shader for rendering static objects
 */
class StaticShader: Shader(
        /** Static vertex shader file */
        "vertexShader.txt",
        /** Static fragment shader file */
        "fragmentShader.txt"){

    /**
     * Binds the fragment shader input vector
     */
    override fun bindAttributes(){
        super.bindAttribute(0, "position")
    }

}