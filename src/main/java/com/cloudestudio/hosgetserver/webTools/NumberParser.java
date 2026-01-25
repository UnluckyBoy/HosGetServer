package com.cloudestudio.hosgetserver.webTools;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Class NumberParser
 * @Author Create By Matrix·张
 * @Date 2026/1/20 下午1:40
 * 233processStrings
 */
public class NumberParser {
    // 存储结果的Map
    private static final Map<Integer, Double> resultMap = new HashMap<>();

    // 将多行字符串转换为List<String>
    public static List<String> convertToLines(String multiLineString) {
        return multiLineString.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());
    }

    // 解析字符串并返回数字列表和每个数字的值
    public static class ParsedResult {
        public List<Integer> numbers;
        public double value;

        public ParsedResult(List<Integer> numbers, double value) {
            this.numbers = numbers;
            this.value = value;
        }
    }

    // 解析不同格式的字符串
    public static ParsedResult parseNumberString(String str) throws Exception {
        // 移除所有空格和中文括号，替换中文逗号为英文逗号
        String cleaned = str
                .replaceAll("[（）]", "") // 移除中文括号
                .replaceAll("，", ",")    // 中文逗号转英文逗号
                .replaceAll("\\s+", "")   // 移除所有空格
                .trim();

        // 情况1: 包含"各"或"个"的情况（每个数字对应相同的值）
        Pattern eachPattern = Pattern.compile("^([\\d,]+)[各|个](\\d+)米$");
        Matcher eachMatcher = eachPattern.matcher(cleaned);

        if (eachMatcher.find()) {
            String numbersStr = eachMatcher.group(1);
            double value = Double.parseDouble(eachMatcher.group(2));

            List<Integer> numbers = parseNumbersString(numbersStr);
            return new ParsedResult(numbers, value);
        }

        // 情况2: 包含"共"的情况（总值均分给每个数字）
        Pattern totalPattern = Pattern.compile("^([\\d,]+)共(\\d+)米$");
        Matcher totalMatcher = totalPattern.matcher(cleaned);

        if (totalMatcher.find()) {
            String numbersStr = totalMatcher.group(1);
            double totalValue = Double.parseDouble(totalMatcher.group(2));

            List<Integer> numbers = parseNumbersString(numbersStr);

            if (numbers.isEmpty()) {
                throw new Exception("没有找到数字: " + str);
            }

            // 计算每个数字应得的均分值
            double valuePerNumber = totalValue / numbers.size();

            // 保留两位小数
            double roundedValue = Math.round(valuePerNumber * 100.0) / 100.0;

            return new ParsedResult(numbers, roundedValue);
        }

        throw new Exception("无法识别的格式: " + str + "，应该包含\"各\"、\"个\"或\"共\"关键字");
    }

    // 解析数字字符串
    private static List<Integer> parseNumbersString(String numbersStr) {
        List<Integer> numbers = new ArrayList<>();
        String[] parts = numbersStr.split(",");

        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                try {
                    numbers.add(Integer.parseInt(part.trim()));
                } catch (NumberFormatException e) {
                    // 忽略无效数字
                }
            }
        }

        return numbers;
    }

    // 处理所有字符串并累加
    public static Map<Integer, Double> processStrings(List<String> strings) {
        for (String str : strings) {
            try {
                ParsedResult parsed = parseNumberString(str);

                System.out.println("解析成功: \"" + str + "\" -> 数字: " + parsed.numbers + ", 每个值: " + parsed.value + "米");

                for (Integer num : parsed.numbers) {
                    double currentValue = parsed.value;
                    double existingValue = resultMap.getOrDefault(num, 0.0);
                    resultMap.put(num, existingValue + currentValue);
                }
            } catch (Exception error) {
                System.out.println("无法解析字符串: \"" + str + "\" - " + error.getMessage());
            }
        }

        return resultMap;
    }

    // 输出结果格式化的函数
    public static void printResult() {
        System.out.println("\n累加结果:");
        System.out.println("数字\t总米数");
        System.out.println("----------------");

        // 按键排序
        List<Integer> sortedKeys = new ArrayList<>(resultMap.keySet());
        Collections.sort(sortedKeys);

        for (Integer num : sortedKeys) {
            double value = resultMap.get(num);
            String displayValue;
            if (value == Math.floor(value) && !Double.isInfinite(value)) {
                displayValue = String.format("%.0f米", value);
            } else {
                displayValue = String.format("%.2f米", value);
            }
            System.out.println(num + "\t" + displayValue);
        }
    }
}
