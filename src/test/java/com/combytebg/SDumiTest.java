package com.combytebg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SDumiTest {

    @Test
    void sDumi() {
        assertEquals(SDumi.sDumi(123.12),
                "Сто двадесет и три лв. и 12 ст.");
        assertEquals(SDumi.sDumi(100123.00),
                "Сто хиляди сто двадесет и три лв. и 00 ст.");
        assertEquals(SDumi.sDumi(2342892429.24),
                "Два милиарда триста четиридесет и два милиона осемстотин деветдесет и две хиляди четиристотин двадесет и девет лв. и 24 ст.");
        assertEquals(SDumi.sDumi(31.33),
                "Тридесет и един лв. и 33 ст.");
        assertEquals(SDumi.sDumi(120.83),
                "Сто и двадесет лв. и 83 ст.");
        assertEquals(SDumi.sDumi(710.63),
                "Седемстотин и десет лв. и 63 ст.");
        assertEquals(SDumi.sDumi(1000.99),
                "Хиляда лв. и 99 ст.");
        assertEquals(SDumi.sDumi(0),
                "Нула лв. и 00 ст.");
        assertEquals(SDumi.sDumi(21000.21),
                "Двадесет и една хиляди лв. и 21 ст.");
        assertEquals(SDumi.sDumi(-100020.20),
                "Минус сто хиляди и двадесет лв. и 20 ст.");
    }
}