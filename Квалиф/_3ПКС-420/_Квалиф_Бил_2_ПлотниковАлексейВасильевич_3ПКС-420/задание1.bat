@echo off
for /f "tokens=3" %%a in ('route print ^| findstr "\<0.0.0.0\>"') do set gateway=%%a
ping %gateway%
pause
