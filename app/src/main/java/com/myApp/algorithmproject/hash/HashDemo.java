package com.myApp.algorithmproject.hash;

import com.myApp.algorithmproject.map.Map;

import static java.lang.Double.doubleToLongBits;

/**
 * author: zhouyh
 * created on: 2020-06-09 19:47
 * description: 哈希demo
 */
public class HashDemo {

    String[] title = new String[]{

    };


    public static void main(String[] args) {


//        System.out.println(titles[0] + "-" +  titles[1]);
//        test1();
        test4(new LinkedHashMap<Object, Integer>());

    }


    static void test4(HashMap<Object,Integer> map){

        map.put("jack",1);
        map.put("rose",2);
        map.put("jim",3);
        map.put("allen",4);
        for (int i = 1; i <= 10; i++){
            map.put("test" + i, i);
            map.put(new Key(i),i);
        }


        map.traversal(new Map.Visitor<Object, Integer>() {

            @Override
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });



    }


    static void test1(){

        HashMap<Object,Integer> map = new HashMap<>();


        for (int i = 1; i <= 1000; i++){
            map.put(new Key(i),i);
        }

//        map.print();

//        map.put(new Key(4),100);
//
//        System.out.println(map.size());
//        System.out.println(map.get(new Key(1)));
//        System.out.println(map.get(new Key(4)));

                map.traversal(new Map.Visitor<Object, Integer>() {
            @Override
            public boolean visit(Object key, Integer value) {
                System.out.println( key+"_" + value);
                return false;
            }
        });

    }



    public static int hashCode(String str){
        int len = str.length();
        int hashcode = 0;

        for (int i = 0; i<len;i++){
            char c = str.charAt(i);
            hashcode = (hashcode * 31 ) + c;
//            hashcode = (hashcode<<5)-hashcode + c;
        }

        // hashcode = [(j * 31 + a)*31 + c]* 31 + k;
        return hashcode;
    }


    public static int hashCode(float value) {

       int bits =  Float.floatToIntBits(10.6f);
       return bits ^ (bits >>> 32);
    }

    public static int hashCode(long value) {

        /**
         * 高32位和低32位混合计算出32位哈希值
         *
         * value                       1111 1111 1111 1111 1111 1111 1111 1111 （1011 0110 0011 1001 0110 1111 1100 1010)低32位
         * value >>> 32 高32位右移 补位(0000 0000 0000 0000 0000 0000 0000 0000) (1111 1111 1111 1111 1111 1111 1111 1111)高32位
         * value^(value>>>32)     异或 1111 1111 1111 1111 1111 1111 1111 1111 （0100 1001 1100 0110 1001 0000 0011 0101）结果
         *
         * int强制转换成32位值
         */
        return (int) (value ^ (value >>> 32));
    }


    public static int hashCode(double value) {
        long bits = doubleToLongBits(value);
        return (int) (bits ^ (bits >>> 32));
    }


    /**
     * 字符串的哈希值
     * 比如字符串jack,由j、a、c、k四个字符组成（字符的本质就是一个整数）
     * 因此，jack的哈希值可以表示位 j * n^3 + a * n ^2 + c * n^1 + k * n^0,等价于 [ (j * n + a) * n + c ] * n + k
     * 在JDK中，乘数n为31， 31是一个奇素数，JVM会将31*i 优化成 （i << 5)-i
     */


    /**
     * 哈希表中哈希函数的实现步骤
     * 1.先生成key的哈希值（必须是整数）
     * 2.再让key的哈希值跟数组的大小进行相关运算，生成一个索引值
     * @param key
     * @return
     */
    int size;
    public int hash(Object key){
        return hashCode((Double) key) & (size - 1);
    }

}
