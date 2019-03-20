package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents work classification.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/07/2019
 *
 * @author Ihar Suvorau
 */
public class WorkClassification extends StoredEntity<String> {

    private Long wrWrkInst;
    private String systemTitle;
    private String article;
    private String author;
    private String publisher;
    private String classification;
    private String standardNumber;
    private Long rhAccountNumber;
    private String rhName;

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getStandardNumber() {
        return standardNumber;
    }

    public void setStandardNumber(String standardNumber) {
        this.standardNumber = standardNumber;
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public String getRhName() {
        return rhName;
    }

    public void setRhName(String rhName) {
        this.rhName = rhName;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        WorkClassification that = (WorkClassification) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.systemTitle, that.systemTitle)
            .append(this.article, that.article)
            .append(this.author, that.author)
            .append(this.publisher, that.publisher)
            .append(this.classification, that.classification)
            .append(this.standardNumber, that.standardNumber)
            .append(this.rhAccountNumber, that.rhAccountNumber)
            .append(this.rhName, that.rhName)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(wrWrkInst)
            .append(systemTitle)
            .append(article)
            .append(author)
            .append(publisher)
            .append(classification)
            .append(standardNumber)
            .append(rhAccountNumber)
            .append(rhName)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("article", article)
            .append("author", author)
            .append("publisher", publisher)
            .append("classification", classification)
            .append("standardNumber", standardNumber)
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .toString();
    }
}
