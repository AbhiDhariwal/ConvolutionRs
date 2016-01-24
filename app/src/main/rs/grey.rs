#pragma version(1)
#pragma rs java_package_name(com.example.innocentevil.hellors)


void root(const uchar4 *in, uchar4 *out,uint32_t x, uint32_t y)
{
    float4 color = rsUnpackColor8888(*in);
    float4 temp = 0.0;
    float av = (color.r + color.g + color.b) / 3;
    temp.r = color.r - (color.r - av) * 0.3;
    temp.g = color.g - (color.g - av) * 0.3;
    temp.b = color.b - (color.b - av) * 0.3;
    *out = rsPackColorTo8888(temp);
}

void init(){
}