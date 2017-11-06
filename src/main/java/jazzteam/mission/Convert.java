package jazzteam.mission;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.regex.Pattern;

//    Перевод числа в цифровой записи в строковую. Например 134345 будет "сто
//тридцать четыре тысячи триста сорок пять". * Учесть склонения - разница
//в окончаниях (к примеру, две и два).
//    Алгоритм должен работать для сколько угодно большого числа, соответственно,
//значения степеней - миллион, тысяча, миллиад и т.д. - должны браться их
//справочника, к примеру, текстового файла.
//    Обязательно создать Data Driven Test (я, как пользователь, должен иметь
//возможность ввести множество наборов 1.число 2.правильный эталонный результат,
// тест самостоятельно проверяет все наборы и говорит, что неверное), который
// доказывает, что Ваш алгоритм работает правильно. Использовать JUnit.
//    По возможности, применить ООП.

public class Convert {
    private String strNum;
    private String translatedNumber;

    //Содержатся единичные и составные числа, и знак отрицательности
    private static final TreeMap<String, String> MAP_NUMBER = new TreeMap<String, String>() {{
        put("-", "минус");
        put("0", "ноль");
        put("1", "один");
        put("1_", "одна");
        put("2", "два");
        put("2_", "две");
        put("3", "три");
        put("4", "четыре");
        put("5", "пять");
        put("6", "шесть");
        put("7", "семь");
        put("8", "восемь");
        put("9", "девять");
        put("11", "одинадцать");
        put("12", "двенадцать");
        put("13", "тринадцать");
        put("14", "четырнадцать");
        put("15", "пятнадцать");
        put("16", "шеснадцать");
        put("17", "семьнадцать");
        put("18", "восемьнадцать");
        put("19", "девятнадцать");
    }};

    //Содержатся десятки, от 10 и до 90
    private static final TreeMap<String, String> MAP_DOZENS_OF = new TreeMap<String, String>() {{
        put("1","десять");
        put("2","двадцать");
        put("3","тридцать");
        put("4","сорок");
        put("5","пятьдесят");
        put("6","шестьдесят");
        put("7","семьдесят");
        put("8","восемьдесят");
        put("9","девяносто");
    }};

    //Содержатся сотни, от 100 и до 900
    private static final TreeMap<String, String> MAP_HUNDREDS = new TreeMap<String, String>() {{
        put("0","");
        put("1","сто");
        put("2","двести");
        put("3","триста");
        put("4","четыреста");
        put("5","пятьсот");
        put("6","шестьсот");
        put("7","семьсот");
        put("8","восемьсот");
        put("9","девятьсот");
    }};

    //Содержатся степени чисел, от 10^3 и до 10^96
    private static final TreeMap<String, String> MAP_CALCULUS = new TreeMap<String, String>() {{
        put("0","");
        put("1","");
        put("2","тысяч");
        put("3","миллион");
        put("4","миллиард");
        put("5","триллион");
        put("6","квадриллион");
        put("7","квинтиллион");
        put("8","секстиллион");
        put("9","септиллион");
        put("10","октиллион");
        put("11","нониллион");
        put("12","дециллион");
        put("13","андециллион");
        put("14","дуодециллион");
        put("15","тредециллион");
        put("16","кваттордециллион");
        put("17","квиндециллион");
        put("18","сексдециллион");
        put("19","септемдециллион");
        put("20","октодециллион");
        put("21","новемдециллион");
        put("22","вигинтиллион");
        put("23","анвигинтиллион");
        put("24","дуовигинтиллион");
        put("25","тревигинтиллион");
        put("26","кватторвигинтиллион");
        put("27","квинвигинтиллион");
        put("28","сексвигинтиллион");
        put("29","септемвигинтиллион");
        put("30","октовигинтиллион");
        put("31","новемвигинтиллион");
        put("32","тригинтиллион");
        put("33","антригинтиллион");

    }};

