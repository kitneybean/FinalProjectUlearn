###Кузьмин Михаил Дмитриевич РИ-311003

# ___Parser___ 
## Парсинг .csv и преобразование в .db

В данном коде создается Java-приложение для парсинга CSV файла и загрузки данных в базу данных SQLite. Вот что я сделал в этом куске кода:

* Организация подключения к базе данных: В блоке main устанавливается соединение с базой данных SQLite с использованием JDBC.

* Создание таблиц: В методе createTables создаются две таблицы - Schools и School_stat, с определенными столбцами для каждой таблицы.

* Парсинг CSV файла и вставка данных в базу данных: В методе parseCsvAndInsertData используется библиотека opencsv для чтения данных из CSV файла. Затем данные вставляются в две таблицы - Schools и School_stat с использованием SQL-запросов.

* Для Schools вставляются данные о school_id, district, school, и county.

* Для School_stat вставляются данные о school_id, grades, students, teachers, calworks, lunch, computer, expenditure, income, english, read, и math.

* Обработка исключений: Для обработки возможных ошибок используется блок try-catch, который выводит стек трейс ошибки в случае исключения.

* Вывод в консоль сообщения об успешной загрузке данных: После успешного завершения операций, выводится сообщение "Данные успешно загружены в базу данных." в консоль.

# ___Chart___
## Задание 1: Постройте график по среднему количеству студентов, в 10 различных странах, взять на свой выбор.

В данном коде я создал приложение на Java с использованием библиотеки JFreeChart для построения графика среднего количества студентов в выбранных странах на основе данных из базы данных SQLite. Все действия, включая подключение к базе данных, выполнены внутри кода.

Я выбрал следующие страны для анализа: Alameda, Butte, Fresno, San Joaquin, Kern, Tulare, Monterey, Sacramento, Los Angeles, Ventura.

Шаги выполняемые кодом:

* Подключение к базе данных: Используется класс DriverManager для установления соединения с базой данных SQLite.

* Получение среднего количества студентов для каждой страны: В методе createDataset выполняется цикл по выбранным странам. Для каждой страны формируется SQL-запрос, который объединяет таблицы schools и school_stat по идентификатору школы (school_id). Затем используется PreparedStatement для выполнения запроса, и результат (среднее количество студентов) добавляется в датасет для графика.

* Построение графика: Используется библиотека JFreeChart для создания столбчатого графика, отображающего среднее количество студентов для каждой выбранной страны. График вставляется в окно, которое выводится на экран.

* Завершение работы приложения: Приложение закрывается при закрытии окна с графиком.

Мною были оставлены комментарии в коде, которые помогут понять каждый шаг.

![image](https://github.com/kitneybean/FinalProjectUlearn/blob/master/img/Chart.png)

# ___Average Expenditure___
## Задание 2: Выведите в консоль среднее количество расходов(expenditure) в Fresno, Contra Costa, El Dorado и Glenn, у которых расход больше 10.

В этом задании я создал Java-приложение для работы с базой данных SQLite. Шаги, которые я предпринял:

* Подключение к базе данных: Я использовал DriverManager.getConnection для подключения к базе данных SQLite с помощью JDBC. Подключение происходит к файлу test.db.

* Вызов метода getAverageExpenditureByCounty для нескольких округов: В методе main я вызываю метод getAverageExpenditureByCounty для: "Fresno", "Contra Costa", "El Dorado", "Glenn".

* Метод getAverageExpenditureByCounty: Этот метод формирует SQL-запрос для получения среднего количества расходов в указанном округе, где расходы больше 10. Я использую подготовленное выражение с параметром, чтобы избежать SQL-инъекций.

* Вывод результата в консоль: Полученные результаты, в данном случае, среднее количество расходов, выводятся в консоль для каждого округа.

* Обработка исключений: Я также предусмотрел обработку исключений для лучшей управляемости ошибок во время выполнения.

Таким образом, код позволяет получить и вывести среднее количество расходов в указанных округах из базы данных SQLite.

![image](https://github.com/kitneybean/FinalProjectUlearn/blob/master/img/AverageExpenditure.png)

# ___High Math Schools___
## Задание 3: Выведите в консоль учебное заведение, с количеством студентов равному от 5000 до 7500 и с 10000 до 11000, с самым высоким показателем по математике (math)

В данном задании я написал код на Java для работы с базой данных SQLite. Шаги, которые я сделал:

* Подключение к базе данных: В блоке main я устанавливаю соединение с базой данных SQLite с использованием JDBC. Это делается с помощью DriverManager.getConnection("jdbc:sqlite:test.db").

* Вызов метода getHighMathSchools: В блоке main дважды вызывается метод getHighMathSchools. Этот метод выполняет SQL-запрос для получения учебных заведений с указанным диапазоном количества студентов и самым высоким показателем по математике.

* Метод getHighMathSchools: Этот метод принимает соединение с базой данных (Connection connection) и два параметра (minStudents и maxStudents), представляющие диапазон количества студентов. Затем он выполняет SQL-запрос с использованием подготовленного выражения (PreparedStatement). В запросе выбираются название учебного заведения (s.school) и показатель по математике (ss.math) из таблиц Schools и School_stat соответственно.

* Вывод результатов в консоль: Если результат запроса не пустой (resultSet.next()), то извлекаются данные (название учебного заведения и показатель по математике), и они выводятся в консоль.

* Обработка исключений: Если в процессе выполнения кода происходит исключение, оно перехватывается, и информация об ошибке выводится в консоль с использованием e.printStackTrace().

Общий результат работы программы - вывод в консоль информации о учебных заведениях с наивысшим показателем по математике в указанных диапазонах количества студентов.

![image](https://github.com/kitneybean/FinalProjectUlearn/blob/master/img/HighMathSchools.png)




