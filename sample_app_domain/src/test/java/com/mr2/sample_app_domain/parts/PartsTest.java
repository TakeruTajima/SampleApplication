package com.mr2.sample_app_domain.parts;

import com.mr2.sample_app_domain.MyStringUtil;
import com.mr2.sample_app_domain.common.Price;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PartsTest {
    private Parts parts;

    @Before
    public void setUp() throws Exception {
        parts = new Parts("name", "model", "maker", new Price(100, "yen"), "unit");
    }

    @Test
    public void validateName() {
        assertTrue("品名：1文字以上", Parts.validateName("あ"));
        assertTrue("品名：100文字以内", Parts.validateName(MyStringUtil.repeatStr(100, "あ")));
        assertFalse("品名：101文字", Parts.validateName(MyStringUtil.repeatStr(101, "あ")));
        assertFalse("品名：空文字", Parts.validateName(""));
        assertFalse("品名：Null", Parts.validateName(null));
    }

    @Test
    public void validateModel() {
        assertTrue("1文字以上", Parts.validateMaker("a"));
        assertTrue("100文字以内", Parts.validateMaker(MyStringUtil.repeatStr(100, "a")));
        assertFalse("101文字", Parts.validateMaker(MyStringUtil.repeatStr(101, "a")));
        assertFalse("空文字", Parts.validateMaker(""));
        assertFalse("Null", Parts.validateMaker(null));
    }

    @Test
    public void validateMaker() {
        assertTrue(Parts.validateMaker("a"));
        assertTrue(Parts.validateMaker(MyStringUtil.repeatStr(100, "a")));
        assertFalse(Parts.validateMaker(MyStringUtil.repeatStr(101, "a")));
        assertFalse(Parts.validateMaker(""));
        assertFalse(Parts.validateMaker(null));
    }

    @Test
    public void validateValue() {
        assertTrue(Parts.validateValue(new Price(110, "yen")));
        assertFalse(Parts.validateValue(null));
    }

    @Test
    public void validateUnit() {
        assertTrue(Parts.validateUnit("a"));
        assertTrue(Parts.validateUnit(MyStringUtil.repeatStr(10, "a")));
        assertFalse(Parts.validateUnit(MyStringUtil.repeatStr(11, "a")));
        assertFalse(Parts.validateUnit(""));
        assertFalse(Parts.validateUnit(null));
    }

    @Test
    public void changeName() {
        final String minStr = "a";
        final String maxStr = MyStringUtil.repeatStr(100, "a");

        parts.changeName(minStr);
        assertEquals("1文字", parts.getName(), minStr);
        parts.changeName(maxStr);
        assertEquals("100文字", parts.getName(), maxStr);

        try {
            parts.changeName("");
            fail();
        }catch (IllegalArgumentException ignored) { }
        try {
            parts.changeName(null);
            fail();
        }catch (IllegalArgumentException ignored) { }
        try {
            parts.changeName(MyStringUtil.repeatStr(101, "a"));
            fail();
        }catch (IllegalArgumentException ignored) { }
    }

    @Test
    public void changeValue() {
        final Price value = new Price(110, "yen");

        parts.changeValue(value);
        assertEquals(parts.getValue(), value);

        try {
            parts.changeValue(null);
            fail();
        }catch (IllegalArgumentException ignored){}
    }

    @Test
    public void changeUnit() {
        final String minUnit = "a";
        final String maxUnit = MyStringUtil.repeatStr(10, "a");

        parts.changeUnit(minUnit);
        assertEquals(parts.getUnit(), minUnit);
        parts.changeUnit(maxUnit);
        assertEquals(parts.getUnit(), maxUnit);

        try{
            parts.changeUnit("");
            fail();
        }catch (IllegalArgumentException ignored){ }
        try {
            parts.changeUnit(null);
            fail();
        }catch (IllegalArgumentException ignored){}
        try {
            parts.changeUnit(MyStringUtil.repeatStr(101, "a"));
            fail();
        }catch (IllegalArgumentException ignored){}
    }

    @Test
    public void getName() {
    }

    @Test
    public void getModel() {
    }

    @Test
    public void getMaker() {
    }

    @Test
    public void getValue() {
    }

    @Test
    public void getUnit() {
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
    }
}