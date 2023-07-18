@echo off

set /p website=Введите адрес сайта: 

echo Выполняется ping для %website%...
ping %website%

echo.
echo Выполняется pathping для %website%...
pathping %website%

pause