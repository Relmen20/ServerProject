# ServerProject

Приложение запускается через команду `sh serverScript.sh`, в папке проекта создаются папки out и data.

При запуске, в консоль может выводиться ошибка `srvSocket Exception Address already in use (Bind failed)` это означает что необходимо изменить порт.

Порт изменяется в файле `serverScript.sh` на 8 строчке `java -cp ./out/ Main <NEW_PORT>`.
