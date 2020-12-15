package com.cx.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @author chenxin
 * @date 2020/12/11
 */

@Document(indexName = "forum_info")
public class ForumInfoElasticSearchData implements Serializable {

    @Id
    private String uuId;

    private Long ownerId;

    private Integer ownerType;

    private Long modelId;

    private String title;

    private String content;

    private Long topicId;

    private String topicName;

    private Integer likeNum;

    private Integer commentNum;

    private Integer browseNum;

    private Date createTime;

    private Long createUserId;

    private List<String> imgUrlList;

    private Integer testNum;

    public ForumInfoElasticSearchData(Long ownerId, Integer ownerType, Long modelId, String title, String content,
        Long topicId, String topicName, Integer likeNum, Integer commentNum, Integer browseNum, Date createTime,
        Long createUserId, List<String> imgUrlList) {
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.modelId = modelId;
        this.title = title;
        this.content = content;
        this.topicId = topicId;
        this.topicName = topicName;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.browseNum = browseNum;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.imgUrlList = imgUrlList;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public Integer getTestNum() {
        return testNum;
    }

    public void setTestNum(Integer testNum) {
        this.testNum = testNum;
    }

    @Override
    public String toString() {
        return "ForumInfoElasticSearchData{" + "uuId='" + uuId + '\'' + ", ownerId=" + ownerId + ", ownerType="
            + ownerType + ", modelId=" + modelId + ", title='" + title + '\'' + ", content='" + content + '\''
            + ", topicId=" + topicId + ", topicName='" + topicName + '\'' + ", likeNum=" + likeNum + ", commentNum="
            + commentNum + ", browseNum=" + browseNum + ", createTime=" + createTime + ", createUserId=" + createUserId
            + ", imgUrlList=" + imgUrlList + ", testNum=" + testNum + '}';
    }
}