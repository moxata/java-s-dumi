package com.combytebg;

import java.util.ArrayList;
import java.util.List;

final public class SDumi {

    private enum Rod {
        MUJKI,
        JENSKI,
        X
    }

    private static final List<Rod> EXP_TO_ROD = List.of(Rod.MUJKI, Rod.X, Rod.X,
            Rod.JENSKI, Rod.X, Rod.X,
            Rod.MUJKI, Rod.X, Rod.X,
            Rod.MUJKI, Rod.X, Rod.X,
            Rod.MUJKI);

    private static final List<String> ci = List.of("нула", "един", "две",
            "три", "четири", "пет", "шест", "седем", "осем", "девет", "десет",
            "единадесет", "дванадесет", "тринадесет", "четиринадесет", "петнадесет",
            "шестнадесет", "седемнадесет", "осемнадесет", "деветнадесет");

    private static final List<String> DESETICI = List.of("padding", "padding",
            "двадесет", "тридесет", "четиридесет", "петдесет", "шестдесет",
            "седемдесет", "осемдесет", "деветдесет");

    private static final List<String> STOTICI = List.of("padding",
            "сто", "двеста" ,"триста", "четиристотин", "петстотин", "шестстотин",
            "седемстотин", "осемстотин", "деветстотин");

    private static final List<List<String>> TRIEXPONENTS = List.of(
            List.of("padding"),
            List.of("хиляда", "хиляди"),
            List.of("милион", "милиона"),
            List.of("милиард", "милиарда"),
            List.of("трилион", "трилиона")
    );

    private static class TriItem {
        String s;
        int exp;
        boolean hasI;
        boolean isPlural;
        String res;
    }

    private static List<TriItem> buildTriList(double suma) {
        suma = Math.abs(suma);
        String zeroPadded = padLeftZeros(Double.toString(suma), 15, '0');
        List<TriItem> triList = new ArrayList<>();
        int triLength = (int)Math.floor((double)zeroPadded.length() / 3);
        TriItem newTri;
        for (int i=0; i<triLength; i++) {
            newTri = new TriItem();
            newTri.s = zeroPadded.substring(i*3, i*3 + 3);
            newTri.exp = zeroPadded.length() - (i*3) - 3;
            newTri.hasI = false;
            newTri.res = "";
            newTri.isPlural = (Integer.parseInt(newTri.s, 10) != 1);
            if (!newTri.s.equals("000")) {
                triList.add(newTri);
            }
        }

        return triList;
    }

    // 0-19
    private static String EDINICI(int n, Rod rod) {
        switch (n) {
            case 1: {
                if (rod == Rod.JENSKI) {
                    return "една";
                } else {
                    return "един";
                }
            }
            case 2: {
                if (rod == Rod.JENSKI) {
                    return "две";
                } else {
                    return "два";
                }
            }
            default:
                return ci.get(n);
        }
    }

    private static String padLeftZeros(String inputString, int length, char ch) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(ch).repeat(Math.max(0, length)));

        return sb.substring(inputString.length()) + inputString;
    }

    void alaBala() {
        ci.add("sdf");
    }
}
