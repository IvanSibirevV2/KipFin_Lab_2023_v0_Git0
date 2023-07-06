@echo off
set /p Path = “Введите путь”
ping -t %Path%
pathping %Path%