    /**
     * Метод в который передается строка для дальнейшего преобразования
     *
     * @param num - получаемая строка чисел.
     * @return строка, полученная после преобразования числа в текстовый вид
     * @throws Exception - если строка длинною более 99 символов или пустая строка,
     * или введены символы не попадающие в формат -?[0-9]+.
     */
    public String convertNumberToString(String num) throws Exception{
        if(num.length() > 99)
            throw new Exception("Введен слишком длинный аргумент. Максимальная длинна строки 99 символов.");

        if(num.length() == 0)
            throw new Exception("Вы ввели пустую строку. Преобразование невозможно.");

        if(!Pattern.compile("-?[0-9]+").matcher(num).matches())
            throw new Exception("Строка введена некорректно. Используйте только числа и символ \"-\" в начале строки.");

        translatedNumber = "";
        this.strNum = num;
        subString();
        return translatedNumber;
    }

    /**
     * Преобразование подстрок в зависимости от количества символов
     *
     * @param i - индекс степени числа
     * @param subStr - получаемая подстрока для преобразования
     */
    private void convertSubstring(int i, String subStr) {
        String[] subSubstr = new String[subStr.length()];

        //заполнение массива подстроками в 1 элемент
        for (int j = 0; j < subSubstr.length; ++j)
            subSubstr[j] = subStr.substring(j, j + 1);

        switch (subSubstr.length) {
            case 3:
                //выводит в строку значение элемента массива с индексом 0
                if (!subSubstr[0].equals("0")) {
                    translatedNumber += MAP_HUNDREDS.get(subSubstr[0]) + " ";
                    //выводит в строку степень с приставкой
                    if (subSubstr[1].equals("0") && subSubstr[2].equals("0") && i > 2)
                        translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + "ов" + " ";
                    //выводит в строку "тысяч"
                    else if(subSubstr[1].equals("0") && subSubstr[2].equals("0") && i == 2)
                        translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + " ";
                }

                //вызов метода для преобразования последних 2-х символов
                declensionTen(subSubstr, i, 2);

                //вызов метода для преобразования последнего символа
                if (!subSubstr[2].equals("0") && !subSubstr[1].equals("1"))
                    declensionOne(subSubstr[2], i);
                break;

            case 2:
                //вызов метода для преобразования массива в 2 символа
                declensionTen(subSubstr, i, 1);

                //вызов метода для преобразования последнего символа
                if (!subSubstr[1].equals("0") && !subSubstr[0].equals("1"))
                    declensionOne(subSubstr[1], i);
                break;

            case 1:
                //преобразование подстрокки длинной в 1 символ
                    declensionOne(subSubstr[0], i);
                break;
        }
    }

    /**
     * Преобразование подстроки размером в 2 символа
     *
     * @param subSubstr - получаемый массив подстрок
     * @param i - индекс степени числа
     * @param j - размер передаваемого массива
     */
    private void declensionTen(String[] subSubstr, int i, int j){
        String index;
        //выводит в строку составное число (11-19)
        if (subSubstr[j-1].equals("1") && !subSubstr[j].equals("0")) {
            index = subSubstr[j-1] + subSubstr[j];
            translatedNumber += MAP_NUMBER.get(index) + " ";
            if(i > 2)
                translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + "ов" + " ";
            else
                translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + " ";
        }

        //выводит в строку значение ячейки массива с индексом j-1(0)
        else if (!subSubstr[j-1].equals("0")) {
            translatedNumber += MAP_DOZENS_OF.get(subSubstr[j-1]) + " ";
            //добавляет степень
            if (subSubstr[j].equals("0") && i > 2)
                translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + "ов" + " ";
            else if(subSubstr[j].equals("0") && i == 2)
                translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + " ";
        }
    }

