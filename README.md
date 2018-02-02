# MVP Template

Архетип для старта новых проектов на основе MVP и трёхуровневой архитектуры. 

Шаблон подходит для проектов, где требуется тестирование или долгосрочная поддержка. 

1. **Three-tier architecture**:
    * [Википедия](https://en.wikipedia.org/wiki/Multitier_architecture#Three-tier_architecture)
    * [Мартин Фаулер](https://martinfowler.com/bliki/PresentationDomainDataLayering.html)
    * [Android-CleanArchitecture](https://github.com/android10/Android-CleanArchitecture)
1. [**MVP** (MV*)](https://ru.wikipedia.org/wiki/Model-View-Presenter): 
    * MVP-библиотека [Moxy](https://github.com/Arello-Mobile/Moxy) 
    * Библиотека для навигации [Cicerone](https://github.com/terrakok/Cicerone)

* Пример использования шаблона в проекте [MVP Example](https://gitlab.noveogroup.com/android-knowledge-base/mvp-example/tree/kotlin)
* Тренинг по использованию шаблона [MVP Training](https://gitlab.noveogroup.com/trainings/Android-Training-MVP)
 
# Code Style & Analysis
 
1. [Noveo Android Check](https://github.com/noveogroup/android-check) Gradle plugin
    * Checkstyle: `app/analyzers/checkstyle.xml`
    * PMD: `app/analyzers/pmd.xml`
1. Lint настроен прямо в `app/build.gradle`
1. Android Studio Inspections
    * В проект намеренно добавлены файлы в `.idea` с модифицированными настройками инспектора
 
# Cтруктура пакетов

### *data*

API обертки над источниками хранения/получения данных. 

Типовые источники данных:
* Backend API
* Database DAO
* SD карта / Файловая система 
* Shared Preferences

### *domain*

Бизнес-логика приложения.

* `domain.interactor` — высокоуровневое API для работы с данными проекта.
    * `ScreenStateDiffHelper` — объект для оптимизации общения между Toolbar / SideMenu / Activity и текущим Fragment'ом.
    * `ScreenInteractor` — логика изменения Toolbar / SideMenu / Activity и текущего Fragment'а. Привязан к `@ActivityScope`. 
* `domain.navigation` — высокоуровневое API навигации, обертка над Cicerone.
* `domain.common` — типовые шаблоны
    * `Converter` — DTO модели и конвертеры BO <--> DTO.

### *presentation*

UI и логика отрисовки/запросов/реакций. 

Каждый пакет отражает `Activity`, за исключением `common` где собраны общие классы.
* `presentation.common`:
    * `presentation.common.android` — базовые классы Android SDK.
    * `presentation.common.mvp` — инициализация Moxy и базовых презентеров.
    * `presentation.common.navigation` — инициализация Cicerone и базовых навигаторов.
* `presentation.main`, `presentation.splash` — MainActivity, SplashActivity и их фрагменты. 

# Delegates

> presentation.common.mvp.delegate.*
> presentation.common.mvp.view.*

Функционал `Activity` и `Fragment` очень похож. Чтобы избежать дублирования кода используется композиция.

* *KeyboardDelegate* — добавляет методы открытия и скрытия клавиатуры.
* *LoadingDelegate* — добавляет ViewHolder с маской и прогрессбаром, для примитивной блокировки экрана на время асинхронных операций.
    
    Тесно связан с `@LayoutWrapper`
* *DialogDelegate* — хранит единственный экземпляр диалога. 

    Закрывает старый перед открытием нового (чтобы избежать WindowLeakedException).
* *ToastDelegate* — хранит единственный экземпляр тоста. 

    Уничтожает его перед показом следующего (чтобы тосты не залипали на экране на час).
* *ToastDebugDelegate* — Toast'ы, показываемые только в `debuggable` сборках. 

    Показывает Toast’ы и пишет в лог указанное сообщение. 
    
    в релизных конфигурациях не будет показывать Toast’ы, но будет писать логи. 
    
    Отлично подойдет для отладки и демонстрационных версий.
    
Каждому делегату соответствует `MvpView`. Реализация каждого метода уже есть в базовых классах. Отнаследовав вашу `MvpView` от одной из делегатных — каждый из этих методов автоматически будет доступен в `Presenter`'е.

# Helper'ы и Util'иты

### Больше *RxJava*
 
`RxHelper` - расширение функционала `CompositeDisposable`

* Хранение подписка и отписка
* Хранение нескольких подписок по тэгу 
    + Подписки складываются в список и отписываются сразу группой
    + Можно не заводить несколько `CompositeDisposable`
* Проверка активности подписки
* `WeakReference`, добавленные подписки не вызовут утечек памяти

`RxDeclarations` - решает проблемы MissingBackpressure
* Удобно использовать вместе с `Subject` / `Relay`

`RxInfiniteDeclarations` - сериализация в бесконечный Observable/Flowable
* Никогда не завершится ошибкой
* Удобно при работе с `Subject` / `Relay`

Важно! В проекте используется [RxRelay](https://github.com/JakeWharton/RxRelay) вместо Subject'ов.

> см. пример [OnePurposeInteractor](https://gitlab.noveogroup.com/android-knowledge-base/mvp-example/blob/kotlin/app/src/main/java/com/noveogroup/mvp/domain/interactor/OnePurposeInteractor.kt)
> см. пример [DataExamplePresenter](https://gitlab.noveogroup.com/android-knowledge-base/mvp-example/blob/kotlin/app/src/main/java/com/noveogroup/mvp/presentation/main/page/example/DataExamplePresenter.kt)

### *AndroidDeclarations*, *ColorDeclarations*

* Типовые методы по работе с `Activity`, `Intent`, `Fragment`, `View`, `Resources` в Android SDK.
* Полезные методы для окрашивания `Drawable`. 

бонус: *DebugDeclarations* и *Delegates*

### *Converter*  

Объект, содержащий логику конвертирования объектов и коллекций, необходимо описать хотя бы одно направление конвертирования. 

### *@Layout* + *@LayoutWrapper* 

* `@Layout` — для установки `R.layout.*` через аннотацию.
* `@LayoutWrapper` — установка типового экрана, с маской, прогрессом, версткой для ошибки, пустого или нормального контента. Использует `R.layut.wrapper_progress_with_mask.xml` в качестве корневой View. 

# Module *noveodebugdrawer* 

[*DebugDrawer*](https://github.com/palaima/DebugDrawer) + [NoveoDebugDrawer](https://github.com/noveogroup/noveo-drawer)

1. `GradleModule` - build commit information
2. `SelectorModule` - select endpoint example
3. `BuildModule` - build versions & package
4. `DeviceModule` - current device info
5. `ScalpelModule` - [Scalpel](https://github.com/JakeWharton/scalpel)
6. `EnablerModule` - enable inspection tools [Stetho](https://github.com/facebook/stetho) and [LeakCanary](https://github.com/square/leakcanary)
7. `SettingsModule` - intents to settings screens
