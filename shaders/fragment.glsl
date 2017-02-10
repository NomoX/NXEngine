#version 330

struct DirectionalLight {
	vec3 colour;
	float intensity;
};

in vec3 fragPos;
in vec3 fragNormal;
in vec2 UV;

uniform sampler2D textureSampler;

uniform vec3 lightPos;
uniform vec3 lightColor;

out vec4 color;

void main() {
	float ambientStrength = 0.1f;
	vec3 ambient = ambientStrength * lightColor;

	vec3 norm = normalize(fragNormal);
	vec3 lightDir = normalize(lightPos); //  - fragPos

	float diff = max(dot(norm, lightDir), 0.0);
	vec3 diffuse = diff * lightColor;
	vec4 result = vec4(ambient + diffuse, 1.0);

	color = result * texture(textureSampler, UV).rgba;
}
