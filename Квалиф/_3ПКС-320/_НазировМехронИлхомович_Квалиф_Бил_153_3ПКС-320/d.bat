@echo off
set /p website = Введите адрес сайта;
ping %website% -n 1 > testping.txt
pathping %website% > testpathping.txt
pause