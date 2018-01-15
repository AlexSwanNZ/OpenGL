package render

/**
 * Describes the models index in the Vertex Array list and the
 * number of vertices in the model
 *
 * @param vaoID VAO index number
 * @param vertexCount number of vertices in the model
 */
data class RawModel(val vaoID: Int, val vertexCount: Int)