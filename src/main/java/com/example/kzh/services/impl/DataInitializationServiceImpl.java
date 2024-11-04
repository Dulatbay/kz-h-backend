package com.example.kzh.services.impl;

import com.example.kzh.entities.KzhModule;
import com.example.kzh.entities.Topic;
import com.example.kzh.entities.User;
import com.example.kzh.repositories.ModuleRepository;
import com.example.kzh.repositories.TopicRepository;
import com.example.kzh.repositories.UserRepository;
import com.example.kzh.services.DataInitializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DataInitializationServiceImpl implements DataInitializationService {
    private final ModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public void initializeModules() {
        var user = userRepository.findByUsername("admin")
                .orElseThrow(() -> new RuntimeException("Internal server error"));

        KzhModule module1 = createModule(
                "Ежелгі дәуір", "Древний век",
                "Бұл тарауда біз Қазақстанның ежелгі дәуірін қарастырамыз...",
                "В этой главе мы рассмотрим древний Казахстан...",
                "1", 360, 1, "Modules/1.jpg", user,
                List.of("Каменный век", "Бронзовый век", "Саки", "Сарматы"),
                List.of("Тас дәуірі", "Қола дәуірі", "Сақтар", "Сарматтар")
        );

        KzhModule module2 = createModule(
                "Түркі дәуірі", "Тюркский период",
                "Бұл тарауда біз Қазақстанның түркі дәуірін қарастырамыз...",
                "В этой главе мы перейдем к тюркскому периоду...",
                "2", 480, 2, "Modules/2.jpg", user,
                List.of("Тюркский каганат и Согдийцы", "Западнотюркский и восточнотюркский каганат", "Тюргеши и карлуки",
                        "Караханиды и каракитайцы", "Огузы, Кимаки, Кипчаки", "ВШП, города и религии", "Письменность, литература, наука"),
                List.of("Түрік қағанаты мен Соғды", "Батыс және шығыс түрік қағанаттары", "Түргештер мен қарлұқтар",
                        "Қарахандықтар мен қарақытайлар", "Оғыздар, қимақтар, қыпшақтар", "Ұлы Жібек жолы, қалалар мен діндер", "Жазу, әдебиет, ғылым")
        );

        KzhModule module3 = createModule(
                "Монғол шапқыншылығы", "Монгольское вторжение и его последствия",
                "Монғол шапқыншылығының әсерін зерттеу...",
                "Важным этапом в истории Казахстана было Монгольское вторжение...",
                "3", 550, 3, "Modules/3.jpg", user,
                List.of("Монгольское вторжение", "Золотая Орда", "Ак Орда и Ханство Абулхаира", "Сибирское ханство и Ногайская Орда",
                        "Государство Эмира Тимура и Могулистан", "Экономика и культура послемонгольского периода"),
                List.of("Моңғол шапқыншылығы", "Алтын Орда", "Ақ Орда мен Әбілқайыр хандығы", "Сібір хандығы мен Ноғай Ордасы",
                        "Әмір Темір мемлекеті және Моғолстан", "Моңғол шапқыншылығынан кейінгі экономика мен мәдениет")
        );

        KzhModule module4 = createModule(
                "Қазақ хандығы", "Казахское ханство",
                "Қазақ хандығының құрылуы мен дамуы...",
                "В этой главе мы рассмотрим Казахское ханство...",
                "1", 600, 4, "Modules/4.jpg", user,
                List.of("Формирование Казахского народа", "Образование Казахского ханства", "Ханы от Керея до Тауке", "Казахско-джунгарские войны"),
                List.of("Қазақ халқының қалыптасуы", "Қазақ хандығының құрылуы", "Керейден Тәуке ханға дейінгі хандар", "Қазақ-жоңғар соғыстары")
        );

        KzhModule module5 = createModule(
                "Қазақ халқы XVIII-XX ғасырда", "Казахский народ в XVIII-XX",
                "Бұл тарауда біз Қазақ халқының XVIII-XX ғғ...",
                "В этой главе мы рассмотрим историю Казахского народа с XVIII по XX век...",
                "5", 720, 5, "Modules/5.jpg", user,
                List.of("Присоединение к Российской империи", "Ханство Абылая. Культура 18 века", "Восстания Е. Пугачева и С. Датулы"),
                List.of("Ресей империясына қосылу", "Абылай хандығы. XVIII ғасыр мәдениеті", "Е. Пугачев пен С. Датұлы көтерілістері")
        );

        KzhModule module6 = createModule(
                "Тәуелсіз Қазақстан", "Независимость",
                "Тәуелсіз Қазақстан тарихы...",
                "В этой главе мы рассмотрим историю независимости Казахстана...",
                "1", 660, 6, "Modules/6.jpeg", user,
                List.of("Независимый Казахстан"),
                List.of("Тәуелсіз Қазақстан")
        );

        moduleRepository.saveAll(List.of(module1, module2, module3, module4, module5, module6));
    }

    private KzhModule createModule(String titleKaz, String titleRu, String descriptionKaz, String descriptionRu,
                                   String level, int hours, int number, String imageUrl, User user,
                                   List<String> topicTitlesRu, List<String> topicTitlesKaz) {
        KzhModule module = new KzhModule()
                .setTitleKaz(titleKaz)
                .setTitleRu(titleRu)
                .setDescriptionKaz(descriptionKaz)
                .setDescriptionRu(descriptionRu)
                .setLevel(level)
                .setHours(hours)
                .setNumber(number)
                .setImageUrl(imageUrl)
                .setUser(user);

        module = moduleRepository.save(module);

        Set<Topic> topics = new HashSet<>();
        for (int i = 0; i < topicTitlesRu.size(); i++) {
            Topic topic = new Topic()
                    .setTitleKaz(topicTitlesKaz.get(i))
                    .setTitleRu(topicTitlesRu.get(i))
                    .setNumber(i + 1)
                    .setKzhModule(module)
                    .setUser(user);
            topics.add(topicRepository.save(topic));
        }
        module.setTopics(topics);
        return moduleRepository.save(module);
    }
}

