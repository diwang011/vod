package com.vod.db.model;

public class UserInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userinfo.id
     *
     * @mbggenerated Mon Apr 24 16:26:22 CST 2017
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userinfo.name
     *
     * @mbggenerated Mon Apr 24 16:26:22 CST 2017
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userinfo.amount
     *
     * @mbggenerated Mon Apr 24 16:26:22 CST 2017
     */
    private Double amount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userinfo.id
     *
     * @return the value of userinfo.id
     *
     * @mbggenerated Mon Apr 24 16:26:22 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userinfo.id
     *
     * @param id the value for userinfo.id
     *
     * @mbggenerated Mon Apr 24 16:26:22 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userinfo.name
     *
     * @return the value of userinfo.name
     *
     * @mbggenerated Mon Apr 24 16:26:22 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userinfo.name
     *
     * @param name the value for userinfo.name
     *
     * @mbggenerated Mon Apr 24 16:26:22 CST 2017
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userinfo.amount
     *
     * @return the value of userinfo.amount
     *
     * @mbggenerated Mon Apr 24 16:26:22 CST 2017
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userinfo.amount
     *
     * @param amount the value for userinfo.amount
     *
     * @mbggenerated Mon Apr 24 16:26:22 CST 2017
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}