#version 330
layout (location=0) in vec3 vertexPos;
layout (location=1) in vec2 vertexUV;
layout (location=2) in vec3 vertexNorm;

out vec2 UV;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main() {
	UV = vertexUV;
	gl_Position = projectionMatrix * modelViewMatrix * vec4(vertexPos, 1.0);
}
