package hero;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: ZhangXinLe
 * @CreateTime: 2018-1219:22
 */
public class TestHero {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        // 将文件内容读取并转化list对象
        List<Hero> list = null;
        try (Stream<String> lines = Files.lines(Paths.get("day21//heroes.txt"))) {
            list = lines.map((s) -> {
                String[] a = s.split("\t");
                // 1. 找到武将中武力前三的hero对象, 提示流也可以排序
                // 2. 按出生地分组
                // 3. 找出寿命前三的武将
                // 4. 女性寿命最高的
                // 5. 找出武力排名前三  100, 99, 97 97 ==> 4个人 吕布", "张飞", "关羽", "马超
                return new Hero(
                        Integer.parseInt(a[0]),
                        a[1],
                        a[2],
                        a[3],
                        Integer.parseInt(a[4]),
                        Integer.parseInt(a[5]),
                        Integer.parseInt(a[6])
                );
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 1. 找到武将中武力前三的hero对象, 提示流也可以排序
        System.out.println("武力值排名前三:");
        list.stream().sorted((h1, h2) -> h2.getPower() - h1.getPower()).limit(3).forEach(h -> System.out.println(h.getName()));
        // 2. 按出生地分组
        System.out.println("出生地分组：");
        Map<String, List<Hero>> group1 = list.stream().collect(Collectors.groupingBy((h) -> h.getLoc()));
        group1.forEach((k, v) -> {
            System.out.println(k + ":" + v.stream().map(h -> h.getName()).collect(Collectors.toList()));
        });
        // 3. 找出寿命前三的武将
        System.out.println("寿命前三：");
        list.stream().sorted((h1, h2) -> (h2.getDeath() - h2.getBirth()) - (h1.getDeath() - h1.getBirth())).limit(3).forEach(h -> System.out.println(h.getName()));
        // 4. 女性寿命最高的
        System.out.println("女性寿命最高的：");
        list.stream().filter(h -> h.getSex().equals("女")).sorted((h1, h2) -> (h2.getDeath() - h2.getBirth()) - (h1.getDeath() - h1.getBirth())).limit(3).forEach(h -> System.out.println(h.getName()));
        // 5. 找出武力排名前三
        System.out.println("武力值前三：");
        Set<Integer> topPowers = list.stream().map(h -> h.getPower()).sorted((p1, p2) -> p2 - p1).limit(3).collect(Collectors.toSet());
        list.stream().filter((h) -> topPowers.contains(h.getPower())).forEach((h) -> System.out.println(h.getName()));
        // 6. 按各个年龄段分组： 0~20, 21~40, 41~60, 60以上
        System.out.println("按年龄分组:");
        Map<String, List<Hero>> group2 = list.stream().collect(Collectors.groupingBy(h -> ageRange(h.getDeath() - h.getBirth())));
        group2.forEach((k, v) -> {
            System.out.println(k + ":" + v.stream().map(h -> h.getName()).collect(Collectors.toList()));
        });
        // 7. 按武力段分组： >=90， 80~89, 70~79, <70
        System.out.println("按武力段分组:");
        Map<String, List<Hero>> group3 = list.stream().collect(Collectors.groupingBy(h -> powerRange(h.getPower())));
        group3.forEach((k, v) -> {
            System.out.println(k + ":" + v.stream().map(h -> h.getName()).collect(Collectors.toList()));
        });
        // 8. 按出生地分组后，统计各组人数
        System.out.println("按出生地分组:");
        Map<String, Long> group4 = list.stream().collect(Collectors.groupingBy((h) -> h.getLoc(), Collectors.counting()));
        group4.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }

    public static String powerRange(int power) {
        if (power >= 90) {
            return "90以上";
        } else if (power >= 80 && power <= 89) {
            return "80~89";
        } else if (power >= 70 && power <= 79) {
            return "70~79";
        } else {
            return "70之下";
        }
    }

    public static String ageRange(int age) {
        if (age >= 0 && age <= 20) {
            return "0~20";
        } else if (age >= 21 && age <= 40) {
            return "21~40";
        } else if (age >= 41 && age <= 60) {
            return "41~60";
        } else {
            return "60以上";
        }
    }
}