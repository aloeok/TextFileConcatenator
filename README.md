# TextFileConcatenator
Способ запуска 1: Склонировать проект в IntelliJ IDE и запустить метод main класса task.concatenator.TextFileConcatenatorRunner
При запуске программы требуется указать:
  - путь (желательно полный) к директории из условия задачи
  - путь (желательно полный) к имени склеенного файла, который будет создан (на момент запуска программы путь должен быть несуществующим, чтобы программа создала новый файл, иначе программа не будет работать)
  - тип склейки файлов (1 - способ из основного задания, 2 - способ из дополнительного задания), задание со звёздочкой реализовано как часть дополнительного задания

Условия работы программы:
  - путь к имени склеенного файла является несуществующим на момент запуска программы
  - директивы внутри файлов даны строго в соответствии с примером (каждая директива на отдельной строке, на строке с директивой есть только директива)

Интерфейс - командная строка

Почти все функции и логика их работы описаны в комментариях в исходном коде программы. Основные идеи решения:
- использование FileChannel для конкатенации и в целом java.nio для работы с файлами (исключение составляет фильтрация строк отдельного файла с помощью FileInputStream и java.util.Stream<T> для поиска директив внутри файла)
- использование алгоритма топологической сортировки для эффективного решения дополнительной задачи и задачи со звёздочкой
- продуманная иерархия классов с чётким разделением реализаций решений разных задач по разным функциям или классам (что, в частности, позволяет независимо тестировать отдельные части программы, легче находить ошибки, проще писать решение конкретной подзадачи, в изоляции от других подзадач)
- какая-никакая обработка исключений и минимальная ориентированность на пользователя
