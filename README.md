**Это было приключение на 20 минут.
**

Все объекты-задачи созданы в одном классе Task.
Поля:
Статус: NEW/IN_PROGRESS/DONE;
Тип: REGULAR (Обычная задача)/ EPIC (Эпик) / SUBTASK (Подзадача)
Содержимое: сама задача в типе данных String.
Поле ArrayList с объектами Task для хранения id подзадач в эпик-задачах. Инициализируется только если в конструктор подаётся тип Эпик.

Поскольку в целом содержимое и поля обычной задачи и сабтаска не отличаются, решил не плодить дополнительные классы (потом немного аукнулось, но не сильно).

Каждой задаче независимо от типа присваевается уникальный ID при создании. Задачи типа Обычная/Эпик создаются в статическом методе класса TaskManager, сабтаск создаётся в идентичном методе объекта задачи Эпик. Вся разница только в том, что при создании сабтаска его id записывается в ArrayList объекта-задачи Эпик.

Все задачи хранятся в единственном созданном объекте master, вся его цель - хранить две хэш-таблицы,  keyList хранит привязку id - hash, dataBase хранит привязку hash - объект. Сам TaskManager использует статические методы для взаимодействия класса main и операций с табличками.

Equals и хэшкод переопределены, две копии одинаковых задач создать не получится (исключение - если содержание задач разных типов, но на то они и разные).

Консольный интерфейс добавлен для тестирования и отладки.


