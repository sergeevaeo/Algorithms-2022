package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    //T = O(n) - трудоемкость
    //R = O(n) - ресурсоемкость
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws IOException {
        int[] a = Files.lines(Paths.get(inputName)).mapToInt(Integer::parseInt).toArray();
        int minI = 0;
        int myMinI = 0;
        int maxI = 0;
        int profit = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[minI]) {
                minI = i;
            } else {
                if (a[i] - a[minI] > profit) {
                    profit = a[i] - a[minI];
                    maxI = i;
                    myMinI = minI;

                }
            }
        }
        return new Pair<>(myMinI + 1, maxI +1);
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    //T = O(first.length * second.length) - трудоемкость
    //R = O(1) - ресурсоемкость
    static public String longestCommonSubstring(String first, String second) {
        if (first == null || second == null ||
                first.length() == 0 || second.length() == 0) return "";
        if (first.equals(second)) return first;
        int [] [] table = new int[first.length()][];
        int length = 0;
        int I = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = new int [second.length()];
            for (int j = 0; j < table[i].length; j ++ ) {
                table[i][j] = 0;
                if(first.charAt(i) == second.charAt(j)) {
                    if(i != 0 && j != 0) {
                        table[i][j] = table[i - 1][j - 1] + 1;
                    } else {
                        table[i][j] = 1;
                    }
                    if (table[i][j] > length) {
                        length = table[i][j];
                        I = i;
                    }
                }
            }
        }
        return first.substring(I - length + 1, I + 1);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    //T = O(nlog(log(n)) - трудоемкость(решето Эратосфена)
    //R = O(1) - ресурсоемкость
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1) return 0;
        int[] primes = new int[limit + 1];
        Arrays.fill(primes, 1);
        primes[0] = 0;
        primes[1] = 0;
        int k = 0;
        for (int i = 2; i < primes.length; i++) {
            if (primes[i] == 1) {
                for (int j = 2; i * j < primes.length; j++){
                    primes[i * j] = 0;
                }
                k++;
            }
        }
        return k;
    }
}

