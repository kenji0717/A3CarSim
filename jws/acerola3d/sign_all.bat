echo off

echo.
echo Enter Path of the keystore.
echo if you use default keystore, press enter.
set /p KEYSTORE="path: "
if "%KEYSTORE%" == "" set KEYSTORE=%USERPROFILE%\.keystore

echo.
echo Enter key alias for sign.
set /p KEYALIAS="key: "

echo.
echo Enter the Password of the keystore.
set /p STOREPASS="password: "

echo.
echo Enter the Password of the key.
set /p KEYPASS="password: "

rem echo.
rem echo KEYSTORE : %KEYSTORE%
rem echo KEYALIAS : %KEYALIAS%
rem echo STOREPASS: %STOREPASS%
rem echo KEYPASS  : %KEYPASS%

echo.
for /R %%J in (*.jar) do (
    echo jarsigner %%J
    jarsigner -keystore "%KEYSTORE%" -storepass %STOREPASS% -keypass %KEYPASS% "%%J" %KEYALIAS%
)

pause
echo on
