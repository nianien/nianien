package com.nianien.core.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 统一浮点数精度
 *
 * @author skyfalling
 * @version 1.0.0
 */
public class FineDecimal extends BigDecimal {


    /**
     * 默认精度为4
     */
    private static final ThreadLocal<Integer> DEFAULT_SCALE = ThreadLocal.withInitial(() -> 4);

    /**
     * 获取当前精度
     *
     * @return
     */
    public static int getDefaultScale() {
        return DEFAULT_SCALE.get();
    }

    /**
     * 设置当前精度
     *
     * @param scale
     */
    public static void setDefaultScale(int scale) {
        DEFAULT_SCALE.set(scale);
    }


    /**
     * @param val 初始值
     */
    public FineDecimal(Number val) {
        this(val == null ? 0 : val.doubleValue());
    }

    /**
     * @param val 初始值
     */
    private FineDecimal(double val) {
        super(val, new MathContext(new BigDecimal(val).setScale(DEFAULT_SCALE.get(), ROUND_HALF_UP).precision(), RoundingMode.HALF_UP));
    }


    @Override
    public FineDecimal add(BigDecimal augend) {
        return $(super.add(augend));
    }

    @Override
    public FineDecimal add(BigDecimal augend, MathContext mc) {
        return $(super.add(augend, mc));
    }

    @Override
    public FineDecimal subtract(BigDecimal subtrahend) {
        return $(super.subtract(subtrahend));
    }

    @Override
    public FineDecimal subtract(BigDecimal subtrahend, MathContext mc) {
        return $(super.subtract(subtrahend, mc));
    }

    @Override
    public FineDecimal multiply(BigDecimal multiplicand) {
        return $(super.multiply(multiplicand));
    }

    @Override
    public FineDecimal multiply(BigDecimal multiplicand, MathContext mc) {
        return $(super.multiply(multiplicand, mc));
    }

    @Override
    public FineDecimal divide(BigDecimal divisor, int scale, int roundingMode) {
        return $(super.divide(divisor, scale, roundingMode));
    }

    @Override
    public FineDecimal divide(BigDecimal divisor, int scale, RoundingMode roundingMode) {
        return $(super.divide(divisor, scale, roundingMode));
    }

    @Override
    public FineDecimal divide(BigDecimal divisor, int roundingMode) {
        return $(super.divide(divisor, roundingMode));
    }

    @Override
    public FineDecimal divide(BigDecimal divisor, RoundingMode roundingMode) {
        return $(super.divide(divisor, roundingMode));
    }

    @Override
    public FineDecimal divide(BigDecimal divisor) {
        return $(super.divide(divisor, DEFAULT_SCALE.get(), RoundingMode.HALF_UP));
    }

    @Override
    public FineDecimal divide(BigDecimal divisor, MathContext mc) {
        return $(super.divide(divisor, mc));
    }

    @Override
    public FineDecimal divideToIntegralValue(BigDecimal divisor) {
        return $(super.divideToIntegralValue(divisor));
    }

    @Override
    public FineDecimal divideToIntegralValue(BigDecimal divisor, MathContext mc) {
        return $(super.divideToIntegralValue(divisor, mc));
    }

    @Override
    public FineDecimal remainder(BigDecimal divisor) {
        return $(super.remainder(divisor));
    }

    @Override
    public FineDecimal remainder(BigDecimal divisor, MathContext mc) {
        return $(super.remainder(divisor, mc));
    }


    @Override
    public FineDecimal[] divideAndRemainder(BigDecimal divisor) {
        return $(super.divideAndRemainder(divisor));
    }

    @Override
    public FineDecimal[] divideAndRemainder(BigDecimal divisor, MathContext mc) {
        return $(super.divideAndRemainder(divisor, mc));
    }

    @Override
    public FineDecimal pow(int n) {
        return $(super.pow(n));
    }

    @Override
    public FineDecimal pow(int n, MathContext mc) {
        return $(super.pow(n, mc));
    }

    @Override
    public FineDecimal abs() {
        return $(super.abs());
    }

    @Override
    public FineDecimal abs(MathContext mc) {
        return $(super.abs(mc));
    }

    @Override
    public FineDecimal negate() {
        return $(super.negate());
    }

    @Override
    public FineDecimal negate(MathContext mc) {
        return $(super.negate(mc));
    }

    @Override
    public FineDecimal plus() {
        return $(super.plus());
    }

    @Override
    public FineDecimal plus(MathContext mc) {
        return $(super.plus(mc));
    }


    @Override
    public FineDecimal round(MathContext mc) {
        return $(super.round(mc));
    }

    @Override
    public FineDecimal movePointLeft(int n) {
        return $(super.movePointLeft(n));
    }

    @Override
    public FineDecimal movePointRight(int n) {
        return $(super.movePointRight(n));
    }

    @Override
    public FineDecimal scaleByPowerOfTen(int n) {
        return $(super.scaleByPowerOfTen(n));
    }

    @Override
    public FineDecimal stripTrailingZeros() {
        return $(super.stripTrailingZeros());
    }

    @Override
    public FineDecimal min(BigDecimal val) {
        return $(super.min(val));
    }

    @Override
    public FineDecimal max(BigDecimal val) {
        return $(super.max(val));
    }

    @Override
    public FineDecimal ulp() {
        return $(super.ulp());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BigDecimal ? this.compareTo((BigDecimal) o) == 0 : false;
    }

    private FineDecimal $(BigDecimal bigDecimal) {
        return new FineDecimal(bigDecimal);
    }

    private FineDecimal[] $(BigDecimal[] bigDecimals) {
        FineDecimal[] fineDecimals = new FineDecimal[bigDecimals.length];
        for (int i = 0; i < bigDecimals.length; i++) {
            fineDecimals[i] = $(bigDecimals[i]);
        }
        return fineDecimals;
    }

}
