//
// Created by mxc on 2016/11/4.
//

#include <jni.h>
#include <com_skymxc_lesson_41_jni_demo_TestJni.h>
#include <stdio.h>

JNIEXPORT jint JNICALL Java_com_skymxc_lesson_141_1jni_1demo_TestJni_jniAdd
        (JNIEnv *env, jobject jo, jint num1, jint num2){

    return  num1+num2;

}


