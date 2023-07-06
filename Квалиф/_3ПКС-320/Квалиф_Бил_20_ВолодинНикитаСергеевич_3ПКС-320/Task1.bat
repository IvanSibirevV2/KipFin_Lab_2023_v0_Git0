@echo off
set /P Path = Введите путь ^>
ping -t %Path%
pathping %Path%