    /**
     * Преобразование подстроки в 1 символ
     *
     * @param subSubstr - получаемая подстрока
     * @param i - индекс степени числа
     */
    private void declensionOne(String subSubstr, int i){
            //выводит "один" со степенью
        if(subSubstr.equals("1") && i > 2) {
            translatedNumber += MAP_NUMBER.get("1") + " ";
            translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + " ";
        }

            //выводит в строку "один"
        else if(subSubstr.equals("1") && i < 2)
            translatedNumber += MAP_NUMBER.get("1") + " ";

            //выводит в строку склонение 1 и добавляет к слову "тысяч" оаончание "а"
        else if(subSubstr.equals("1") && i == 2){
            translatedNumber += MAP_NUMBER.get("1_") + " ";
            translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + "а" + " ";
        }

            //выводит числа от 2 до 4 в строку с окончаниями
        else if(Integer.valueOf(subSubstr) >= 2 && Integer.valueOf(subSubstr) <= 4 && i > 2) {
            translatedNumber += MAP_NUMBER.get(subSubstr) + " ";
            translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + "а" + " ";
        }

            //выводит числа от 2 до 4 в строку с окончаниями
        else if(Integer.valueOf(subSubstr) >= 2 && Integer.valueOf(subSubstr) <= 4 && i == 2){
            //т.к. 2 имеет отличное от других чисел склонение, то в строку выводится по условию "две"
            if(subSubstr.equals("2"))
                translatedNumber += MAP_NUMBER.get("2_") + " ";
            else
                translatedNumber += MAP_NUMBER.get(subSubstr) + " ";
            translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + "и" + " ";
        }

            //выводит в строку числа, начиная от 2 и до 4 без окончаний
        else if(Integer.valueOf(subSubstr) >= 2 && Integer.valueOf(subSubstr) <= 4 && i < 2)
            translatedNumber += MAP_NUMBER.get(subSubstr) + " ";

            //выводит в строку числа, начиная от 5 и выше
        else if(Integer.valueOf(subSubstr) >= 5){
            translatedNumber += MAP_NUMBER.get(subSubstr) + " ";
            //степеням, таким как миллион и дальше придает окончание "ов"
            if(i > 2)
            translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + "ов" + " ";
            else if(i == 2) translatedNumber += MAP_CALCULUS.get(String.valueOf(i)) + " ";
        }
        else translatedNumber += MAP_NUMBER.get(subSubstr) + " ";
    }

    /**
     * Проверка строки на наличие символа отрицательности
     *
     * @param lengthString - начальная длинна строки
     * @return длинну строки
     */
    private int negativity(int lengthString) {
        String negative = strNum.substring(0, 1);

        //проверка на наличие знака отрицательности
        if(negative.equals("-")) {
            translatedNumber += MAP_NUMBER.get(negative) + " ";
            strNum = strNum.substring(1, lengthString);
            --lengthString;
        }
        //возвращает длинну строки, после удаления лишних нулей
        return selectionZero();
    }

    /**
     * Проверка строки на введенные вначале нули
     */
    private int selectionZero(){
        int lengthStrNum = strNum.length();
        int quantityZero = lengthStrNum;

        //если длинна строки > 1 символа
        if(lengthStrNum > 1)
            for(int i = 0; i < quantityZero; ++i) {
                if (strNum.substring(0, 1).equals("0")) {
                    strNum = strNum.substring(1, lengthStrNum);
                    lengthStrNum = lengthStrNum - 1;
                }
                else break;
            }
            return lengthStrNum;
    }

    /**
     * Полученная строка разбивается на подстроки по 3 символа,
     * которые передаются в метод convertSubstring для преобразования
     */
    private void subString(){
        int lengthString = negativity(strNum.length());

        //создается массив, длинной равной длинне строки / 3, если имеется остаток от деления - размер массива + 1
        String[] subStr = new String[(lengthString % 3 == 0) ? (lengthString / 3) : (lengthString / 3 + 1)];

        //массив заполняется подстроками по 3 символа
        for (int i = 0; i < subStr.length; ++i) {
            subStr[i] = strNum.substring((lengthString - 3 >= 0) ? (lengthString - 3) : 0, lengthString);
            lengthString = lengthString - 3;
        }

        //передача подстрок для преобразования
        for(int i = subStr.length - 1; i >= 0; --i)
            convertSubstring(i + 1, subStr[i]);
    }

    public static void main(String[] args) {
        try {
            Convert conv = new Convert();
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Введите ваше число: ");
            System.out.print(conv.convertNumberToString(read.readLine()));
            read.close();
        }
        catch (Exception ex){
            System.out.print(ex.toString());
        }
    }
}