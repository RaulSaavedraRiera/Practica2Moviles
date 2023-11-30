#include <jni.h>
#include <vector>
#include "picosha2.h"

using namespace std;
using namespace picosha2;

extern "C"
JNIEXPORT jstring JNICALL
Java_com_saavedradelariera_NDKManager_generateHash(JNIEnv *env, jclass clazz, jstring data) {
    jboolean isCopy;
    const char *convertedValue = env->GetStringUTFChars(data, &isCopy);
    string src_comp = "(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;";

    vector<unsigned char> hash(k_digest_size);
    std::string data_str(convertedValue);

    hash256(data_str.begin(), data_str.end(), hash.begin(), hash.end());
    string hex_str = bytes_to_hex_string(hash.begin(), hash.end());

    return env->NewStringUTF(hex_str.c_str());
}