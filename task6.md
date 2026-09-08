================

На более ранних этапах ты уже заложил(а) архитектуру, сделал(а) CLI-интерфейс, настроил(а) фоновые асинхронные задачи (ExecutorService) с graceful shutdown и перевел(а) приложение на Spring Boot.

Подключаем БД

В рамках текущей домашки №11 вместо сохранения структуры данных в памяти будем сохранять в БД.

В какую именно — на ваше усмотрение. Дальнейшее описание будет подразумевать использование PostgreSQL.

Что необходимо

1. Настроить подключение к БД, указав параметры подключения в aplication.properties или application.yaml файле.

2. Добавить инициализацию БД при старте. Скрипт schema.sql с описанием схемы данных (создание таблиц). Подробнее

3. Создать 2 репозитория (аннотация @Repository ) для юзеров и вакансий.

4. Использовать там JdbcTemplate или NamedParameterJdbcTemplate и реализовать запросы по вставке и чтению данных из БД.
   Что потребуется

Как минимум нужна зависимость на драйверы для работы с БД

implementation 'org.postgresql:postgresql:42.7.3'

Для работы с базой из кода используем SpringJDBC .

implementation 'org.springframework.boot:spring-boot-starter-jdbc'

Также нужна сама БД. Если ее нет и устанавливать себе на ноут или компьютер не хочется, можно поднять ее в докере. Или использовать какую-нибудь in-memory базу данных, например H2.
Примеры

Пример того, как может выглядеть ваш репозиторий:

@Repository
public class UserRepository {
private final NamedParameterJdbcTemplate jdbcTemplate;   
private final RowMapper<User> userRowMapper = (rs, rowNum) -> new Use  UUID.fromString(rs.getString("id")),
rs.getString("name"),
rs.getObject("created", LocalDate.class)
);

public User getById(String userId) {
return jdbcTemplate.queryForObject("""  SELECT *
FROM USER
WHERE id = :userId""",
Map.of("userId", userId), userRowMapper  );
}
}

Примеры использования Spring JDBC
Про NamedParameterJdbcTemplate
