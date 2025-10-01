package packages.server.services;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public String intToRoman(int num) {
        List<Character> answer = new ArrayList<>();

        while (num != 0) {
            if (num / 1000 > 0) {
                answer.add('M');
                num -= 1000;
            } else if (num / 500 > 0) {
                answer.add('D');
                num -= 500;
            } else if (num / 100 > 0) {
                answer.add('C');
                num -= 100;
            } else if (num / 50 > 0) {
                answer.add('L');
                num -= 50;
            } else if (num / 10 > 0) {
                answer.add('X');
                num -= 10;
            } else if (num / 5 > 0) {
                answer.add('V');
                num -= 5;
            } else if (num / 1 > 0) {
                answer.add('I');
                num -= 1;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char c : answer) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.err.println(solution.intToRoman(39));
    }
}
