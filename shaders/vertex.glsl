#version 330
layout (location=0) in vec3 vertexPos;
layout (location=1) in vec2 vertexUV;
layout (location=2) in vec3 vertexNormal;

out vec3 fragPos;
out vec3 fragNormal;
out vec2 UV;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main() {
	vec4 mvPos = modelViewMatrix * vec4(vertexPos, 1.0);
	gl_Position = projectionMatrix * mvPos;
	UV = vertexUV;

	fragPos = vec3(modelViewMatrix * vec4(vertexPos, 1.0f));
	fragNormal = vertexNormal;
}
