package com.botforuni.handlers;

import com.botforuni.keybords.Keyboards;
import com.botforuni.domain.*;
import com.botforuni.services.*;
import com.botforuni.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Slf4j
@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {
    @Autowired
    private TelegramUserService telegramUserService;
    @Autowired
    private StatementService statementService;
    @Autowired
    private StatementCacheService statementCacheService;
    @Autowired
    private SendMessageService sendMessageService;
    @Autowired
    private StatementInfoService statementInfoService;

    @Override
    public void choose(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        Long telegramId = message.getChatId();
        String callbackData = callbackQuery.getData();

        log.info("Обробка callback запиту від користувача з ID: {}, callbackData: {}", telegramId, callbackData);

        TelegramUserCache telegramUserCache = telegramUserService.getOrGenerate(telegramId);

        switch (callbackData) {
            case "/menu" -> {
                log.info("Користувач з ID: {} вибрав меню", telegramId);
                sendMessageService.sendMessage(telegramId, Constants.MENU, Keyboards.menuKeyboard());
            }
            case "choose_statement" -> {
                log.info("Користувач з ID: {} вибрав пункт 'Вибрати заяву'", telegramId);
                sendMessageService.sendMessage(telegramId, Constants.CHOOSESTATEMENT, Keyboards.chooseStatementKeyboard());
            }
            case "statements" -> {
                log.info("Користувач з ID: {} вибрав перегляд своїх заяв", telegramId);
                List<Statement> statements = statementService.getAllUserStatements(telegramUserCache.getTelegramId());

                if (statements.isEmpty()) {
                    log.info("У користувача з ID: {} немає жодної зареєстрованої заявки", telegramId);
                    sendMessageService.sendMessage(telegramId, "У вас немає жодної зареєстрованої заявки", Keyboards.linkToMenuKeyboard());
                } else {
                    log.info("У користувача з ID: {} знайдено {} заяв(и)", telegramId, statements.size());
                    Statement lastStatement = statements.remove(0);
                    statements.forEach(statement -> sendMessageService.sendMessage(telegramId, statement.toString()));
                    sendMessageService.sendMessage(telegramId, lastStatement.toString(), Keyboards.linkToMenuKeyboard());
                }
            }
            case "statementForMilitaryOfficer" -> {
                log.info("Користувач з ID: {} вибрав пункт 'Заява для військової кафедри'", telegramId);
                StatementCache statementCache = statementCacheService.generateStatement(telegramUserCache, Constants.STATEMENTFORMILITARI);

                telegramUserCache.setPosition(Position.INPUT_USER_NAME);
                telegramUserCache.setStatementCache(statementCache);
                telegramUserService.save(telegramUserCache);

                sendMessageService.sendMessage(telegramId, "Введіть свій ПІБ:");
                log.debug("Збережено новий кеш заяви для військової кафедри для користувача з ID: {}", telegramId);
            }
            case "statementForStudy" -> {
                log.info("Користувач з ID: {} вибрав пункт 'Заява для навчання'", telegramId);
                StatementCache statementCache = statementCacheService.generateStatement(telegramUserCache, Constants.STATEMENTFORSTUDY);

                telegramUserCache.setPosition(Position.INPUT_USER_NAME);
                telegramUserCache.setStatementCache(statementCache);
                telegramUserService.save(telegramUserCache);

                sendMessageService.sendMessage(telegramId, "Введіть свій ПІБ:");
                log.debug("Збережено новий кеш заяви для навчання для користувача з ID: {}", telegramId);
            }
            case "confirm" -> {
                log.info("Користувач з ID: {} підтвердив реєстрацію заяви", telegramId);
                Statement statement = statementService.mapStatement(telegramUserCache.getStatementCache());
                StatementInfo statementInfo = statementInfoService.generate(statement);

                statement.setStatementInfo(statementInfo);
                statementService.save(statement);

                statementCacheService.removeAllByUserId(telegramUserCache.getTelegramId());

                telegramUserCache.setStatementCache(null);
                telegramUserCache.setPosition(Position.NONE);
                telegramUserService.save(telegramUserCache);

                sendMessageService.sendMessage(telegramId, "Реєстрація пройшла успішно❗", Keyboards.linkToMenuKeyboard());
                log.info("Заяву користувача з ID: {} успішно зареєстровано", telegramId);
            }
            case "cancel" -> {
                if (Position.CONFIRMATION == telegramUserCache.getPosition()) {
                    log.info("Користувач з ID: {} скасував реєстрацію заяви", telegramId);

                    statementCacheService.removeAllByUserId(telegramUserCache.getTelegramId());

                    telegramUserCache.setStatementCache(null);
                    telegramUserCache.setPosition(Position.NONE);
                    telegramUserService.save(telegramUserCache);

                    sendMessageService.sendMessage(telegramId, "Реєстрацію довідки скасовано", Keyboards.linkToMenuKeyboard());
                    log.info("Заява користувача з ID: {} успішно скасована", telegramId);
                } else {
                    log.warn("Користувач з ID: {} намагався скасувати реєстрацію без підтвердженої заявки", telegramId);
                }
            }
            default -> log.warn("Отримано невідомий callbackData: {} від користувача з ID: {}", callbackData, telegramId);
        }
    }
}
