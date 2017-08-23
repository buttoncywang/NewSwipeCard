net start w32time
w32tm /config /update /manualpeerlist:"192.168.78.8"
w32tm /resync
pause