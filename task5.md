2⃣ Создать main класс для запуска спринг контейнера. Например:

@SpringBootApplication

public class TestApp {

    public static void main(String[] args) {

        SpringApplication.run(TestApp.class, args);

    }

}

3⃣ Создать REST контроллеры (Аннотация @RestController) для работы с системой. UserController, JobController, StatController, SuggestController. Команду history оставить только в CLI. В контроллерах нужно реализовать методы аналоги комманд из CLI.

4⃣ Контроллеры обращаются к сервисам. У кого-то они уже есть. У кого нет, перенесите сервисную логику в эти классы. Пример:

@Service

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    public void create(CreateUserRequestDTO requestDTO) {

        User user = new User(UUID.randomUUID(), requestDTO.getUsername());

        userRepository.create(user);

    }

    ...

}
5⃣ Сервисные классы идут в репозитории за данными. Репозиторий — класс для работы с данными. Сохранять, обновлять, удалять, получать. Обычно инкапсулирует внутри работу с БД. В вашем случае это пока не БД, а данные в памяти.

@Repository

public class UserRepository {
private final Map users = new HashMap();

    public void create(User user) {
        users.put(user.getUsername(), user);
    }

    public User getByName(String name) {
        return users.get(name);
    }
}

6⃣ Сделать возможным запуск поиска "лучшего предложения" (которое работает в отдельном потоке) с помощью спринга. Подробнее про нужные спринг аннотации по ссылке: https://spring.io/guides/gs/scheduling-tasks

Возможность ручного запуска через ScheduledExecutorService (в мейн классе, который запускает CLI приложение) можно оставить, это ни на что не влияет.
7⃣ Предлагается такая структура проекта

boot/

├── domain/

├── repository/

├── service/

├── web/

└── TestApp.java

В вашем случае корневой пакет может быть не boot, а test, или что-то другое.

🔹 web или controllers для контроллеров и ДТО классов для запросов ответов. Если их много, то можно их вынести в отдельный пакет dto.
🔹 service для сервисов
🔹 repository для репозиториев
🔹 domain или model или entities для объектов вашей предметной области.

Могут быть еще пакеты, если захотите. Например validation — для классов, которые будут заниматься валидацией входных данных. Или mappers — для классов, которые будут конвертировать ДТО в domain объекты и обратно.

8⃣ CLI, который уже реализован, никуда не пропадает. Ваше приложение будет уметь работать и с CLI, и с веб запросами. Схему взаимодействия компонентов можно представить так:

┌────────────┐   ┌────────────┐

|            Controllers               |      |                  CLI                     |

└──────┬─────┘   └─────┬──────┘

                         └──────┬────────┘

                                                  │

                                   ┌───┴────┐

                                    |        Services         |

                                   └────────┘

                                                  │
    
                         ┌──────┴───────┐

                          |                Repositories                |

                         └──────────────┘