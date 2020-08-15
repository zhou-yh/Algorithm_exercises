package com.myApp.algorithmproject.stack;

import java.util.HashMap;

/**
 * author: zhouyh
 * created on: 2020-05-19 18:31
 * description: 判断有效括号
 */
public class _20_有效的括号 {


    /**
     * 还有一种方法 hashmap
     */
    private static HashMap<Character,Character> map = new HashMap<>();

    static {
        map.put('(',')');
        map.put('[',']');
        map.put('{','}');
    }




    /**
     * 是否有效
     * @param s
     * @return
     */
    public boolean isValidate(String s){

        Stack<Character> stack = new Stack<>();
        int length = s.length();
        for (int i = 0; i < length ; i++){
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{'){ //左括号
                stack.push(c);
            }else { //右括号
                if (stack.isEmpty())return false;

                char left = stack.pop();
                if (left == '(' && c == ')') return false;
                if (left == '[' && c == ']') return false;
                if (left == '{' && c == '}') return false;
            }
        }

        return stack.isEmpty();
    }

    /**
     * {[()]}
     * @param s
     * @return
     */
    public boolean isValidate2(String s){

        /**
         * 效率低，耗内存
         */
        while (s.contains("{}")|| s.contains("[]")||s.contains("()")){
           s =  s.replace("{}","");
           s =  s.replace("[]","");
           s =  s.replace("()","");
        }
        return s.isEmpty();
    }
}
