#include <jni.h>
#include <string>

extern "C"
jstring
Java_red2009_gravity_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string title = "GRΛVITY";
    return env->NewStringUTF(title.c_str());
}
