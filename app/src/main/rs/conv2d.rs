#pragma version(1)
#pragma rs java_package_name(com.example.innocentevil.hellors)


rs_allocation gIn;

float* kmatrix;
uint32_t ksize;
float kdiv;
bool normal;

static uint32_t width;
static uint32_t height;

void root(const uchar4* in, uchar4* out, uint32_t x, uint32_t y)
{
    if(x < 1 || y < 1)
        return;
    if((x > width - 1) || (y > height - 1))
        return;
    uint8_t kx,ky;
    float4 temp = 0;
    const uchar4* kin = in - (ksize / 2) - (ksize / 2) * width;
    for(kx = 0; kx < ksize; kx++)
    {
        for(ky = 0; ky < ksize;ky++)
        {
            temp += rsUnpackColor8888(kin[kx + ksize * ky]) * kmatrix[kx + ksize * ky];
        }
    }
    if(normal) { temp /= kdiv; }
    temp.a = 1.0f;
    *out = rsPackColorTo8888(temp);
}

void init()
{

}

void setup()
{
    width = rsAllocationGetDimX(gIn);
    height = rsAllocationGetDimY(gIn);
    rsDebug("KSize : ",ksize);
}