package com.ljw.guava;

import com.google.common.collect.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2023/1/18 10:03
 */
public class GuavaTest {
    /**
     * 双键Map
     */
    @Test
    void testHashBasedTable() {
        Table<String, String, Integer> table = HashBasedTable.create();
        //存放元素 ：行 列 值
        table.put("Hydra", "Jan", 20);
        table.put("Hydra", "Feb", 28);

        table.put("Trunks", "Jan", 28);
        table.put("Trunks", "Feb", 16);
        table.put("Trunks", "3545", 16);
        //取出元素
        Integer dayCount = table.get("Trunks", "3545");
        Assertions.assertEquals(dayCount, 16);
        //以行 为key转Map
        Map<String, Map<String, Integer>> rowMap = table.rowMap();
        //以列 为key转Map
        Map<String, Map<String, Integer>> columnMap = table.columnMap();
        //取出行为：Hydra
        Map<String, Integer> row = table.row("Hydra");
        //取出列为：Jan
        Map<String, Integer> column = table.column("Jan");
        //取出行的所有key
        Set<String> rowKeySet = table.rowKeySet();
        table.put("Trunks", "3545", 99);
        //取出元素
        Integer edit = table.get("Trunks", "3545");
        Assertions.assertEquals(edit, 99);
        //取出列的所有key
        Set<String> columnKeySet = table.columnKeySet();
        //value集合，值相同有多个
        Collection<Integer> values = table.values();

        //包含行列key
        boolean contains = table.contains("Hydra", "Jan");
        Assertions.assertTrue(contains);
        //包含行key
        boolean hydra = table.containsRow("Hydra");
        Assertions.assertTrue(hydra);
        //包含列key
        boolean containsColumn = table.containsColumn("3545");
        Assertions.assertTrue(containsColumn);
        //包含值
        boolean containsValue = table.containsValue(99);
        Assertions.assertTrue(containsValue);
        System.out.println(table);
        //行和列的转置
        Table<String, String, Integer> transpose = Tables.transpose(table);
        System.out.println(transpose);
        //转cell对象
        Set<Table.Cell<String, String, Integer>> cells = table.cellSet();
        cells.forEach(cell -> {
            //遍历一个cell
            System.out.println(cell.getRowKey() + "," + cell.getColumnKey() + ":" + cell.getValue());
        });
    }

    /**
     * 双向Map
     * value不可重复
     */

    @Test
    void testHashBiMap() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("Hydra", "Programmer");
        biMap.put("Tony", "IronMan");
        biMap.put("Thanos", "Titan");
        //value不可重复
        //biMap.put("Tony2", "IronMan");
        //使用key获取value
        System.out.println(biMap.get("Tony"));
        //反转后的BiMap并不是一个新的对象，它实现了一种视图的关联，所以对反转后的BiMap执行的所有操作会作用于原先的BiMap上。
        BiMap<String, String> inverse = biMap.inverse();
        //使用value获取key
        System.out.println(inverse.get("IronMan"));
        //对反转后的BiMap中的内容进行了修改后，再看一下原先BiMap中的内容
        inverse.put("IronMan", "Stark");
        System.out.println(inverse);
        System.out.println(biMap);
        //返回value集合
        Set<String> values = biMap.values();

        try {
            biMap.put("Thanos222", "Titan");
        } catch (Exception e) {
            System.out.println("value不可重复,使用forcePut强制更新");
        }
        biMap.forcePut("Thanos222", "Titan");
        System.out.println(biMap);
    }


    /**
     * 多值map
     */
    @Test
    void testMultimap() {
        //Multimap
        Multimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.put("day", 1);
        multimap.put("day", 2);
        multimap.put("day", 8);
        multimap.put("month", 3);
        System.out.println(multimap);
        Collection<Integer> day = multimap.get("day");
        System.out.println(day);
        System.out.println("===============修改前");
        //HashMultimap、TreeMultimap等
        //使用确定的类：ArrayListMultimap，那么get方法将返回一个List
        ArrayListMultimap<String, Integer> multimap2 = ArrayListMultimap.create();
        multimap2.put("day", 1);
        multimap2.put("day", 2);
        //那么get方法将返回一个List
        List<Integer> day2 = multimap2.get("day");
        System.out.println(day2);
        //Multimap的get方法会返回一个非null的集合，但是这个集合的内容可能是空
        List<Integer> day33 = multimap2.get("day333");
        System.out.println(day33);
        //使用get方法返回的集合也不是一个独立的对象，可以理解为集合视图的关联，对这个新集合的操作仍然会作用于原始的Multimap上
        //这个0是下标
        day2.remove(0);
        day33.add(33);
        System.out.println("===============get修改后");
        System.out.println(day2);
        System.out.println(day33);
        System.out.println("===============asMap修改后");
        //转换为Map：asMap也是试图映射，修改会改变原来的值
        Map<String, Collection<Integer>> map = multimap2.asMap();
        map.get("day").add(20);
        System.out.println(map);
        System.out.println(day2);
        System.out.println(multimap2);

        //map中元素个数，包括重复的key
        System.out.println(multimap.size());
        //map中元素个数，包括重复的key
        System.out.println(multimap.entries().size());
        //map中的key
        Set<String> keySet = multimap.keySet();
    }

    /**
     * 范围Map
     */
    @Test
    void testRangeMap() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        //closedOpen:[)左闭右开区间
        rangeMap.put(Range.closedOpen(0, 60), "不及格");
        //closed:[]左闭右闭区间
        rangeMap.put(Range.closed(60, 90), "良好");
        //openClosed:(]左开右闭区间
        rangeMap.put(Range.openClosed(90, 100), "优秀");

        Assertions.assertEquals(rangeMap.get(59), "不及格");
        Assertions.assertEquals(rangeMap.get(60), "良好");
        Assertions.assertEquals(rangeMap.get(90), "良好");
        Assertions.assertEquals(rangeMap.get(91), "优秀");

        //移除区间，没有区间的返回null
        rangeMap.remove(Range.closed(60, 70));
        //移除[0-60)【60,70】(70,90](90,100]
        Assertions.assertEquals(rangeMap.get(59), "不及格");
        Assertions.assertEquals(rangeMap.get(60), null);
        Assertions.assertEquals(rangeMap.get(65), null);
        Assertions.assertEquals(rangeMap.get(70), null);
        Assertions.assertEquals(rangeMap.get(71), "良好");
        //转map
        Map<Range<Integer>, String> rangeStringMap = rangeMap.asMapOfRanges();
        rangeStringMap.forEach((range, v) -> {
            Integer lowerEndpoint = range.lowerEndpoint();
            Integer upperEndpoint = range.upperEndpoint();
            System.out.println(lowerEndpoint);
            System.out.println(upperEndpoint);
            System.out.println(v);
        });
    }


    /**
     * 实例Map
     */
    @Test
    void testClassToInstanceMap() {
        AtomicInteger s = new AtomicInteger(99999);
        //存储类和值
        Map<Class, Object> map = new HashMap<>();
        map.put(AtomicInteger.class, s);
        //需要转类型
        AtomicInteger o = (AtomicInteger) map.get(AtomicInteger.class);
        Assertions.assertEquals(o, s);

        //ClassToInstanceMap方式
        ClassToInstanceMap<Object> instanceMap = MutableClassToInstanceMap.create();
        instanceMap.putInstance(AtomicInteger.class, s);
        AtomicInteger value = instanceMap.getInstance(AtomicInteger.class);
        //不需转类型
        Assertions.assertEquals(value, s);

    }


}
