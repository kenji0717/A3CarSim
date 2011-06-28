echo off

mkdir tmp

for /R %%J in (*.jar) do (
    echo unjarsigner %%J
    rmdir /q /s tmp
    mkdir tmp
    cd tmp
    jar xf "%%~fJ"
    rmdir /q /s META-INF
    del "%%~fJ"
    jar cf "%%~fJ" *
    cd ..
)

rmdir /q /s tmp

pause
echo on
