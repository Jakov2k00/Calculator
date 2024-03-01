
// Author: Jakov2k00 for Kata.Academy

import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Это - калькулятор. Он умеет вычислять самые простые выражения");
        System.out.println("Вы можете использовать либо арабские, либо римские цифры и символы: '+', '-', '*', '/'");
        System.out.println("Введите арифметическое выражение в поле ниже:");

        String result = in.nextLine();

        System.out.println(calc(result));
    }

    public static String calc(String input) {

        Converter converter = new Converter();
        String[] oper = {"+", "-", "*", "/"};
        int operIndex = -1;

        for (int i = 0; i < oper.length; i++) {
            if (input.contains(oper[i])) {
                operIndex = i;
                break;
            }
        }
        if (operIndex == -1) {
            throw new RuntimeException("Некорректный знак выражения");
        }
        String[] parts = input.split(" ");

        if (parts.length == 1) {
            throw new RuntimeException("Некорректный формат арифметической операции");
        }

        if (parts.length > 3) {
            throw new RuntimeException("Некорректный формат арифметической операции");
        }

        if (converter.isRoman(parts[0]) != converter.isRoman(parts[2])) {
            throw new RuntimeException("Вводимые числа должны быть в одном формате");
        }
        int a, b;
        boolean isRoman = converter.isRoman(parts[0]);

        if (isRoman) {
            a = converter.romanToInt(parts[0]);
            b = converter.romanToInt(parts[2]);
        } else {
            a = Integer.parseInt(parts[0]);
            b = Integer.parseInt(parts[2]);
        }

        if ((a < 1 || a > 10) || (b < 1 || b > 10)) {
            throw new RuntimeException("Числа не могут быть меньше 1 и больше 10");
        }
        int result = 0;
        String sResult = "";

        switch (oper[operIndex]) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
        }
        if (isRoman) {
            if (result <= 0) {
                throw new RuntimeException("Вы ввели выражение, равное нулю/меньше нуля. В римском калькуляторе нет данных значений");
            }
            sResult = converter.intToRoman(result);
            return sResult;
        } else {
            return Integer.toString(result);
        }
    }
}

class Converter {

    TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
    TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

    Converter() {

        romanKeyMap.put('I', 1);
        romanKeyMap.put('V', 5);
        romanKeyMap.put('X', 10);
        romanKeyMap.put('L', 50);
        romanKeyMap.put('C', 100);

        arabianKeyMap.put(100, "C");
        arabianKeyMap.put(90, "XC");
        arabianKeyMap.put(50, "L");
        arabianKeyMap.put(40, "XL");
        arabianKeyMap.put(10, "X");
        arabianKeyMap.put(9, "IX");
        arabianKeyMap.put(5, "V");
        arabianKeyMap.put(4, "IV");
        arabianKeyMap.put(1, "I");

    }

    boolean isRoman(String value) {
        return romanKeyMap.containsKey(value.charAt(0));
    }

    String intToRoman(int value) {

        String roman = "";
        int arabianKey;

        do {
            arabianKey = arabianKeyMap.floorKey(value);
            roman += arabianKeyMap.get(arabianKey);
            value -= arabianKey;
        } while (value != 0);

        return roman;
    }

    int romanToInt(String str) {

        int end = str.length()-1;
        char[] arr = str.toCharArray();
        int arabian;
        int result = romanKeyMap.get(arr[end]);

        for(int i = end - 1; i >= 0; i--) {
            arabian = romanKeyMap.get(arr[i]);

            if(arabian < romanKeyMap.get(arr[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }
        }
        return result;
    }
}
