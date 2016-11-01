/**
 * 项目: posboss
 * 包名：com.yogapay.boss.enums
 * 文件名: SettleType
 * 创建时间: 2015/1/4 10:53
 * 支付界科技有限公司版权所有，保留所有权利
 */
package com.yogapay.boss.enums;

/**
 * @Todo: 结算类型
 * @Author: Zhanggc
 */
public enum SettleType {
    MinSheng("民生类"),
    CanYu("餐娱类"),
    PiFa("批发类"),
    YiBan("一般类"),
    FangChe("房车类"),
    Other("其他");

    String name;

    SettleType(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
