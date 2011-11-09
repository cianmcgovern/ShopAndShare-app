Build Instructions for local use:
In the "jni" directory, create a directory called "gccbuild"
cd into the new directory and run "cmake ../"
Now call make in the gccbuild directory, an executable called "ShopAndStore" should be created.

Build Instrunctions for Android:
cd into the "jni" directory and run "ndk-build"
Now go back up into the projects root directory and run "ant debug", this creates an apk file called "ShopAndStore-debug.apk" in the "bin" directory.
