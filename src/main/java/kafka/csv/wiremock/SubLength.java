package kafka.csv.wiremock;

import java.util.HashMap;
import java.util.Map;

public class SubLength {
    public static void main(String[] args) {

        String s = "abcabcbb";

        int counter=0;
        for (int i = 0; i < s.length(); i++) {
            Map<Character, Integer> map = new HashMap<>();
            int temp = 0;
            for (int j = 0; j < s.length(); j++) {
                if(map.get(s.charAt(j)) == null){
                    map.put(s.charAt(j), temp++);
                }
            }
            if(temp > counter){
                counter = temp;
            }
        }
        System.out.println(counter);
    }
}





//        int counter = 0;
//        for (int i = 0; i < s.length(); i++) {
//            int temp = 0;
//            for (int j = i+1; j < s.length(); j++) {
//                char[] x = new char[s.length()];
//                x[j] = s.charAt(j);
//                if(s.charAt(i) != s.charAt(j)){
//                    temp++;
//                }
//                else{
//                    break;
//                }
//            }
//            if (temp > counter){
//                counter = temp;
//            }
//
//        }
//        System.out.println(counter);
//    }

