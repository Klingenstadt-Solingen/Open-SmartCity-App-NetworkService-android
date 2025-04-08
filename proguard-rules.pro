# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Fix obfuscation removing classes referenced in fields of serialized classes
-if class *
-keepclasseswithmembers class <1> {
    <init>(...);
    @com.google.gson.annotations.SerializedName <fields>;
}