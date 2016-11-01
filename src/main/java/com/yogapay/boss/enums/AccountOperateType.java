/**
 * 项目: posboss
 * 包名：com.yogapay.boss.enums
 * 文件名: AccountOperateType
 * 创建时间: 2014/10/9 15:51
 * 支付界科技有限公司版权所有，保留所有权利
 */
package com.yogapay.boss.enums;

/**
 * @Todo: 账户操作类型
 * @Author: Zhanggc
 */
public enum AccountOperateType {
    IN("in"),//进款
    OUT("out"),//出款
    FREEZE("freeze"),//冻结
    UNFREEZE("unfreeze"),//解冻
    ADJUSTIN("adjust_in"),//调款进
    ADJUSTOUT("adjust_out");//调款出

    private String name;
    AccountOperateType(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
}