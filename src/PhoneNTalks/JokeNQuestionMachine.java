package PhoneNTalks;

public class JokeNQuestionMachine {
    private static Joke[] low_lvl_jokes = {new Joke("Тульский бродячий цирк, " +
            "с каждым годом все больше бродячий и все меньше цирк.", "Hahaha, mmm, it was not bad!!!"),
            new Joke("Если ты гордишься своей чистой совестью, значит у тебя плохая память.",
                    "Good joke, but you can better."),
            new Joke("В который раз убеждаюсь что женщины умеют хранить секреты. Группами. Человек по сорок.",
                    "I like it.")
    };
    private static Joke[] middle_lvl_jokes = {
            new Joke("Только первая бутылка алкоголя стоит дорого. Потом цена не имеет значения... ",
                    "Hahah, good!!!"),
            new Joke("Водка была на столько паленая, что состав был сразу написан шрифтом Брайля ",
                    "Hahahha, oh God!"),
            new Joke("Я назову собаку именем твоим... ", "Ahhaha, pls don't do it)))")
    };
    private static Joke[] high_lvl_jokes = {
            new Joke("В дожде можно спрятать слёзы, в снегопаде сопли, а в бассейне пописять.",
                    "HAhahahhahha, perfect, JIZAAA"),
            new Joke("Если вас послали на все четыре стороны — идите на юг. Там теплее.",
                    "Oh my goddd, I'll be lauphing for a week!"),
            new Joke("Каждый тупик мечтает стать лабиринтом.",
                    "Ahahahahhahahahahahahhahahahhahah, stoooppp joking!")
    };

    private static Question[] questions = {
            new Question("How are you?", "I'm good, thanks!"),
            new Question("What is ending year of Great Patriotic War?", "1945" ),
            new Question("For what Java method intern() using?", "For faster string comparing."),
            new Question("Where can I buy table?", "In IKEA, it's not advert/"),
            new Question("What is the best company of PC processors?", "Intel inc.")
    };

    public static Question getRandomQuestion(){
        int first_border = 0;
        int end_border = questions.length-1;
        int random_index = 0 + (int) (Math.random() *
                ((end_border - first_border) + 1));
        return questions[random_index];
    }

    public static Joke get_joke(int lvl) {
        int first_border = 0;
        int end_border = 2;
        int random_index = 0 + (int) (Math.random() *
                ((end_border - first_border) + 1));
        if (lvl >= 0 && lvl <= 33) {
            return low_lvl_jokes[random_index];
        }

        if (lvl > 33 && lvl <= 66) {
            return middle_lvl_jokes[random_index];
        }

        if (lvl > 66) {
            return high_lvl_jokes[random_index];
        }
        return new Joke("Default Joke", "It's not funny...");
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
