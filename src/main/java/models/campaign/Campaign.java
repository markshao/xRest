package models.campaign;

import models.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Jacky.Li on 14-4-29.
 * 以下信息都是存放在campaign数据表里面
 */
public class Campaign extends BaseModel {

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getMediaPlanId() {
        return mediaPlanId;
    }

    public void setMediaPlanId(Integer mediaPlanId) {
        this.mediaPlanId = mediaPlanId;
    }

    public Integer getAdvId() {
        return advId;
    }

    public void setAdvId(Integer advId) {
        this.advId = advId;
    }

    public Byte getIsRtb() {
        return isRtb;
    }

    public void setIsRtb(Byte isRtb) {
        this.isRtb = isRtb;
    }

    public Byte getPayment() {
        return payment;
    }

    public void setPayment(Byte payment) {
        this.payment = payment;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getIadShorturl() {
        return iadShorturl;
    }

    public void setIadShorturl(String iadShorturl) {
        this.iadShorturl = iadShorturl;
    }

    public Integer getCsAccountId() {
        return csAccountId;
    }

    public void setCsAccountId(Integer csAccountId) {
        this.csAccountId = csAccountId;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public Integer getCreatorSourceId() {
        return creatorSourceId;
    }

    public void setCreatorSourceId(Integer creatorSourceId) {
        this.creatorSourceId = creatorSourceId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getOwnerSourceId() {
        return ownerSourceId;
    }

    public void setOwnerSourceId(Integer ownerSourceId) {
        this.ownerSourceId = ownerSourceId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    /**
     * 订单编号
     */
    private Integer campaignId;
    /**
     * 订单名称
     */
    private String name;
    /**
     * 订单来源编号
     */
    private Integer sourceId;
    /**
     * 媒介计划编号
     */
    private Integer mediaPlanId;
    /**
     * 广告主编号
     */
    private Integer advId;
    /**
     * 是否包含RTB投放
     */
    private Byte isRtb;
    /**
     * 订单收费类型
     */
    private Byte payment;
    /**
     * 订单预算
     */
    private BigDecimal budget;
    /**
     * 艾瑞点击监测短代码。用于被订单下MediaBuy初始创建时继承。
     */
    private String iadShorturl;
    /**
     * ASG编号
     */
    private Integer csAccountId;
    /**
     * ASG姓名
     */
    private String csName;
    /**
     * 创建人所在平台编号
     */
    private Integer creatorSourceId;
    /**
     * 创建人编号。该编号为创建人在其平台中的用户编号
     */
    private Integer creatorId;
    /**
     * 创建人名称
     */
    private String creatorName;
    /**
     * 负责人所在平台编号
     */
    private Integer ownerSourceId;
    /**
     * 负责人编号。该编号为负责人在其平台中的用户编号
     */
    private Integer ownerId;
    /**
     * 负责人名称
     */
    private String ownerName;
    /**
     * 订单开始时间
     */
    private Date startTime;
    /**
     * 订单结束时间
     */
    private Date endTime;

    @Override
    public void validateSchema() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
