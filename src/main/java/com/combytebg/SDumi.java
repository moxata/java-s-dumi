package com.combytebg;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class SDumi {

    public static String sDumiCialaChast(double suma) {
        suma = (long) suma;
        if (suma == 0) {
            return "нула";
        }

        boolean isPlus = (suma >= 0);
        List<TriItem> triList = buildTriList(suma);

        for (TriItem tri : triList) {
            String s = tri.s;

            if (s.charAt(0) == '0') {

                if (s.charAt(1) <= '1') {

                    if (tri.exp == 3 && !tri.isPlural) {
                        // не се казва "една хиляда"
                        tri.res = "";
                    } else {
                        tri.res = EDINICI(stoi(s), EXP_TO_ROD.get(tri.exp));
                    }

                } else {

                    tri.res = DESETICI.get(ctoi(s.charAt(1)));
                    if (s.charAt(2) != '0') {
                        tri.res += " и " + EDINICI(ctoi(s.charAt(2)), EXP_TO_ROD.get(tri.exp));
                        tri.hasI = true;
                    }
                }

            } else {

                tri.res = STOTICI.get(ctoi(s.charAt(0)));
                int dvu = stoi(s.substring(1));
                if (dvu > 0) {
                    if (s.charAt(1) <= '1') {
                        tri.res += " и " + EDINICI(dvu, EXP_TO_ROD.get(tri.exp));
                        tri.hasI = true;
                    } else {
                        if (s.charAt(2) == '0') {
                            tri.res += " и";
                            tri.hasI = true;
                        }
                        tri.res += " " + DESETICI.get(ctoi(s.charAt(1)));
                        if (s.charAt(2) != '0') {
                            tri.res += " и " + EDINICI(ctoi(s.charAt(2)), EXP_TO_ROD.get(tri.exp));
                            tri.hasI = true;
                        }
                    }
                }
            }
        }

        StringBuilder res = new StringBuilder();
        for (int i=0, len=triList.size(); i<len; i++) {
            res.append(triList.get(i).res).append(" ");
            int triExp = (int) Math.floor((double) triList.get(i).exp / 3);
            if (triExp >= 1) {
                int isPluralI = triList.get(i).isPlural ? 1 : 0;
                res.append(TRIEXPONENTS.get(triExp).get(isPluralI)).append(" ");
            }
            if (i == triList.size()-2 && !triList.get(i+1).hasI) {
                res.append("и ");
            }
        }
        res = new StringBuilder(res.toString().trim());
        if (!isPlus) {
            res.insert(0, "минус ");
        }

        return res.toString();
    }

    public static String sDumi(double suma) {
        return sDumi(suma, "лв.");
    }

    public static String sDumi(double suma, String valuta) {
        String s = sDumiCialaChast((long) suma) + " " + valuta;
        String stot = frac(suma);
        stot = padLeftZeros(stot, 2);
        s += " и " + stot + " ст.";
        // first char should be upper case
        s =  Character.toUpperCase(s.charAt(0)) + s.substring(1);
        return s;
    }

    private static int ctoi(char ch) {
        return Integer.parseInt(String.valueOf(ch), 10);
    }

    private static int stoi(String s) {
        return Integer.parseInt(s, 10);
    }

    private enum Rod {
        MUJKI,
        JENSKI,
        X
    }

    private static final List<Rod> EXP_TO_ROD = Arrays.asList(Rod.MUJKI, Rod.X, Rod.X,
            Rod.JENSKI, Rod.X, Rod.X,
            Rod.MUJKI, Rod.X, Rod.X,
            Rod.MUJKI, Rod.X, Rod.X,
            Rod.MUJKI);

    private static final List<String> ci = Arrays.asList("нула", "един", "две",
            "три", "четири", "пет", "шест", "седем", "осем", "девет", "десет",
            "единадесет", "дванадесет", "тринадесет", "четиринадесет", "петнадесет",
            "шестнадесет", "седемнадесет", "осемнадесет", "деветнадесет");

    private static final List<String> DESETICI = Arrays.asList("padding", "padding",
            "двадесет", "тридесет", "четиридесет", "петдесет", "шестдесет",
            "седемдесет", "осемдесет", "деветдесет");

    private static final List<String> STOTICI = Arrays.asList("padding",
            "сто", "двеста" ,"триста", "четиристотин", "петстотин", "шестстотин",
            "седемстотин", "осемстотин", "деветстотин");

    private static final List<List<String>> TRIEXPONENTS = Arrays.asList(
            Arrays.asList("padding", "padding"),
            Arrays.asList("хиляда", "хиляди"),
            Arrays.asList("милион", "милиона"),
            Arrays.asList("милиард", "милиарда"),
            Arrays.asList("трилион", "трилиона")
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
        String zeroPadded = padLeftZeros(Long.toString((long) suma), 15);
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

    private static String padLeftZeros(String inputString, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<length; i++) {
            sb.append('0');
        }

        return sb.substring(inputString.length()) + inputString;
    }

    private static String frac(double suma) {
        StringBuilder sSuma = new StringBuilder(String.format("%.2f", suma));
        char decimalSep = DecimalFormatSymbols.getInstance().getDecimalSeparator();
        int dot = sSuma.toString().indexOf(decimalSep);
        if (dot != -1) {
            sSuma = new StringBuilder(sSuma.substring(dot + 1));
            while (sSuma.length() < 2) {
                sSuma.append('0');
            }
            return sSuma.toString();
        } else {
            return "0";
        }
    }
}
