#pragma version(1)
#pragma rs java_package_name(com.example.innocentevil.hellors)


void init()
{

}

void root(const uchar4 *in, uchar4 *out, uint32_t x,uint32_t y)
{
    out->r = 0xff - in->r;
    out->g = 0xff - in->g;
    out->b = 0xff - in->b;
}
