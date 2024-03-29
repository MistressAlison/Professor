/**
 * Sketchy Stippling / Dot-Drawing Effect by Ruofei Du (DuRuofei.com)
 * Link to demo: https://www.shadertoy.com/view/ldSyzV
 * starea @ ShaderToy, License Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 *
 * A one-pass shader for dotted drawing / sketch post processing effect.
 * Press the mouse for a slower but more classic sketching effect, though I prefer the dotted version.
 * Shader forked and related ones are listed below.
 * Works better with video mipmaping.
 *
 * Reference:
 * [1] Pencil vs Camera. http://www.duruofei.com/Research/pencilvscamera
 *
 * Forked or related:
 * [1] Pol's Photoshop Blends Branchless: https://www.shadertoy.com/view/Md3GzX
 * [2] Gaussian Blur: https://www.shadertoy.com/view/ltBXRh
 * [3] williammalo2's Blur with only one pixel read: https://www.shadertoy.com/view/XtGGzz
 * [3] demofox's greyscale: https://www.shadertoy.com/view/XdXSzX
 * [4] iq's Postprocessing: https://www.shadertoy.com/view/4dfGzn
 * [5] related blur: https://www.shadertoy.com/view/XsVBDR
 *
 * Related masterpieces:
 * [1] flockaroo's Notebook Drawings: https://www.shadertoy.com/view/XtVGD1
 * [2] HLorenzi's Hand-drawn sketch: https://www.shadertoy.com/view/MsSGD1
 **/
const float PI = 3.1415926536;
const float PI2 = PI * 2.0;
const int mSize = 9;
const int kSize = (mSize-1)/2;
const float sigma = 3.0;
float kernel[mSize];

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float u_scale;//settings dot scale
uniform vec2 u_screenSize;//width, height
uniform vec2 u_mouse;
uniform float x_time;

// Gaussian PDF
float normpdf(in float x, in float sigma)
{
    return 0.39894 * exp(-0.5 * x * x / (sigma * sigma)) / sigma;
}

//
vec3 colorDodge(in vec3 src, in vec3 dst)
{
    return step(0.0, dst) * mix(min(vec3(1.0), dst/ (1.0 - src)), vec3(1.0), step(1.0, src));
}

float greyScale(in vec3 col)
{
    return dot(col, vec3(0.3, 0.59, 0.11));
    //return dot(col, vec3(0.2126, 0.7152, 0.0722)); //sRGB
}

vec2 random(vec2 p){
    p = fract(p * (vec2(314.159, 314.265)));
    p += dot(p, p.yx + 17.17);
    return fract((p.xx + p.yx) * p.xy);
}

void main()
{
    vec2 q = gl_FragCoord.xy / u_screenSize.xy;
    vec3 col = texture(u_texture, q).rgb;

    vec2 r = random(q);
    r.x *= PI2;
    vec2 cr = vec2(sin(r.x),cos(r.x))*sqrt(r.y);

    vec3 blurred = texture(u_texture, q + cr * (vec2(mSize) / u_screenSize.xy) ).rgb;

    vec3 inv = vec3(1.0) - blurred;
    // color dodge
    vec3 lighten = colorDodge(col, inv);
    // grey scale
    vec3 res = vec3(greyScale(lighten));

    // more contrast
    res = vec3(pow(res.x, 3.0));
    //res = clamp(res * 0.7 + 0.3 * res * res * 1.2, 0.0, 1.0);

    gl_FragColor = vec4(res, 1.0);
}