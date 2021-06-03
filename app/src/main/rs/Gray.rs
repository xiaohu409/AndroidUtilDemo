#pragma version(1)
#pragma rs java_package_name(com.github.xiaohu409.androidutildemo.rs)

void root(const uchar4 *in, uchar4 *out, uint32_t x, uint32_t y) {
    // a 是透明度，这里不修改透明度。
    out->a = in->a;

    // 快，但并不是真正意义的去色
    out->r = out->g = out->b = (in->r + in->g + in->b) / 3;

    // 慢，但是是真正的去色
    // out->r = out->g = out->b = (in->r * 299 + in->g * 587 + in->b * 114 + 500) / 1000;
}

void init() {
}