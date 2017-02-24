package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import java.util.Arrays;
import java.util.List;

/**
 * CSV processor for uploading {@link Usage}s.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/21/17
 *
 * @author Aliaksei Pchelnikau
 */
public class UsageCsvProcessor extends CommonCsvProcessor<Usage> {

    private String batchId;
    private String userName;

    /**
     * Constructor.
     *
     * @param batchId  batch id
     * @param userName user name
     */
    public UsageCsvProcessor(String batchId, String userName) {
        this.batchId = batchId;
        this.userName = userName;
    }

    @Override
    protected List<ICsvColumn> getHeaders() {
        return Arrays.asList(Header.values());
    }

    @Override
    protected Usage deserialize(List<String> params) {
        Usage result = new Usage();
        result.setId(RupPersistUtils.generateUuid());
        result.setBatchId(batchId);
        result.setDetailId(getLong(Header.DETAIL_ID, params));
        result.setWorkTitle(getString(Header.TITLE, params));
        result.setArticle(getString(Header.ARTICLE, params));
        result.setStandardNumber(getString(Header.STANDARD_NUMBER, params));
        result.setWrWrkInst(getLong(Header.WR_WRK_INST, params));
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(getLong(Header.RH_ACCT_NUMBER, params));
        result.setRightsholder(rightsholder);
        result.setPublisher(getString(Header.PUBLISHER, params));
        result.setPublicationDate(getDate(Header.PUB_DATE, params));
        result.setNumberOfCopies(getInteger(Header.NUMBER_OF_COPIES, params));
        result.setOriginalAmount(getBigDecimal(Header.AMT_IN_ORIG_CURRENCY, params));
        result.setMarket(getString(Header.MARKET, params));
        result.setMarketPeriodFrom(getInteger(Header.MARKET_PERIOD_FROM, params));
        result.setMarketPeriodTo(getInteger(Header.MARKET_PERIOD_TO, params));
        result.setAuthor(getString(Header.AUTHOR, params));
        result.setStatus(UsageStatusEnum.ELIGIBLE);
        result.setCreateUser(userName);
        result.setUpdateUser(userName);
        return result;
    }

    private enum Header implements ICsvColumn {
        DETAIL_ID("Detail ID"),
        TITLE("Title"),
        ARTICLE("Article"),
        STANDARD_NUMBER("Standard Number"),
        WR_WRK_INST("Wr Wrk Inst"),
        RH_ACCT_NUMBER("RH Acct Number"),
        PUBLISHER("Publisher"),
        PUB_DATE("Pub Date"),
        NUMBER_OF_COPIES("Number of Copies"),
        AMT_IN_ORIG_CURRENCY("Amt in Orig Currency"),
        MARKET("Market"),
        MARKET_PERIOD_FROM("Market Period From"),
        MARKET_PERIOD_TO("Market Period To"),
        AUTHOR("Author");

        private String columnName;

        Header(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String getColumnName() {
            return columnName;
        }
    }
}
