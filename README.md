Для создания и запуска базы данных, нужно запустить файл Docker-compose. Он создаст удаленную бд PostgreSQl.
После чего в файле application.properties нужно заменить строчку
spring.jpa.hibernate.ddl-auto=update
на 
spring.jpa.hibernate.ddl-auto=create

1. Получать информацию о каждой расходной операции в тенге (KZT), рублях (RUB)
и других валютах в реальном времени и сохранять ее в своей собственной базе
данных (БД)
<br>
URL: POST http://localhost:8080/transactions
<img width="1440" alt="Снимок экрана 2024-03-29 в 14 44 49" src="https://github.com/yaroslav775507/Redickh_IDF/assets/103926398/b2d55d96-e651-4f09-8b0d-7475e4c67d7b">
<br>
{
    "accountFrom": 12345,
    "accountTo": 987654,
    "currencyShortName": "USD",
    "sum": 100.50,
    "expenseCategory": "product"
}
<br>
<br>
2. Хранить месячный лимит по расходам в долларах США (USD) раздельно для двух
категорий расходов: товаров и услуг. Если не установлен, принимать лимит равным
1000 USD;
<br>
<br>
URL: POST http://localhost:8080/limit
<img width="1440" alt="Снимок экрана 2024-03-29 в 14 40 58" src="https://github.com/yaroslav775507/Redickh_IDF/assets/103926398/5bb74f54-b001-472d-85e2-fc01b2e818c7">
<br>

{
    "category": "products",
    "amount": 1500.00,
    "currency": "USD",
    "startDate": "2024-04-01"
}
<br>
<br>
3. Запрашивать данные биржевых курсов валютных пар KZT/USD, RUB/USD по
дневному интервалу (1day/daily) и хранить их в собственной базе данных. При
расчете курсов использовать данные закрытия (close). В случае, если таковые
недоступны на текущий день (выходной или праздничный день), то использовать
данные последнего закрытия (previous_close);
<br>
<br>
<img width="1440" alt="Снимок экрана 2024-03-29 в 14 57 27" src="https://github.com/yaroslav775507/Redickh_IDF/assets/103926398/1d53901e-52f8-426c-8834-80bb8440a1da">
<br>
<br>
@Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(fixedRate = 5000)
  
    public void updateExchangeRate() {
        String pairs = "USDRUB";
        String exchangeRateResponse = getExchangeRate(pairs);
        try {
            double rate = parseExchangeRateFromJson(exchangeRateResponse);
            Currency currency = new Currency();
            currency.setCurrencyPair(pairs);
            currency.setSum(rate);
            currency.setDate(LocalDateTime.now());
            exchangeRepository.save(currency);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Currency saveCurrency(String pair) {
        String exchangeRateResponse = getExchangeRate(pair);
        try {
            double rate = parseExchangeRateFromJson(exchangeRateResponse);
            Currency currency = new Currency();
            currency.setCurrencyPair(pair);
            currency.setSum(rate);
            currency.setDate(LocalDateTime.now());
            return exchangeRepository.save(currency);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private double parseExchangeRateFromJson(String exchangeRateResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(exchangeRateResponse);
            JsonNode dataNode = rootNode.get("data");
            JsonNode usdRubNode = dataNode.get("USDRUB");
            String rateAsString = usdRubNode.asText();
            return Double.parseDouble(rateAsString);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error parsing JSON response: " + e.getMessage());
        }
    }
-----------------------------------------

`CurrencyExchangeService`

1. **Метод `getExchangeRate(String pairs)`:**
   - **Описание:** Этот метод отправляет запрос на внешний API для получения текущего курса обмена валюты для заданной пары.
   - **Параметры:**
     - `pairs` (String): Пара валют, для которой нужно получить курс обмена (например, "USDRUB").
   - **Возвращаемое значение:** Строка с ответом от API, содержащая курс обмена валют.

2. **Метод `updateExchangeRate()`:**
   - **Описание:** Этот метод запускается по расписанию и обновляет курс обмена валюты "USDRUB" в базе данных.
   - **Расписание:** Каждый день в полночь (cron expression: "0 0 0 * * ?").
   - **Действия:**
     - Отправляет запрос на внешний API для получения текущего курса обмена валюты.
     - Парсит ответ и сохраняет курс обмена в базе данных.

3. **Метод `saveCurrency(String pair)`:**
   - **Описание:** Этот метод сохраняет текущий курс обмена валюты для указанной пары в базе данных.
   - **Параметры:**
     - `pair` (String): Пара валют, для которой нужно сохранить курс обмена (например, "USDRUB").
   - **Возвращаемое значение:** Объект `Currency`, представляющий сохраненную запись о курсе обмена в базе данных.

4. **Метод `parseExchangeRateFromJson(String exchangeRateResponse)`:**
   - **Описание:** Этот метод парсит JSON-ответ от внешнего API, чтобы извлечь курс обмена валюты.
   - **Параметры:**
     - `exchangeRateResponse` (String): JSON-ответ от внешнего API, содержащий курс обмена валют.
   - **Возвращаемое значение:** Курс обмена валюты в формате double.
   - **Исключения:** Если происходит ошибка при парсинге JSON-ответа, выбрасывается исключение `IllegalArgumentException`.
------------------------
`ExchangeRateService`

1. **Метод `saveExchange(ExchangeRate rate)`:**
   - **Описание:** Этот метод сохраняет объект `ExchangeRate` в базе данных.
   - **Параметры:**
     - `rate` (ExchangeRate): Объект `ExchangeRate`, который нужно сохранить в базе данных.
   - **Возвращаемое значение:** Сохраненный объект `ExchangeRate`.
   - **Действия:** Метод использует репозиторий `ExchangeRateRepository` для сохранения объекта `ExchangeRate` в базе данных.
------------------------
`MonthlyLimitService`

1. **Метод `saveMonthLimit(MonthlyLimit limit)`:**
   - **Описание:** Этот метод сохраняет объект `MonthlyLimit` в базе данных.
   - **Параметры:**
     - `limit` (MonthlyLimit): Объект `MonthlyLimit`, который нужно сохранить в базе данных.
   - **Возвращаемое значение:** Сохраненный объект `MonthlyLimit`.
   - **Действия:** Метод использует репозиторий `MonthlyLimitRepository` для сохранения объекта `MonthlyLimit` в базе данных.

2. **Метод `changeLimit(Long userId, Double newLimit)`:**
   - **Описание:** Этот метод изменяет лимит для указанного пользователя. Если лимит для пользователя существует, он обновляется с новым значением. Если лимита для пользователя не существует, создается новый лимит с указанным значением.
   - **Параметры:**
     - `userId` (Long): Идентификатор пользователя.
     - `newLimit` (Double): Новое значение лимита для пользователя.
   - **Возвращаемое значение:** Объект `MonthlyLimit`, представляющий обновленный или созданный лимит.
   - **Действия:** Метод сначала проверяет существует ли лимит для указанного пользователя. Если лимит существует, он обновляется новым значением лимита. Если лимита нет, создается новый лимит для указанного пользователя с новым значением лимита.
------------------------
`TransactionService`

1. **Метод `saveTransaction(Transaction transaction)`:**
   - **Описание:** Этот метод сохраняет транзакцию в базе данных.
   - **Параметры:**
     - `transaction` (Transaction): Объект `Transaction`, который нужно сохранить в базе данных.
   - **Возвращаемое значение:** Сохраненный объект `Transaction`.
   - **Действия:** Метод использует репозиторий `TransactionRepository` для сохранения объекта `Transaction` в базе данных.


  



