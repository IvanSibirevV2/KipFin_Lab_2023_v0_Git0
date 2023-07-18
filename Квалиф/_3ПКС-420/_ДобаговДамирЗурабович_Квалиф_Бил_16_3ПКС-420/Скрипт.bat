@echo off
:loop
echo ping translit-online.ru
ping -n 1 translit-online.ru
echo.
echo ping kip.eljur.ru
ping -n 1 kip.eljur.ru
echo.
echo ping mail.rambler.ru
ping -n 1 mail.rambler.ru
echo.
echo tracert translit-online.ru
tracert -d translit-online.ru
echo.
echo tracert kip.eljur.ru
tracert -d kip.eljur.ru
echo.
echo tracert mail.rambler.ru	
tracert -d mail.rambler.ru
echo.
goto loop