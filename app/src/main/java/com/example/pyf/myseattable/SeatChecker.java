package com.example.pyf.myseattable;

/**
 * Created by pyf on 2017/2/8.
 */
public interface SeatChecker {
    /**
     * 是否可用座位
     *
     * @param row
     * @param column
     * @return
     */
    boolean isValidSeat(int row, int column);
    /**
     * 是否已售
     *
     * @param row
     * @param column
     * @return
     */
    boolean isSold(int row, int column);

    void checked(int row, int column);

    void unCheck(int row, int column);

    /**
     * 获取选中后座位上显示的文字
     *
     * @param row
     * @param column
     * @return 返回2个元素的数组, 第一个元素是第一行的文字, 第二个元素是第二行文字, 如果只返回一个元素则会绘制到座位图的中间位置
     */
    String[] checkedSeatTxt(int row, int column);
}
