package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.OffsetDateTime;

/**
 * Contains information from Oracle AP system related to rightsholder.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/17/20
 *
 * @author Stanislau Rudak
 */
public class RhTaxInformation {

    private Long payeeAccountNumber;
    private String payeeName;
    // TODO {srudak} rename to RH
    private Long rorAccountNumber;
    private String rorName;
    private Long tboAccountNumber;
    private String tboName;
    private String productFamily;
    private String taxOnFile;
    private Integer notificationsSent;
    private OffsetDateTime dateOfLastNotificationSent;
    private String typeOfForm;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String city;
    private String state;
    private String zip;
    private String countryCode;
    private String country;
    private String withHoldingIndicator;
    private String payGroup;

    public Long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(Long payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public Long getRorAccountNumber() {
        return rorAccountNumber;
    }

    public void setRorAccountNumber(Long rorAccountNumber) {
        this.rorAccountNumber = rorAccountNumber;
    }

    public String getRorName() {
        return rorName;
    }

    public void setRorName(String rorName) {
        this.rorName = rorName;
    }

    public Long getTboAccountNumber() {
        return tboAccountNumber;
    }

    public void setTboAccountNumber(Long tboAccountNumber) {
        this.tboAccountNumber = tboAccountNumber;
    }

    public String getTboName() {
        return tboName;
    }

    public void setTboName(String tboName) {
        this.tboName = tboName;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public String getTaxOnFile() {
        return taxOnFile;
    }

    public void setTaxOnFile(String taxOnFile) {
        this.taxOnFile = taxOnFile;
    }

    public Integer getNotificationsSent() {
        return notificationsSent;
    }

    public void setNotificationsSent(Integer notificationsSent) {
        this.notificationsSent = notificationsSent;
    }

    public OffsetDateTime getDateOfLastNotificationSent() {
        return dateOfLastNotificationSent;
    }

    public void setDateOfLastNotificationSent(OffsetDateTime dateOfLastNotificationSent) {
        this.dateOfLastNotificationSent = dateOfLastNotificationSent;
    }

    public String getTypeOfForm() {
        return typeOfForm;
    }

    public void setTypeOfForm(String typeOfForm) {
        this.typeOfForm = typeOfForm;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWithHoldingIndicator() {
        return withHoldingIndicator;
    }

    public void setWithHoldingIndicator(String withHoldingIndicator) {
        this.withHoldingIndicator = withHoldingIndicator;
    }

    public String getPayGroup() {
        return payGroup;
    }

    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        RhTaxInformation that = (RhTaxInformation) obj;
        return new EqualsBuilder()
            .append(this.payeeAccountNumber, that.payeeAccountNumber)
            .append(this.payeeName, that.payeeName)
            .append(this.rorAccountNumber, that.rorAccountNumber)
            .append(this.rorName, that.rorName)
            .append(this.tboAccountNumber, that.tboAccountNumber)
            .append(this.tboName, that.tboName)
            .append(this.productFamily, that.productFamily)
            .append(this.taxOnFile, that.taxOnFile)
            .append(this.notificationsSent, that.notificationsSent)
            .append(this.dateOfLastNotificationSent, that.dateOfLastNotificationSent)
            .append(this.typeOfForm, that.typeOfForm)
            .append(this.addressLine1, that.addressLine1)
            .append(this.addressLine2, that.addressLine2)
            .append(this.addressLine3, that.addressLine3)
            .append(this.addressLine4, that.addressLine4)
            .append(this.city, that.city)
            .append(this.state, that.state)
            .append(this.zip, that.zip)
            .append(this.countryCode, that.countryCode)
            .append(this.country, that.country)
            .append(this.withHoldingIndicator, that.withHoldingIndicator)
            .append(this.payGroup, that.payGroup)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(payeeAccountNumber)
            .append(payeeName)
            .append(rorAccountNumber)
            .append(rorName)
            .append(tboAccountNumber)
            .append(tboName)
            .append(productFamily)
            .append(taxOnFile)
            .append(notificationsSent)
            .append(dateOfLastNotificationSent)
            .append(typeOfForm)
            .append(addressLine1)
            .append(addressLine2)
            .append(addressLine3)
            .append(addressLine4)
            .append(city)
            .append(state)
            .append(zip)
            .append(countryCode)
            .append(country)
            .append(withHoldingIndicator)
            .append(payGroup)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("payeeAccountNumber", payeeAccountNumber)
            .append("payeeName", payeeName)
            .append("rorAccountNumber", rorAccountNumber)
            .append("rorName", rorName)
            .append("tboAccountNumber", tboAccountNumber)
            .append("tboName", tboName)
            .append("productFamily", productFamily)
            .append("taxOnFile", taxOnFile)
            .append("notificationsSent", notificationsSent)
            .append("dateOfLastNotificationSent", dateOfLastNotificationSent)
            .append("typeOfForm", typeOfForm)
            .append("addressLine1", addressLine1)
            .append("addressLine2", addressLine2)
            .append("addressLine3", addressLine3)
            .append("addressLine4", addressLine4)
            .append("city", city)
            .append("state", state)
            .append("zip", zip)
            .append("countryCode", countryCode)
            .append("country", country)
            .append("withHoldingIndicator", withHoldingIndicator)
            .append("payGroup", payGroup)
            .toString();
    }
}
