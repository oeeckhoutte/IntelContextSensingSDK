@echo OFF

rmdir /s apk /Q
mkdir apk

echo -------------------------------------------------
echo ** DEPENDENCY PROJECT UPDATE **
echo -------------------------------------------------

call cmd /c "android update project --target android-8 --path ..\..\..\google-play-services_lib"

echo -------------------------------------------------
echo ** ANDROID PROJECT UPDATE **
echo -------------------------------------------------

call cmd /C android update project --name Intel-ContextSensingApiFlowSample --target android-18 --path .

echo -------------------------------------------------
echo ** ANT COMPILATION ON RELEASE**
echo -------------------------------------------------

call cmd /C ant clean release
if errorlevel 1 exit /b 1

move /Y .\bin\Intel-ContextSensingApiFlowSample-release-unsigned.apk .\apk\Intel-ContextSensingApiFlowSample.apk
