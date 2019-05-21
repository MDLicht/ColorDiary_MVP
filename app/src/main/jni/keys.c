#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_mdlicht_zb_colordiarymvp_common_App_getNativeKey1(JNIEnv *env, jobject instance) {
    return (*env)->  NewStringUTF(env, "Color_Diary@#qwe");
}

JNIEXPORT jstring JNICALL
Java_com_mdlicht_zb_colordiarymvp_common_App_getNativeKey2(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "1528095873216000");
}