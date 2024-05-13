package org.example.solut;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SolutionDemo {
    public static void main(String[] args) {
        List<String> strList = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");
        Map<String, Integer> result = new Solution().calculateStringCount(strList);
        System.out.println(result);
        Map<String, Long> result1 = new Solution().calculateStringCountNew(strList);
        System.out.println(result1);

        String testStr = "hhhhhhAAABBBCCsss";
        Map<Character, Integer> result2 = new Solution().calculateCharacterCount(testStr);
        System.out.println(result2);
    }
}

class Solution {
    // 输入：strList = ["apple", "banana", "apple", "orange", "banana", "apple"]
    // 输出：{"apple": 3, "banana": 2, "orange": 1}

    /**
     * 计算字符串列表中每个字符串的出现次数
     * @param strList
     * @return
     */
    public Map<String, Integer> calculateStringCount(List<String> strList) {
        // 创建一个HashMap来存储字符串和它们的计数
        Map<String, Integer> stringCountMap = new HashMap<>();

        // 遍历字符串列表
        for (String str : strList) {
            // 如果字符串已经在地图中，增加计数
            if (stringCountMap.containsKey(str)) {
                stringCountMap.put(str, stringCountMap.get(str) + 1);
            }
            // 否则，将字符串添加到地图中，计数为1
            else {
                stringCountMap.put(str, 1);
            }
        }

        // 返回结果
        return stringCountMap;
    }

    // 输入：str = "Hello, World!"
    // 输出：{"H": 1, "e": 1, "l": 3, "o": 2, ",": 1, "W": 1, "r": 1, "d": 1, "!": 1}

    /**
     * 计算字符串中每个字符的出现次数
     * @param str
     * @return
     */
    public Map<Character, Integer> calculateCharacterCount(String str) {
        // 创建一个HashMap来存储字符和它们的计数
        Map<Character, Integer> characterCountMap = new HashMap<>();

        // 遍历字符串中的每个字符
        for (char c : str.toCharArray()) {
            // 如果字符已经在地图中，增加计数
            if (characterCountMap.containsKey(c)) {
                characterCountMap.put(c, characterCountMap.get(c) + 1);
            }
            // 否则，将字符添加到地图中，计数为1
            else {
                characterCountMap.put(c, 1);
            }
        }

        // 返回结果
        return characterCountMap;
    }

    /**
     * 计算字符串列表中每个字符串的出现次数
     *
     * @param strList 输入的字符串列表
     * @return 包含每个字符串及其出现次数的映射
     */
    public Map<String, Long> calculateStringCountNew(List<String> strList) {
        // 检查输入列表是否为空，如果是则返回一个空映射，避免NullPointerException
        if (strList == null) {
            return Collections.emptyMap();
        }

        // 使用Stream API和Collectors来简化计数逻辑和提高性能
        return strList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


}
