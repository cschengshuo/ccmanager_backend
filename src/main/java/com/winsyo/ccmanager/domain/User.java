package com.winsyo.ccmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    /**
     * 姓名
     */
    @Column
    private String name;

    /**
     * 登录名
     */
    @Column
    private String loginName;

    /**
     * 手机号
     */
    @Column
    private String mobile;

    /**
     * 邀请码
     */
    @Column
    private String inviteCode;

    /**
     * 身份证号
     */
    @Column
    private String identityCard;

    /**
     * 代理层级 TODO 改名
     */
    @Column
    private Integer userType;

    /**
     * 上级用户ID
     */
    @Column
    private String parentId;

    /**
     * 顶级用户ID
     */
    @Column
    private String topUserId;

    /**
     * 证书
     */
    @Column
    private String licence;

    /**
     * 紧急联系人
     */
    @Column
    private String emergencyContact;
}
